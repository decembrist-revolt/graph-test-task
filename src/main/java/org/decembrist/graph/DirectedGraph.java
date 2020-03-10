package org.decembrist.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Directed graph implementation
 */
public class DirectedGraph<T> implements Graph<T> {

	final Map<String, DefaultVertex<T>> vertices = new HashMap<>();

	DirectedGraph() {
	}

	@Override
	public Vertex<T> addVertex(T item) {
		final var id = UUID.randomUUID().toString();
		final var vertex = newVertex(id, item);
		vertices.put(id, vertex);
		return vertex;
	}

	@Override
	public Vertex<T> findById(String id) {
		return vertices.get(id);
	}

	@Override
	public Set<Vertex<T>> find(T object) {
		return vertices.values().stream()
				.filter(vertex -> vertex.getItem().equals(object))
				.collect(Collectors.toSet());
	}

	@Override
	public boolean hasId(String id) {
		return vertices.containsKey(id);
	}

	@Override
	public int size() {
		return vertices.size();
	}

	@Override
	public List<Vertex<T>> getPath(Vertex<T> vertex1, Vertex<T> vertex2) {
		final var defaultVertex1 = ensureInGraph(vertex1);
		final var defaultVertex2 = ensureInGraph(vertex2);
		if (vertex1.equals(vertex2)) {
			return Collections.singletonList(vertex1);
		}
		if (defaultVertex1.connected.size() == 0) {
			return Collections.emptyList();
		}
		final var path = walkVertices(defaultVertex1, defaultVertex2);
		return new ArrayList<>(path);
	}

	@Override
	public void printPath(Vertex<T> vertex1, Vertex<T> vertex2) {
		final var defaultVertex1 = ensureInGraph(vertex1);
		final var defaultVertex2 = ensureInGraph(vertex2);
		final var path = walkVertices(defaultVertex1, defaultVertex2);
		final var pathString = path.stream()
				.map(DefaultVertex::getItem)
				.map(Objects::toString)
				.collect(Collectors.joining(" -> "));
		System.out.println(pathString);
	}

	protected DefaultVertex<T> newVertex(String id, T item) {
		return new DefaultVertex<T>(id, item, this);
	}

	protected Stack<DefaultVertex<T>> walkVertices(DefaultVertex<T> vertex1,
												   DefaultVertex<T> vertex2) {
		Set<DefaultVertex<T>> visited = new HashSet<>();
		Stack<DefaultVertex<T>> path = new Stack<>();
		path.push(vertex1);
		visited.add(vertex1);
		Stack<Iterator<DefaultVertex<T>>> iteratorStack = new Stack<>();
		iteratorStack.push(vertex1.connected.iterator());
		while (iteratorStack.size() > 0) {
			final var vertex = iteratorStack.peek().next();
			if (vertex.equals(vertex2)) {
				path.push(vertex);
				break;
			} else if (!visited.contains(vertex) && !vertex.connected.isEmpty()) {
				path.push(vertex);
				iteratorStack.push(vertex.connected.iterator());
			} else if (!iteratorStack.peek().hasNext()) {
				while (iteratorStack.size() > 0 && !iteratorStack.peek().hasNext()) {
					iteratorStack.pop();
					path.pop();
				}
			}
			visited.add(vertex);
		}
		return path;
	}

	private DefaultVertex<T> ensureInGraph(Vertex<T> vertex) {
		final var id = vertex.getId();
		if (!hasId(id) || !(vertex instanceof DefaultVertex)) {
			final var message = String.format("Graph %s has no vertex key %s", this, id);
			throw new RuntimeException(message);
		}
		return (DefaultVertex<T>) vertex;
	}
}
