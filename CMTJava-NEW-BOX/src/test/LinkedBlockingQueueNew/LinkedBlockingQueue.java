package test.LinkedBlockingQueueNew;

import stm.*;

public class LinkedBlockingQueue<E> extends TLinkedBlockingQueue<E>{


	public LinkedBlockingQueue(Integer nCapacity) throws Exception{
		super(0);
	}

	public LinkedBlockingQueue(){
		super();
	}


	public E take() throws Exception{
		if(this.isFull()){
			throw new RetryException();
		}else{
			return this.extract();
		}
	}

	/**
     Retrieves and removes the head of this queue, 
		or returns null if this queue is empty.
	*/
	public E poll() throws Exception{
		Integer countValue = getCount();
		if(isFull() || countValue != 0){
			return extract();		
		}
		else{
			return null;
		}
	}


	/*Adds the specified element to the tail of this queue, 
	waiting if necessary for space to become available.
	*/
	public void put(E e) throws Exception{
		if(isFull()){
			throw new RetryException();
		}else{
			insert(e);
		}
	}

	/**
     * Inserts the specified element at the tail of this queue if it is
     * possible to do so immediately without exceeding the queue's capacity,
     * returning true upon success and false if this queue
     * is full.
	*/
	public boolean offer(E e) throws Exception{
		if(isFull()){
			return false;
		}else{
			insert(e);
			return true;
		}
	}

	/** 
		Auxiliar Methods
	*/

    /**
     * Creates a node and links it at end of queue.
     */
	private void insert(E x) throws Exception{
		Node<E> tail = getLast();
		Node<E> node = new Node(x);
		setLast(node);
		Integer countValue = getCount();
		setCount(countValue+1);
		if(countValue == 0){
			setHead(node);
		}else{
			tail.setNext(node);
		}
	}


    /**
     * Removes a node from head of queue,
     * returns the node
     */
	private E extract() throws Exception{
		Node<E> headValue = getHead();
		Integer countValue = count.getBox(); 
		setCount(countValue - 1);
		if(countValue == 1){
			setHead(null);
			setLast(null);
			return headValue.getItem();
		}else{
			Node<E> first = getHead().getNext();
			Node<E> second = first.getNext();
			first.setNext(second);
			setHead(first);
			return headValue.getItem();
		}

	}

	/**
	Checks wheather the queue is full or not
	*/
	private Boolean isFull() throws Exception{
		Integer countValue = getCount();
		Integer capacityValue = getCapacity();
		if(countValue != capacityValue){
			return false;
		}else{
			return true;
		}

		
	}
}
