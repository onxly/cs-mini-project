package EXEcutioners.adts.abstractClasses;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

import EXEcutioners.adts.interfaces.IEntry;
import EXEcutioners.adts.interfaces.IMap;

public class HashMap<K, V> implements IMap<K, V> {
	
	private class ValueIterator implements Iterator<V>{
		Iterator<IEntry<K, V>> entries = HashMap.this.entrySet().iterator();
		
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
		Iterator<IEntry<K, V>> entries = HashMap.this.entrySet().iterator();
		
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
	
	private TableMap<K,V>[] table;
	private int _size = 0;
	private int capacity = 0;
	private int prime;
	private long shift;
	private long scale;
	
	public HashMap(int cap, int p)
	{
		prime=p;
		capacity=cap;
		Random rand = new Random();
		scale = rand.nextInt(prime -1) + 1;
		shift = rand.nextInt(prime);
		createTable();
	}
	
	public HashMap(int cap){
		prime= 109345121;
		capacity=cap;
		Random rand = new Random();
		scale = rand.nextInt(prime -1) + 1;
		shift = rand.nextInt(prime);
		createTable();
	}
	
	public HashMap(){
		prime= 109345121;
		capacity=17;
		Random rand = new Random();
		scale = rand.nextInt(prime -1) + 1;
		shift = rand.nextInt(prime);
		createTable();
	}
	

	
	private int hashValue(K key)
	{	
		return (int) ((Math.abs(key.hashCode()*scale + shift ) % prime) % capacity) ;	
	}
	
	
	private void createTable() {};
	private V bucketGet(int h,K k) 
	{
		TableMap<K,V> bucket= table[h];
		if (bucket==null) {
			return null;
		}
		return bucket.get(k);
		
	}
		
		
	private V bucketPut(int h,K k,V v) {
		TableMap<K,V>bucket = table[h];
		if (bucket == null) {
			table[h]= new TableMap<>();
			bucket = table[h];
		}
		int oldSize = bucket.size();
		V answer = bucket.put(k,v);
		this._size += bucket.size() - oldSize;
		return answer;
	}
		
		
	private V bucketRemove(int h,K k) {
		TableMap<K,V> bucket = table[h];
		if(bucket==null) {
			return null;
		}
		int oldSize = bucket.size();
		V answer = bucket.remove(k);
		this._size -= (oldSize-bucket.size()); 
		return answer;
	}
	

	@Override
	public int size() {
		return _size;
	}

	@Override
	public boolean isEmpty() {
		return _size ==0;
	}

	@Override
	public V get(K key) {
		return bucketGet(hashValue(key), key);
	}

	@Override
	public V put(K key, V value) {
		V oldValue = bucketPut(hashValue(key), key, value);
		
		if (_size > capacity) {
			resize(2 * capacity - 1);
		}
		
		return oldValue;
	}
	
	 private void resize(int newCap) {
		 ArrayList<IEntry<K,V>> buffer = new ArrayList<>(_size);
		 
		 for (IEntry<K,V> entry : entrySet()) {
			 buffer.add(entry);
		 }

		 capacity = newCap;
		 createTable(); 
		 _size = 0;
		 for(IEntry<K,V> entry : buffer)
		 {
			 put(entry.getKey(),entry.getValue());
		 }
		
	}

	@Override
	public V remove(K key) {
		return bucketRemove(hashValue(key), key);
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
		ArrayList<IEntry<K,V>> buffer = new ArrayList<>();
		for(int i=0; i<capacity; i++) {
			if(table[i] !=null) {
				for(IEntry<K,V> entry: table[i].entrySet()) {
					buffer.add(entry);
				}
			}
		}
	
		return buffer;
	}

}
