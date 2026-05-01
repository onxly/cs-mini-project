package EXEcutioners.adts.abstractClasses;

import java.util.Iterator;
import java.util.NoSuchElementException;

import EXEcutioners.adts.interfaces.IPosition;
import EXEcutioners.adts.interfaces.IPositionalList;

public class LinkedPositionalList<E> implements IPositionalList<E>, Iterable<E> {
	private class PositionsIterator implements Iterator<IPosition<E>>{
		private IPosition<E> cursor = LinkedPositionalList.this.first();
		private IPosition<E> current = null;
		
		@Override
		public boolean hasNext() {
			if (cursor == null) {
				return false;
			} else {
				return true;
			}
			
		}

		@Override
		public IPosition<E> next() throws NoSuchElementException{
			if (cursor != null) {
				current = cursor;
				cursor = LinkedPositionalList.this.after(cursor);
				return current;
			} else {
				throw new NoSuchElementException("Iterator has no next element");
			}
		}
		
		public void remove() throws IllegalStateException
		{
			if (current != null) {
				LinkedPositionalList.this.remove(current);
				current = null;
			} else {
				throw new IllegalStateException("Current item is already null");
			}
		}
		
	}
	
	private class ElementsIterator implements Iterator<E>{
		Iterator<IPosition<E>> pIterator = new PositionsIterator();
		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return pIterator.hasNext();
		}

		@Override
		public E next() {
			// TODO Auto-generated method stub
			return pIterator.next().getElement();
		}
		
		public void remove() {
			pIterator.remove();
		}
		
	}
	
	
	private Node<E> header;
	private Node<E> trailer;
	private int _size;
	
	public LinkedPositionalList() {
		this._size = 0;
		this.header = new Node<E>(null, null, null);
		this.trailer = new Node<E>(header, null, null);
		this.header.setNext(trailer);
	}
	
	private Node<E> validate(IPosition<E> p) throws IllegalArgumentException{
		if (p instanceof Node) {
			Node<E> node = (Node<E>) p;
			if (node.getNext() != null) {
				return node;
			} else {
				throw new IllegalArgumentException("Node was removed from list");
			}
		} else {
			throw new IllegalArgumentException("Position is invalid");
		}
	}
	
	private IPosition<E> position(Node<E> node){
		if (node == header || node == trailer) {
			return null;
		}
		return node;
	}
	
	@Override
	public IPosition<E> first() {
		return position(header.getNext());
	}

	@Override
	public IPosition<E> last() {
		return position(trailer.getPrev());
	}

	@Override
	public IPosition<E> before(IPosition<E> pos) throws IllegalArgumentException {
		Node<E> node = validate(pos);
		
		return position(node.getPrev());
	}

	@Override
	public IPosition<E> after(IPosition<E> pos) throws IllegalArgumentException{
		Node<E> node = validate(pos);
		return position(node.getNext());
	}

	@Override
	public boolean isEmpty() {
		if (_size == 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int size() {
		return this._size;
	}

	@Override
	public IPosition<E> addFirst(E elm) {
		Node<E> newNode = new Node<E>(header, header.getNext(), elm);
		Node<E> next = header.getNext();
		next.setPrev(newNode);
		header.setNext(newNode);
		this._size++;
		return position(newNode);
	}

	@Override
	public IPosition<E> addLast(E elm) {
		Node<E> newNode = new Node<E>(trailer.getPrev(), trailer, elm);
		Node<E> prev = trailer.getPrev();
		prev.setNext(newNode);
		trailer.setPrev(newNode);
		_size++;
		return null;
	}

	@Override
	public IPosition<E> addBefore(IPosition<E> pos, E elm) {
		Node<E> node = validate(pos);
		Node<E> prev = node.getPrev();
		Node<E> newNode = new Node<E>(prev, node, elm);
		node.setPrev(newNode);
		prev.setNext(newNode);
		_size++;
		return position(newNode);
	}

	@Override
	public IPosition<E> addAfter(IPosition<E> pos, E elm) {
		Node<E> node = validate(pos);
		Node<E> next = node.getNext();
		Node<E> newNode = new Node<E>(node, next, elm);
		node.setNext(newNode);
		next.setPrev(newNode);
		_size++;
		return position(newNode);
	}

	@Override
	public E set(IPosition<E> pos, E elm) throws IllegalArgumentException{
		Node<E> node = validate(pos);
		E og = node.getElement();
		node.setElement(elm);
		return og;
	}

	@Override
	public E remove(IPosition<E> pos) throws IllegalArgumentException{
		Node<E> node = validate(pos);
		Node<E> next = node.getNext();
		Node<E> prev = node.getPrev();
		prev.setNext(next);
		next.setPrev(prev);
		E og = node.getElement();
		node.setPrev(null);
		node.setNext(null);
		_size--;
		return og;
	}
	
	public Iterator<E> iterator(){
		return new ElementsIterator();
	}
	
	public Iterator<IPosition<E>> positions(){
		return new PositionsIterator();
	}

}
