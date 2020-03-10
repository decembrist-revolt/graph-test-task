package org.decembrist.graph;

import java.util.List;
import java.util.Set;

/**
 * Base graph interface
 * @param <T> graph item class
 */
public interface Graph<T> {

	/**
	 * @param item vertex to add
	 * @return created vertex
	 */
	Vertex<T> addVertex(T item);

	/**
	 * @param id internal vertex id
	 * @return vertex with id
	 */
	Vertex<T> findById(String id);

	/**
	 * @param object to find
	 * @return vertex collection with item equals object
	 */
	Set<Vertex<T>> find(T object);

	/**
	 * @param id internal vertex id
	 * @return true if graph contains vertex with id
	 */
	boolean hasId(String id);

	/**
	 * @return graph vertex count
	 */
	int size();

	/**
	 * @param vertex1 start
	 * @param vertex2 end
	 * @return path from vertex1 to vertex2, or empty list
	 */
	List<Vertex<T>> getPath(Vertex<T> vertex1, Vertex<T> vertex2);

	/**
	 * Prints path from vertex1 to vertex2 to console
	 *
	 * @param vertex1 start
	 * @param vertex2 end
	 */
	void printPath(Vertex<T> vertex1, Vertex<T> vertex2);

}
