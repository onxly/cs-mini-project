package EXEcutioners.adts.abstractClasses;

import java.io.Serializable;

import EXEcutioners.adts.interfaces.IEntry;

public class MapEntry<K, V> implements IEntry<K, V>, Serializable {
	private K key;
	private V value;
	
	public MapEntry(K k, V v) {
		this.key = k;
		this.value = v;
	}
	
	@Override
	public K getKey() {
		return this.key;
	}

	@Override
	public V getValue() {
		return this.value;
	}
	
	public void setKey(K k) {
		this.key = k;
	}
	
	public V setValue(V v) {
		V oldValue = value;
		this.value = v;
		return oldValue;
	}

}
