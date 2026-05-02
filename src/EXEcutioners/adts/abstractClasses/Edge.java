package EXEcutioners.adts.abstractClasses;

import EXEcutioners.adts.interfaces.IEdge;
import EXEcutioners.adts.interfaces.IPosition;
import EXEcutioners.adts.interfaces.IVertex;

public class Edge<V, E> implements IEdge<V, E>  {
	private E element;
	private IPosition<IEdge<V, E>> pos;
	private IVertex<V>[] endpoints;
	
	public Edge(IVertex<V> a, IVertex<V> b, E elm) {
		this.element = elm;
		endpoints = (IVertex<V>[]) new IVertex[2];
		endpoints[0] = a;
		endpoints[1] = b;
	}
	
	@Override
	public E getElement() {
		return this.element;
	}

	@Override
	public IVertex<V>[] getEndpoints() {
		return this.endpoints;
	}
	
	public void setPosition(IPosition<IEdge<V, E>> p) {
		this.pos = p;
	}
	
	public IPosition<IEdge<V, E>> getPosition(){
		return pos;
	}

}
