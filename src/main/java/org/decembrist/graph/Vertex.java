package org.decembrist.graph;

/**
 * Base vertex interface
 * @param <T> graph item class
 */
public interface Vertex<T> {

	/**
	 * @return internal vertex id
	 */
	String getId();

	/**
	 * @return vertex item
	 */
	T getItem();

	/**
	 * @param vertex to make edge to
	 * @return this
	 */
	Vertex<T> addEdge(Vertex<T> vertex);

}
