package EXEcutioners.adts.abstractClasses;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import EXEcutioners.adts.interfaces.IEdge;
import EXEcutioners.adts.interfaces.IGraph;
import EXEcutioners.adts.interfaces.IPosition;
import EXEcutioners.adts.interfaces.IPositionalList;
import EXEcutioners.adts.interfaces.IVertex;

public class MapGraph<V, E> implements IGraph<V, E>{
	protected boolean isDirected;
	protected LinkedPositionalList<IVertex<V>> vertices = new LinkedPositionalList<>();
	protected LinkedPositionalList<IEdge<V,E>> edges = new LinkedPositionalList<>();
	
	
	public MapGraph(boolean bDirected) {
		this.isDirected = bDirected;
	}
	
	private Edge<V, E> validateEdge(IEdge<V, E> e) throws IllegalArgumentException{
		if (e instanceof Edge) {
			Edge<V, E> edge = (Edge<V, E>) e;
			return edge;
		} else {
			throw new IllegalArgumentException("Argument given is not of type Edge");
		}
	}
	
	private Vertex<V, E> validateVertex(IVertex<V> v) throws IllegalArgumentException{
		if (v instanceof Vertex) {
			Vertex<V, E> vertex = (Vertex<V, E>) v;
			return vertex;
		} else {
			throw new IllegalArgumentException("Argument given is not of type Vertex");
		}
	}
	
	@Override
	public int numVertices() {
		return vertices.size();
	}

	@Override
	public int numEdges() {
		return vertices.size();
	}

	@Override
	public int outDegree(IVertex<V> vertex) {
		Vertex<V, E> newVertex = validateVertex(vertex);
		
		return newVertex.getOutgoing().size();
	}

	@Override
	public int inDegree(IVertex<V> vertex) {
		Vertex<V, E> newVertex = validateVertex(vertex);
		
		return newVertex.getIncoming().size();
	}

	@Override
	public Iterable<IVertex<V>> vertices() {
		// TODO Auto-generated method stub
		return vertices;
	}

	@Override
	public Iterable<IEdge<V, E>> edges() {
		return edges;
	}

	@Override
	public Iterable<IEdge<V, E>> outgoingEdges(IVertex<V> vertex) {
		Vertex<V, E> newVertex = validateVertex(vertex);
		return newVertex.getOutgoing().values();
	}
	
	@Override
	public Iterable<IEdge<V, E>> incomingEdges(IVertex<V> vertex) {
		Vertex<V, E> newVertex = validateVertex(vertex);
		return newVertex.getIncoming().values();
	}

	@Override
	public IVertex<V>[] endVertices(IEdge<V, E> edge) {
		Edge<V, E> newEdge = validateEdge(edge);
		return newEdge.getEndpoints();
	}

	@Override
	public IVertex<V> opposite(IVertex<V> vertex, IEdge<V, E> edge) throws NoSuchElementException {
		Edge<V, E> newEdge = validateEdge(edge);
		IVertex<V>[] endpoints = newEdge.getEndpoints();
		
		if (endpoints[0] == vertex) {
			return endpoints[0];
		} else if (endpoints[1] == vertex) {
			return endpoints[1];
		} else {
			throw new NoSuchElementException("The vertex is not incident to this edge");
		}
		
	}

	@Override
	public Vertex<V, E> insertVertex(V elm) {
		Vertex<V, E> vertex = new Vertex<>(elm, this.isDirected);
		vertex.setPosition(vertices.addLast(vertex));
		return vertex;
	}

	@Override
	public IEdge<V, E> insertEdge(IVertex<V> start, IVertex<V> end, E elm) throws IllegalArgumentException {
		
		if (getEdge(start, end) == null) {
			Edge<V, E> edge = new Edge<V, E>(start, end, elm);
			edge.setPosition(edges.addLast(edge));
			Vertex<V, E> origin = validateVertex(start);
			Vertex<V, E> dest = validateVertex(end);
			
			origin.getOutgoing().put(end, edge);
			dest.getIncoming().put(start, edge);
			return edge;
		} else {
			throw new IllegalArgumentException("Edge from start to end already exists");
		}
	}

	@Override
	public V removeVertex(IVertex<V> vertex) {
		Vertex<V, E> newVertex = validateVertex(vertex);
		V oldV = newVertex.getElement();
		for(IEdge<V, E> e : newVertex.getOutgoing().values()) {
			removeEdge(e);
		}
		for(IEdge<V, E> e :newVertex.getIncoming().values()) {
			removeEdge(e);
		}
		vertices.remove(vertex.getPosition());
		return oldV;
	}

	@Override
	public E removeEdge(IEdge<V, E> edge) {
		Edge<V, E> newEdge = validateEdge(edge);
		E oldElm = newEdge.getElement();
	    if (newEdge != null) {
	    	IVertex<V>[] points = newEdge.getEndpoints();
	    	Vertex<V, E> source = validateVertex(points[0]);
	    	Vertex<V, E> destination = validateVertex(points[0]);

		    HashMap<IVertex<V>, IEdge<V, E>> srcEdges = (HashMap<IVertex<V>, IEdge<V, E>>) source.getOutgoing();
		    HashMap<IVertex<V>, IEdge<V, E>> destEdges = (HashMap<IVertex<V>, IEdge<V, E>>) source.getOutgoing();
		    srcEdges.remove(destination);
		    destEdges.remove(source);
		    edges.remove(edge.getPosition());
		    return oldElm;
	    } else {
	    	return null;
	    }

	    
	}

	@Override
	public IEdge<V, E> getEdge(IVertex<V> v1, IVertex<V> v2) {
		Vertex<V, E> vertex = validateVertex(v1);
		if (vertex.getOutgoing() != null ) {	

			return vertex.getOutgoing().get(v2);
		
		} else {
			return null;
		}
		
	}

}
