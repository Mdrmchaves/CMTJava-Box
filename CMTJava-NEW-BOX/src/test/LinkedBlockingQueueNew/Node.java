package test.LinkedBlockingQueueNew;

import stm.*;

class Node<E>{
	private Box<E> item;
	private Box<Node<E>> next;

	public E getItem() throws Exception{
		return item.getBox();
	}

	public void setItem( E e) throws Exception{
		item.setBox(e);
	}

	public Node<E> getNext() throws Exception{
		return next.getBox();
	}

	public void setNext(Node<E> node) throws Exception{
		next.setBox(node);
	}
	 

	 Node(E x) throws Exception{
		item = new Box<E>(x);
		next = new Box<Node<E>>(null);
	}
	
}