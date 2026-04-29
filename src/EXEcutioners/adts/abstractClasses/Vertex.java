package EXEcutioners.adts.abstractClasses;

import EXEcutioners.adts.interfaces.IEdge;
import EXEcutioners.adts.interfaces.IMap;
import EXEcutioners.adts.interfaces.IPosition;
import EXEcutioners.adts.interfaces.IVertex;

public class Vertex<V, E> implements IVertex<V>  {
	 private V element;
	 private IPosition <IVertex<V>> pos;
	 private HashMap<IVertex<V>, IEdge<V,E>> outgoing;
	 private HashMap<IVertex<V>, IEdge<V,E>> incoming;
	 
	 public Vertex(V elm, boolean bDirected) {
		 this.element = elm;
		 outgoing = new HashMap<>();
		 
		 if (bDirected == true) {
			 incoming = new HashMap<>();
		 } else {
			 incoming = outgoing;
		 }
		 
	 }
	 
	 @Override
	 public V getElement() {
		return this.element;
	 }
	 @Override
	 public IPosition<IVertex<V>> getPosition() {
		return this.pos;
	 }
	 
	 public void setPosition(IPosition<IVertex<V>> p) {
		 this.pos = p;
	 }
	 
	 public IMap<IVertex<V>, IEdge<V, E>> getOutgoing() {
		 return outgoing;
	}
	 
	 public IMap<IVertex<V>, IEdge<V, E>> getIncoming() {
		 return incoming;
	}
}
