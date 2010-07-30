package org.scardf.jena

import com.hp.hpl.jena.rdf.model._
import com.hp.hpl.jena.util.iterator.ExtendedIterator

import org.scardf.Node
import org.scardf.RdfList

case class JenaRdfList( jlist: RDFList ) extends RdfList[Node] {

  override def iterator: Iterator[Node] =  this.elements
  
  override def first = Jena.node( jlist.getHead )

  def rest = JenaRdfList( jlist.getTail )
  
  def node = Jena.subjectNode( jlist )
  
  override def elements = new JenaExtendedIterator( jlist.iterator )
}

class JenaExtendedIterator( ei: ExtendedIterator[RDFNode] ) extends Iterator[Node] {
  def hasNext = ei.hasNext
  def next = Jena node ei.next
}
