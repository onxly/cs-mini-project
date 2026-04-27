package EXEcutioners.adts.abstractClasses;

import EXEcutioners.adts.interfaces.IPosition;

public class Node<E> implements IPosition<E> {
	private E element;
	private Node<E> next;
	private Node<E> prev;
	
	public Node(Node<E> p, Node<E> n, E e) {
		this.prev = p;
		this.next = n;
		this.element = e;
	}
	
	@Override
	public E getElement() {
		return element;
	}
	
	public void setElement(E elm) {
		this.element = elm;
	}
	
	public Node<E> getNext(){
		return next;
	}
	
	public void setNext(Node<E> newNext) {
		this.next = newNext;
	}

	public Node<E> getPrev(){
		return prev;
	}
	
	public void setPrev(Node<E> newPrev) {
		this.prev = newPrev;
	}
	
}
