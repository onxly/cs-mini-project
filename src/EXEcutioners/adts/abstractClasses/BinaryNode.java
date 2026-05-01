package EXEcutioners.adts.abstractClasses;

import EXEcutioners.adts.interfaces.IPosition;

public class BinaryNode<E> implements IPosition<E> {
	private BinaryNode<E> parent;
	private BinaryNode<E> left;
	private BinaryNode<E> right;
	private E element;
	
	public BinaryNode(BinaryNode<E> p, BinaryNode<E> l, BinaryNode<E> r, E e) {
		this.parent = p;
		this.left = l;
		this.right = r;
		this.element = e;
	}
	
	public BinaryNode<E> getParent() {
		return parent;
	}



	public void setParent(BinaryNode<E> parent) {
		this.parent = parent;
	}



	public BinaryNode<E> getLeft() {
		return left;
	}



	public void setLeft(BinaryNode<E> left) {
		this.left = left;
	}



	public BinaryNode<E> getRight() {
		return right;
	}



	public void setRight(BinaryNode<E> right) {
		this.right = right;
	}



	public void setElement(E element) {
		this.element = element;
	}



	@Override
	public E getElement() {
		return element;
	}

}
