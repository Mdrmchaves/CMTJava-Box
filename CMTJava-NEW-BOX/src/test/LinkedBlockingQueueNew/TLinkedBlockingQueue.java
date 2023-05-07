package test.LinkedBlockingQueueNew;
import stm.*;
public class TLinkedBlockingQueue<E> {
    private Box<Integer> capacity;
	private Box<Integer> count;
	private Box<Node<E>> head;
	private Box<Node<E>> last;

    /* Get Set */

	public Integer getCapacity() throws Exception{
		return capacity.getBox();
	}

	public void setCapacity(Integer n) throws Exception{
		capacity.setBox(n);
	}

	public Integer getCount() throws Exception{
		return count.getBox();
	}

	public void setCount(Integer n) throws Exception{
		count.setBox(n);
	}

	public Node<E> getHead() throws Exception{
		return head.getBox();
	}

	public void setHead(Node<E> e) throws Exception{
		head.setBox(e);
	}

	public Node<E> getLast() throws Exception{
		return last.getBox();
	}

	public void setLast(Node<E> e) throws Exception{
		last.setBox(e);
	}

    public TLinkedBlockingQueue(Integer nCapacity) throws Exception{
		capacity = new Box<Integer>(nCapacity);
		count = new Box<Integer>(0);
		head = new Box<Node<E>>(null);
		last = new Box<Node<E>>(null);
	}

	public TLinkedBlockingQueue(){
		capacity = new Box<Integer>(0);
		count = new Box<Integer>(0);
		head = new Box<Node<E>>(null);
		last = new Box<Node<E>>(null);
	}
}
