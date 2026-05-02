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
			root = new BinaryNode<IEntry<K,V>>(null, new BinaryNode<IEntry<K,V>>(null, null, null, null), 
					new BinaryNode<IEntry<K,V>>(null, null, null, null),
					new PQEntry<K,V>(key, value));
			root.getLeft().setParent(root);
			root.getRight().setParent(root);
			insertNode = super.root;
			_size++;
			
			lastNode = root.getLeft();
		} else {
			//add the node to the last position			
			lastNode.setElement(new PQEntry<K,V>(key, value));
		    
		    // 2. Create the new empty "External Nodes" for future insertions
		    lastNode.setLeft(new BinaryNode<IEntry<K,V>>(lastNode, null, null, null));
		    lastNode.setRight(new BinaryNode<IEntry<K,V>>(lastNode, null, null, null));
		    
		    // 3. Move the pointer to the newly filled node, then calculate the next empty spot
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
	    
	    if (parent == null || parent.getElement() == null) {
	        return;
	    }

	    if (comp.compare(node.getElement().getKey(), parent.getElement().getKey()) < 0) {
	        IEntry<K,V> temp = node.getElement();
	        node.setElement(parent.getElement());
	        parent.setElement(temp);
	        
	        upheap(parent);
	    }
	}
	
	private void downheap(BinaryNode<IEntry<K,V>> node) {
	    if (node == null || node.getElement() == null) return;

	    boolean hasLeft = node.getLeft() != null && node.getLeft().getElement() != null;
	    boolean hasRight = node.getRight() != null && node.getRight().getElement() != null;

	    if (!hasLeft && !hasRight) return;

	    BinaryNode<IEntry<K,V>> smallest = null;

	    if (hasLeft && !hasRight) {
	        smallest = node.getLeft();
	    } else if (!hasLeft && hasRight) {
	        smallest = node.getRight();
	    } else {
	       
	        if (comp.compare(node.getLeft().getElement().getKey(), 
	                         node.getRight().getElement().getKey()) < 0) {
	            smallest = node.getLeft();
	        } else {
	            smallest = node.getRight();
	        }
	    }


	    if (smallest != null && comp.compare(node.getElement().getKey(), 
	                                         smallest.getElement().getKey()) > 0) {
	        IEntry<K,V> temp = node.getElement();
	        node.setElement(smallest.getElement());
	        smallest.setElement(temp);
	        
	        // Continue down the tree
	        downheap(smallest);
	    }
	}
	
	private BinaryNode<IEntry<K,V>> getLastNodeRemove(BinaryNode<IEntry<K,V>> node) {
	    BinaryNode<IEntry<K,V>> p = node.getParent();
	    
	    
	    if (p != null && p.getRight() == node) {
	        return p.getLeft();
	    }

	    BinaryNode<IEntry<K,V>> curr = node;
	    while (curr.getParent() != null && curr.getParent().getLeft() == curr) {
	        curr = curr.getParent();
	    }

	    if (curr.getParent() != null) {
	        curr = curr.getParent().getLeft();
	    }

	    
	    while (curr.getRight() != null && curr.getRight().getElement() != null) {
	        curr = curr.getRight();
	    }
	    return curr;
	}
	
	private BinaryNode<IEntry<K,V>> getLastNodeInsert(BinaryNode<IEntry<K,V>> node) {
	    BinaryNode<IEntry<K,V>> p = node.getParent();
	    if (p != null && p.getLeft() == node) {
	        return p.getRight(); 
	    }

	    
	    BinaryNode<IEntry<K,V>> curr = node;
	    while (curr.getParent() != null && curr.getParent().getRight() == curr) {
	        curr = curr.getParent();
	    }


	    if (curr.getParent() != null) {
	        curr = curr.getParent().getRight();
	    }

	    while (curr.getLeft() != null) {
	        curr = curr.getLeft();
	    }
	    return curr;
	}
	
}
