package org.decembrist.graph;

/**
 * Undirected graph implementation
 */
public class BiDirectionalGraph<T> extends DirectedGraph<T> {

	BiDirectionalGraph() {
	}

	@Override
	protected DefaultVertex<T> newVertex(String id, T item) {
		return new BiDirectionalVertex<>(id, item, this);
	}

	/**
	 * Undirected graph vertex class
	 */
	static class BiDirectionalVertex<T> extends DefaultVertex<T> {

		public BiDirectionalVertex(String id, T item, Graph<T> graph) {
			super(id, item, graph);
		}

		@Override
		public Vertex<T> addEdge(Vertex<T> vertex) {
			if (!connected.contains(vertex)) {
				super.addEdge(vertex);
				vertex.addEdge(this);
			}
			return this;
		}
	}

}
