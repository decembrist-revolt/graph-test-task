package org.decembrist.graph;

/**
 * Build graph with selected options
 */
public class GraphBuilder<T> {

	private static String UNSUPPORTED_MESSAGE = "weighted edges unsupported yet";

	private boolean isDirected = false;
	private boolean isBuilt = false;

	/**
	 * @param isDirected should graph be directed
	 * @return this
	 */
	public GraphBuilder<T> directed(boolean isDirected) {
		this.isDirected = isDirected;
		return this;
	}

	/**
	 * @param weighted should graph be weighted
	 * @return this
	 */
	public GraphBuilder<T> weighted(boolean weighted) {
		if (weighted) {
			throw new UnsupportedOperationException(UNSUPPORTED_MESSAGE);
		}
		return this;
	}

	/**
	 * @param threadSafe should graph be threadSafe
	 * @return this
	 */
	public GraphBuilder<T> threadSafe(boolean threadSafe) {
		if (threadSafe) {
			throw new UnsupportedOperationException(UNSUPPORTED_MESSAGE);
		}
		return this;
	}

	/**
	 * Can only be called once
	 * @return ready to use graph with selected options
	 */
	public Graph<T> build() {
		Graph<T> graph;
		if (!isBuilt) {
			if (isDirected) {
				graph = new DirectedGraph<T>();
			} else {
				graph = new BiDirectionalGraph<T>();
			}
		} else {
			throw new RuntimeException("Builder built already");
		}
		isBuilt = true;
		return graph;
	}

}
