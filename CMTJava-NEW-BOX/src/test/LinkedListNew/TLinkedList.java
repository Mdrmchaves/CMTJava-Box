package test.LinkedListNew;
import stm.*;

class TLinkedList<E>{

	private Box<Node<E>> head;
	private Box<Node<E>> tail;
	private Box<Integer> size;

	public Node<E> getHead() throws Exception{
		return head.getBox();
	}

	public void setHead(Node<E> e) throws Exception{
		head.setBox(e);
	}

	public Node<E> getTail() throws Exception{
		return tail.getBox();
	}

	public void setTail(Node<E> e) throws Exception{
		tail.setBox(e);
	}

	public Integer getSize() throws Exception{
		return size.getBox();
	}

	public void setSize(Integer n) throws Exception{
		size.setBox(n);
	}

	TLinkedList() {
		
		head = new Box<Node<E>>(null);
		
		tail = new Box<Node<E>>(null);
	    
		size = new Box<Integer>(0);
	}
	
}