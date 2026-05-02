package EXEcutioners.adts.interfaces;

import EXEcutioners.adts.abstractClasses.HashMap;

public interface IVertex<V> {
	V getElement();
	String getName();
	IPosition<IVertex<V>> getPosition();
}
