package EXEcutioners.adts.interfaces;

public interface IPositionalList<E> {
	IPosition<E> first();
	IPosition<E> last();
	IPosition<E> before(IPosition<E> pos);
	IPosition<E> after(IPosition<E> pos);
	boolean isEmpty();
	int size();
	IPosition<E> addFirst(E elm);
	IPosition<E> addLast(E elm);
	IPosition<E> addBefore(IPosition<E> pos, E elm);
	IPosition<E> addAfter(IPosition<E> pos, E elm);
	E set(IPosition<E> pos, E elm);
	E remove(IPosition<E> pos);
}
