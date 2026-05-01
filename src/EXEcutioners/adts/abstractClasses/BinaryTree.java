package EXEcutioners.adts.abstractClasses;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import EXEcutioners.adts.interfaces.IPosition;
import EXEcutioners.adts.interfaces.ITree;

public class BinaryTree<E> implements ITree<E> {
	
	protected BinaryNode<E> root = null; 
	protected int _size =0;
	
	public BinaryTree(){
		
	}
	
	protected BinaryNode<E> validate(IPosition<E> p) throws IllegalArgumentException{
		if (p instanceof BinaryNode) {
			BinaryNode<E> node = (BinaryNode<E>) p;
			if (node.getParent() != node) {
				return node;
			} else {
				throw new IllegalArgumentException("Node was removed from list");
			}
		} else {
			throw new IllegalArgumentException("Position is invalid");
		}
	}

	@Override
	public boolean isInternal(IPosition<E> p) { 
		return numChildren(p) > 0; 
	}
	
	@Override
	public boolean isExternal(IPosition<E> p) { 
		return numChildren(p) == 0; 
	}
	public boolean isRoot(IPosition<E> p) { 
		return p == root(); 
	}
	public boolean isEmpty() { 
		return size() == 0; 
	}
	
	public int depth(IPosition<E> p) {
		if (isRoot(p)) {
			return 0;
		} else {
			
		}
		return 1 + depth(parent(p));
	}
	
	public int height(IPosition<E> p){
		int h=0; 
		for(IPosition<E> c: children(p)) {
			h = Math.max(h,1+height(c));
		}
		return h;
	}
	
	public IPosition<E> sibling(IPosition<E> p){
		IPosition<E>parent=parent(p);
		if(parent==null) {
			return null;
		}
		if(p==left(parent)) {
			return right(parent);
		} else {
			return left(parent); 
		}
	}
	
	@Override
	public int numChildren(IPosition<E> p) throws IllegalArgumentException {
		int iCount=0;
		if(left(p) != null) {
			iCount++;
		}
		if(right(p) != null) {
			iCount++;
		}
		return iCount;
	}
	
	 public IPosition<E> root(){
		 return root;
	}
	 
	public IPosition<E> parent(IPosition<E> p) throws IllegalArgumentException{
		BinaryNode<E> node = validate(p);
		return node.getParent();
	}
	
	public IPosition<E> left(IPosition<E>p) throws IllegalArgumentException{
		BinaryNode<E> node = validate(p);
		 return node.getLeft();
	}
	
	public IPosition<E> right(IPosition<E> p) throws IllegalArgumentException{
		BinaryNode<E> node = validate(p);
		return node.getRight();
	}

	@Override
	public Iterable<IPosition<E>> children(IPosition<E> p) throws IllegalArgumentException {
		ArrayList<IPosition<E>> list = new ArrayList<>(2); //maxcapacityof2
		if(left(p) != null) {
			list.add(left(p));
		}
		if(right(p) != null) {
			list.add(right(p));
		}
		return list;
	}

	


	@Override
	public int size() {
		// TODO Auto-generated method stub
		return _size;
	}
	
	public IPosition<E> addRoot(E e) throws IllegalStateException{
		if(isEmpty()) {
			root = new BinaryNode<E>(null, null, null, e);
			_size = 1;
			return root;
		} else {
			throw new IllegalStateException("Tree is not empty");
		}	
	}
	
	 public IPosition<E> addLeft(IPosition<E> p, E e) throws IllegalArgumentException{
		 BinaryNode<E> parent = validate(p);
		 if(parent.getLeft() == null) {
			 BinaryNode<E> child = new BinaryNode<E>(parent, null, null, e);
			 parent.setLeft(child);
			 _size++;
			 return child;
		 } else {
			 throw new IllegalArgumentException("p already has a left child");
		 }
	 }
	 
	 public IPosition<E> addRight(IPosition<E> p, E e) throws IllegalArgumentException{
		 BinaryNode<E> parent = validate(p);
		 if(parent.getRight() == null) {
			 BinaryNode<E> child = new BinaryNode<E>(parent, null, null, e);
			 parent.setRight(child);
			 _size++;
			 return child;
		 } else {
			 throw new IllegalArgumentException("p already has a left child");
		 }
	 }

	 public E set(IPosition<E> p, E e) throws IllegalArgumentException{
		 BinaryNode<E> node = validate(p);
		 E oldElm = node.getElement();
		 node.setElement(e);
		 return oldElm;
	 }
	 
	 public void attach(IPosition<E>p, BinaryTree<E> t1, BinaryTree<E>t2) throws IllegalArgumentException{
		 BinaryNode<E> node = validate(p);
		 if(!isInternal(p)) {
			 _size += t1.size()+t2.size();
			if(!t1.isEmpty()){ 
				t1.root.setParent(node);
				node.setLeft(t1.root);
				t1.root=null;
				t1._size = 0;
			}
			if(!t2.isEmpty()){ 
				t2.root.setParent(node);
				node.setRight(t2.root);
				t2.root=null;
				t2._size=0;
			} 
		 } else {
			 throw new IllegalArgumentException("P is not a leaf");
		 }
	}
	 
	public E remove(IPosition<E> p) throws IllegalArgumentException{
		BinaryNode<E> node = validate(p);
		if(numChildren(p) != 2) {
			BinaryNode<E> child = (node.getLeft()!=null?node.getLeft() :node.getRight());
			if(child != null) {
				child.setParent(node.getParent());
			}
			 
			if(node == root) {
				root = child;
			} else {
				BinaryNode<E> parent = node.getParent();
				if(node == parent.getLeft()) {
					parent.setLeft(child);
				} else {
					parent.setRight(child);
				}
			}
			_size--;
			E temp = node.getElement();
			node.setElement(null); 
			node.setLeft(null);
			node.setRight(null);
			node.setParent(node);
			return temp;
		} else {
			throw new IllegalArgumentException("p is full");
		}
		
		 
	}

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<IPosition<E>> positions() {
		// TODO Auto-generated method stub
		return null;
	}

}
