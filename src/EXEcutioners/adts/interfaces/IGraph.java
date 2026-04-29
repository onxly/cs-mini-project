package EXEcutioners.adts.interfaces;
import java.util.NoSuchElementException;


public interface IGraph<V, E> {
	int numVertices();
	int numEdges();
	int outDegree(IVertex<V> vertex);
	int inDegree(IVertex<V> vertex);
	Iterable<IVertex<V>> vertices();
	Iterable<IEdge<V,E>> edges();
	Iterable<IEdge<V, E>> outgoingEdges(IVertex<V> vertex);
	Iterable<IEdge<V, E>> incomingEdges(IVertex<V> vertex);
	IVertex<V>[] endVertices(IEdge<V, E> edge);
	IVertex<V> opposite(IVertex<V> vertex, IEdge<V, E> edge) throws NoSuchElementException;
	IEdge<V, E> getEdge(IVertex<V> v1, IVertex<V> v2);
	IVertex<V> insertVertex(V elm);
	IEdge<V, E> insertEdge(IVertex<V> v1, IVertex<V> v2, E elm) throws IllegalArgumentException;
	V removeVertex(IVertex<V> vertex);
	E removeEdge(IEdge<V, E> edge);
	
}
