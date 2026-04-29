package EXEcutioners.adts.interfaces;

public interface IEdge<V, E> {
	
	E getElement();
	IVertex<V>[] getEndpoints();
	IPosition<IEdge<V, E>> getPosition();
}
