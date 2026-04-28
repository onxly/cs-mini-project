package EXEcutioners.adts.interfaces;

import java.util.Iterator;

public interface IMap<K, V> {
	int size();
	boolean containsKey(K key);
	boolean isEmpty();
	V get(K key);
	V put(K key, V value);
	V remove(K key);
	Iterator<K> keySet();
	Iterator<V> values();
	Iterator<IEntry<K,V>>entrySet();
}
