package EXEcutioners.adts.interfaces;

import java.util.Iterator;

public interface ITree<E> {
	IPosition<E> root();
	IPosition<E> parent(IPosition<E> p) throws IllegalArgumentException;
	Iterable<IPosition<E>> children(IPosition<E> p) throws IllegalArgumentException;
	int numChildren(IPosition<E> p) throws IllegalArgumentException;
	boolean isInternal(IPosition<E> p) throws IllegalArgumentException;
	boolean isExternal(IPosition<E> p) throws IllegalArgumentException;
	boolean isRoot(IPosition<E> p) throws IllegalArgumentException;
	int size();
	boolean isEmpty();
	Iterator<E> iterator();
	Iterable<IPosition<E>> positions();

}
