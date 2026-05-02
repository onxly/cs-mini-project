package EXEcutioners.adts.interfaces;

public interface IVertex<V> {
	V getElement();
	String getName();
	IPosition<IVertex<V>> getPosition();
}
