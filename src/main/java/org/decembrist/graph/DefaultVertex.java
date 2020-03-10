package org.decembrist.graph;

import java.util.HashSet;
import java.util.Set;

/**
 * Simple directed vertex implementation
 */
class DefaultVertex<T> implements Vertex<T> {

	private String id;
	private T item;
	private Graph<T> graph;

	Set<DefaultVertex<T>> connected = new HashSet<>();

	/**
	 * @param id internal vertex id
	 * @param item vertex item
	 * @param graph parent graph
	 */
	public DefaultVertex(String id, T item, Graph<T> graph) {
		this.id = id;
		this.item = item;
		this.graph = graph;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public T getItem() {
		return item;
	}

	@Override
	public Vertex<T> addEdge(Vertex<T> vertex) {
		if (!graph.hasId(id) || !(vertex instanceof DefaultVertex)) {
			final var message = String.format("Graph %s has no vertex key %s", graph, id);
			throw new RuntimeException(message);
		}
		connected.add((DefaultVertex<T>) vertex);
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof DefaultVertex)) return false;

		DefaultVertex<?> that = (DefaultVertex<?>) o;

		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

}
