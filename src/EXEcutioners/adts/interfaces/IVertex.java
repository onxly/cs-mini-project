package EXEcutioners.adts.interfaces;

public interface IVertex<V> {
	V getElement();
	IPosition<IVertex<V>> getPosition();
}
