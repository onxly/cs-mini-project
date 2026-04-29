package EXEcutioners.adts.interfaces;

import java.util.Iterator;

public interface IMap<K, V> {
	int size();
	boolean isEmpty();
	V get(K key);
	V put(K key, V value);
	V remove(K key);
	Iterable<K> keySet();
	Iterable<V> values();
	Iterable<IEntry<K,V>>entrySet();
}
