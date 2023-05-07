package test.LinkedListNew;

import stm.*;

class Node<E>{

	private Box<E> element;
	private Box<Node<E>> next;
	private Box<Node<E>> previous;
	
	public E getElement() throws Exception{
		return element.getBox();
	}
	public void setElement(E e) throws Exception{
		element.setBox(e);
	}

	public Node<E> getNext() throws Exception{
		return next.getBox();
	}
	public void setNext(Node<E> node) throws Exception{
		next.setBox(node);
	}

	public Node<E> getPrevios() throws Exception{
		return previous.getBox();
	}
	public void setPrevious(Node<E> e) throws Exception{
		previous.setBox(e);
	}

	Node(E element, Node<E> next, Node<E> previous) {
			this.element = new Box<E>(element);
			this.next = new Box<Node<E>>(next);
			this.previous = new Box<Node<E>>(previous);
	}
	
}

