package EXEcutioners.adts.abstractClasses;

import java.util.Comparator;

import EXEcutioners.adts.interfaces.IEntry;
import EXEcutioners.adts.interfaces.IPosition;
import EXEcutioners.adts.interfaces.IPriorityQueue;

public class Heap<K, V> extends BinaryTree<IEntry<K,V>> implements IPriorityQueue<K, V>  {
	private class DefaultComparator implements Comparator<K> {
		
		public int compare(K a, K b) throws ClassCastException {
			
			return ((Comparable<K>) a).compareTo(b);
		}
	}
	
	private Comparator<K> comp;
	private BinaryNode<IEntry<K, V>> lastNode = null;
	
	public Heap(Comparator<K> c){
		comp=c;
	}
	
	public Heap(){
		this.comp = new DefaultComparator();
	}

	public int compare(IEntry<K,V> a, IEntry<K,V> b){
		return comp.compare(a.getKey(),b.getKey());
	}
	
	public boolean checkKey(K key) throws IllegalArgumentException{
		try{
			return(comp.compare(key,key)==0);
		} catch(ClassCastException e){
			throw new IllegalArgumentException("Key provided is not comparable");
		}
	}

	@Override
	public int size() {
		return super.size();
	}

	@Override
	public boolean isEmpty() {
		return super.isEmpty();
	}

	@Override
	public IEntry<K, V> insert(K key, V value) throws IllegalArgumentException {
		BinaryNode<IEntry<K, V>> insertNode = null;
		IEntry<K, V> val = new PQEntry<K, V>(key, value); 
		if (super.isEmpty()) {
			PQEntry<K,V> newEntry = new PQEntry<K,V>(key, value);
			super.addRoot(newEntry);
			insertNode = super.root;
			_size++;
			root.getLeft().setParent(root);
			root.getRight().setParent(root);
			lastNode = root.getLeft();
		} else {
			//add the node to the last position			
			lastNode.setElement(new PQEntry<K,V>(key, value));
			lastNode.setLeft(new BinaryNode<IEntry<K,V>>(lastNode, null, null, null));
			lastNode.setRight(new BinaryNode<IEntry<K,V>>(lastNode, null, null, null));
			insertNode = lastNode;
			lastNode = getLastNodeInsert(lastNode);
			_size++;
		}
		upheap(insertNode);
		return val;
	}

	@Override
	public IEntry<K, V> min() {
		
		return super.root().getElement();
	}

	@Override
	public IEntry<K, V> removeMin() {
		IEntry<K,V> itemToRemove = root().getElement();

		if (_size == 1) {
			root = null;
			_size--;
			return itemToRemove;
		} else {
			lastNode = getLastNodeRemove(lastNode);
			root.setElement(lastNode.getElement());
			lastNode.setElement(null);
			
			if (!isExternal(lastNode)) {
				lastNode.setLeft(null);
				lastNode.setRight(null);
			}
			
			_size--;
			downheap(root);
			return itemToRemove;
		}
	}

	private void upheap(BinaryNode<IEntry<K,V>> node) {
		BinaryNode<IEntry<K,V>> parent = node.getParent();
		if (parent != null) {
			IEntry<K,V> entry = node.getElement();
			IEntry<K,V> entParent = parent.getElement();
			if (comp.compare(entry.getKey(), entParent.getKey()) > 0) {
				PQEntry<K,V> newEntry = new PQEntry<>(entParent.getKey(), entParent.getValue());
				PQEntry<K,V> newParent = new PQEntry<K,V>(entry.getKey(), entry.getValue());
				entry = newEntry;
				entParent = newParent;
				upheap(parent);
			}
		}
	}
	
	private void downheap(BinaryNode<IEntry<K,V>> node) {
		if (node.getLeft().getElement() != null || node.getRight().getElement() != null) {
			BinaryNode<IEntry<K,V>> smallest = null;
			if (node.getLeft().getElement() == null) {
				smallest = node.getRight();
				
			} else if (node.getRight().getElement() == null) {
				smallest = node.getLeft();
			} else {
				BinaryNode<IEntry<K,V>> left = node.getLeft();
				BinaryNode<IEntry<K,V>> right = node.getRight();
				if (comp.compare(left.getElement().getKey(), right.getElement().getKey()) < 0) {
					smallest = left;
				} else {
					smallest = right;
				}
				
			}
			
			IEntry<K,V> entry = node.getElement();
			IEntry<K,V> entChild = smallest.getElement();
			if (comp.compare(entry.getKey(), entChild.getKey())  >= 0) {
				PQEntry<K,V> newEntry = new PQEntry<>(entChild.getKey(), entChild.getValue());
				PQEntry<K,V> newChild = new PQEntry<K,V>(entry.getKey(), entry.getValue());
				entry = newEntry;
				entChild = newChild;
				downheap(smallest);
			}
			
		}
	}
	
	private BinaryNode<IEntry<K,V>> getLastNodeRemove(BinaryNode<IEntry<K,V>> node) {
		BinaryNode<IEntry<K,V>> parent = node.getParent();
		if (parent.getRight() == node) {
			return parent.getLeft();
		}

		BinaryNode<IEntry<K,V>> currentNode = node.getParent();
		parent = currentNode.getParent();
		while (!isRoot(currentNode) && parent.getRight() == currentNode) {
			currentNode = currentNode.getParent();
			parent = currentNode.getParent();
		}
		
		if (!isRoot(currentNode) && parent.getRight() == currentNode) {
			currentNode = (BinaryNode<IEntry<K, V>>) sibling(currentNode);
		}

		while (currentNode.getLeft().getElement() != null || currentNode.getRight().getElement() != null) {
			currentNode = currentNode.getRight();
		}

		return currentNode;
	}
	
	private BinaryNode<IEntry<K,V>> getLastNodeInsert(BinaryNode<IEntry<K,V>> node) {
		BinaryNode<IEntry<K,V>> parent = node.getParent();
		if (parent.getLeft() == node) {
			return (BinaryNode<IEntry<K, V>>) sibling(node);
		}
		
		BinaryNode<IEntry<K,V>> currentNode = node.getParent();
		parent = currentNode.getParent();
		while (!isRoot(currentNode) && parent.getLeft() == currentNode) {
			currentNode = currentNode.getParent();
		}
		
		if (!isRoot(currentNode) && parent.getLeft() == currentNode) {
			currentNode = (BinaryNode<IEntry<K, V>>) sibling(currentNode);
		}

		while (!isExternal(currentNode)) {
			currentNode = currentNode.getLeft();
		}
		return currentNode;
	}
	
}
