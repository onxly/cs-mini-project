package EXEcutioners.adts.interfaces;

public interface IEdge<V> {
	V getElement();
	IVertex<V>[] getEndpoints();
}
