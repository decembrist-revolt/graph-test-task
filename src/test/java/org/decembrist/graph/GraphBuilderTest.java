package org.decembrist.graph;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GraphBuilderTest {

	@Test
	public void successBuilderTest() {
		var builder = new GraphBuilder<>();
		builder.directed(true);
		var graph = builder.build();
		assertTrue(graph instanceof DirectedGraph);
		assertEquals(0, graph.size());
		assertThrows(RuntimeException.class, builder::build);

		builder = new GraphBuilder<>();
		builder.directed(false);
		graph = builder.build();
		assertTrue(graph instanceof BiDirectionalGraph);
		assertEquals(0, graph.size());
	}

}
