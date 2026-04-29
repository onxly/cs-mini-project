package EXEcutioners.adts.abstractClasses;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

import EXEcutioners.adts.interfaces.IEntry;
import EXEcutioners.adts.interfaces.IMap;

public class TableMap<K, V> implements IMap<K, V> {
	
	private class EntryIterator implements Iterator<IEntry<K,V>>{
		int iCurrent = 0;
		@Override
		public boolean hasNext() {
			if (iCurrent < _size) {
				return true;
			} else {
				return false;
			}	
		}

		@Override
		public IEntry<K, V> next() throws NoSuchElementException {
			if (iCurrent != _size) {
				IEntry<K, V> entry = table.get(iCurrent);
				iCurrent++;
				return entry;
			} else {
				throw new NoSuchElementException("Iterator has no next element");
			}
		}
		
		public void remove() {
			throw new UnsupportedOperationException("Removal using the iterator not allowed");
		}
	}
	
	private class ValueIterator implements Iterator<V>{
		Iterator<IEntry<K, V>> entries = TableMap.this.entrySet().iterator();
		
		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return entries.hasNext();
		}

		@Override
		public V next() throws NoSuchElementException {
			return entries.next().getValue();
		}
		
		public void remove() {
			throw new UnsupportedOperationException("Removal using the iterator not allowed");
		}
		
	}
	
	
	private class KeyIterator implements Iterator<K>{
		Iterator<IEntry<K, V>> entries = TableMap.this.entrySet().iterator();
		
		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return entries.hasNext();
		}

		@Override
		public K next() throws NoSuchElementException {
			return entries.next().getKey();
		}
		
		public void remove() {
			throw new UnsupportedOperationException("Removal using the iterator not allowed");
		}	
	}
	
	private class EntryIterable implements Iterable<IEntry<K,V>>{

		@Override
		public Iterator<IEntry<K, V>> iterator() {
			// TODO Auto-generated method stub
			return new EntryIterator();
		}
		
	}
	
	private class ValueIterable implements Iterable<V>{

		@Override
		public Iterator<V> iterator() {
			// TODO Auto-generated method stub
			return new ValueIterator();
		}
		
	}
	
	private class KeyIterable implements Iterable<K>{

		@Override
		public Iterator<K> iterator() {
			// TODO Auto-generated method stub
			return new KeyIterator();
		}
		
	}
	
	private ArrayList<MapEntry<K,V>> table;
	private int _size = 0;
	
	public TableMap() {
		table = new ArrayList<MapEntry<K, V>>();
	}
	
	private int findIndex(K key) {
		for (int i=0; i < table.size(); i++) {
			if (table.get(i).getKey().equals(key)) {
				return i;
			}
		}
		return -1;
	}
	
	@Override
	public int size() {
		return this._size;
	}

	public boolean containsKey(K key) {
		if (findIndex(key) == -1) {
			return false;
		} else {
			return true;
		}
		
	}

	@Override
	public boolean isEmpty() {
		return _size == 0;
	}
	
	@Override
	public V get(K key) {
		int iIndex = findIndex(key);
		if (iIndex == -1) {
			return null;
		} else {
			return table.get(iIndex).getValue();
		}
	}

	@Override
	public V put(K key, V value) {
		int iIndex = findIndex(key);
		if (iIndex == -1) {
			MapEntry<K,V> newEntry = new MapEntry<K, V>(key, value);
			table.add(newEntry);
			_size++;
			return null;
		} else {
			V oldValue = table.get(iIndex).getValue();
			table.get(iIndex).setValue(value);
			return oldValue;
		}
	}

	@Override
	public V remove(K key) {
		if (this._size != 0) {
			int iIndex = findIndex(key);
			if (iIndex == -1) {
				return null;
			} else {
				V oldValue = table.get(iIndex).getValue();
				table.remove(iIndex);
				return oldValue;
			}
		} 
		return null;
	}

	@Override
	public Iterable<K> keySet() {
		return new KeyIterable();
	}

	@Override
	public Iterable<V> values() {
		return new ValueIterable();
	}

	@Override
	public Iterable<IEntry<K, V>> entrySet() {
		return new EntryIterable();
	}

}
