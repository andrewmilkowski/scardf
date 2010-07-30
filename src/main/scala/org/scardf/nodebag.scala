package org.scardf

import org.joda.time.LocalDate
import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat.{date => IsoFormat}

case class RdfTraversalException( msg: String ) extends Exception( msg )

object NodeBag {
  val empty = NodeBag( List(), Graph() )
}

case class NodeBag( nodes: List[Node], graph: Graph ) extends Iterable[Node] {

  override def iterator: Iterator[Node] =  this.elements
  
  override def elements = nodes.elements
  def contains( n: Node ) = nodes contains n
  
  lazy val nodesFromGraph: List[NodeFromGraph] = nodes map{ _(graph) }

  private[this] def values( n: Node, p: UriRef ) = n match {
    case l: Literal => List( l )
    case n: SubjectNode => (graph/n/p).nodes
  }
  
  def singleNode: NodeFromGraph = nodes match {
    case List( n ) => n(graph)
    case _ => throw new RdfTraversalException( "Expected single-node bag, but got " + this )
  }
  
  def /( predicate: UriRef ) = NodeBag( nodes.flatMap{ values( _, predicate ) }, graph )
  def /[T]( bc: NodeBagConverter[T] ) = bc( this )
  def /[T]( nc: NodeToValueConverter[T] ) = nc( singleNode )
  def ? = this/NodeConverter.asBoolean.set == Set( true )
  def /#? = !nodes.isEmpty
  
  def rend = nodes.map{ _.rend }.mkString( "bag{ ", ", ", " }" )

  lazy val sorted = new NodeBag( nodes sort { (a, b) => a.rend < b.rend }, graph )

  /**
   * Equal if argument is a NodeBag with the same elements, regardless of order.
   */
  override def equals( o: Any ) = o match {
    case that: NodeBag => (this.sorted sameElements that.sorted) && (this.graph == that.graph)
    case _ => false
  }
  
  override lazy val hashCode: Int = sorted.nodes.hashCode + graph.hashCode
}
