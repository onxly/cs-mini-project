package EXEcutioners.adts.abstractClasses;

import EXEcutioners.adts.interfaces.IEntry;

public class PQEntry<K,V> implements IEntry<K,V> {
	 private K k; //key
	 private V v; //value
	 public PQEntry(K key, V value){
		 k=key;
		 v=value;
	 }
	 
	 public K getKey(){
		 return k;
	}
	 
	public V getValue() {
		return v;
	}
	
	
	public void setKey(K key){
		k=key;
	}
	
	public void setValue(V value){
		v=value;
	}
}
