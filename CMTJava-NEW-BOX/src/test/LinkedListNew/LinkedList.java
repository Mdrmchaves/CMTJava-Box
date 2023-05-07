package test.LinkedListNew;

import stm.*;

public class LinkedList<E> extends TLinkedList{	

	public java.lang.Void addFirst(E e) throws Exception{
		Node<E> head = getHead();
		Node<E> node = new Node<E>(e, head,null);
		setHead(node);
		Integer size = getSize();
		setSize(size+1);
		if(size == 0){
			setTail(node);
		}else{
			node.setNext(head);
			head.setPrevious(node);
		}
		return null;
	}

	public java.lang.Void addLast(E e) throws Exception{
		Node<E> tail = getTail();
		Node<E> node = new Node<E>(e, null, tail);
		setTail(node);
		Integer lsize = getSize();
		setSize(lsize+1);
		if(lsize == 0){
			setHead(node);
		}else{
			tail.setNext(node);
			node.setPrevious(node);
		}
		return null;
	}

	public Boolean contains(E e) throws Exception{
		Node<E> head = getHead();
		return this.contains(head, e);
	}

	private Boolean contains(Node<E> curNode, E e) throws Exception{
		if(curNode == null){
			return false;
		}else{
			E element = curNode.getElement();
			if(element == e){
				return true;
			}else{
				Node<E> next = curNode.getNext();
				return this.contains(curNode, e);
			}
		}
	}

	public Boolean remove(E e) throws Exception{
		Node<E> head = getHead();
		return remove(head, e);
	}

	private Boolean remove(Node<E> curNode, E e) throws Exception{
		if(curNode == null){
			return false;
		}else{
			E element = curNode.getElement();
			if(element == e){
				remove(curNode);
				return true;
			}else{
				Node<E> node = curNode.getNext();
				return remove(node, e);
			}
		}
	}

	private E remove(Node<E> node) throws Exception{
		Integer lsize = getSize();
		setSize(lsize-1);
		if(lsize == 1){
			setHead(null);
			setTail(null);
		}else{
			Node<E> head = getHead();
			Node<E> tail = getTail();
			Node<E> next = node.getNext();
			Node<E> previous = node.getPrevios();
			if(node == head){
				setHead(next);
				next.setPrevious(null);
			}else if(node == tail){
				setTail(previous);
				previous.setNext(null);
			}else{
				previous.setNext(next);
				next.setPrevious(previous);
			}
		}
		return node.getElement();
	}
}
