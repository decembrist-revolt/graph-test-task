package org.decembrist.graph;

import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BiDirectionalGraphTest {

	@Test
	public void addVertexTest() {
		final var biDirectionalGraph = new BiDirectionalGraph<String>();
		final var testVertex1 = biDirectionalGraph.addVertex("test1");
		final var testVertex2 = biDirectionalGraph.addVertex("test2");
		final var testVertex3 = biDirectionalGraph.addVertex("test3");
		testVertex1.addEdge(testVertex2);
		testVertex1.addEdge(testVertex3);

		assertEquals(3, biDirectionalGraph.size());
		assertTrue(testVertex1 instanceof BiDirectionalGraph.BiDirectionalVertex);
		var defaultVertex1 = (DefaultVertex<String>) testVertex1;
		var defaultVertex2 = (DefaultVertex<String>) testVertex2;
		var defaultVertex3 = (DefaultVertex<String>) testVertex3;
		assertEquals(2, defaultVertex1.connected.size());
		assertEquals(1, defaultVertex2.connected.size());
		assertEquals(1, defaultVertex3.connected.size());
		assertEquals(testVertex1, defaultVertex2.connected.stream().findFirst().get());
		assertEquals(testVertex1, defaultVertex3.connected.stream().findFirst().get());
		assertEquals("test1", defaultVertex1.getItem());
		final var connectedItemsString = defaultVertex1.connected.stream()
				.map(Vertex::getItem)
				.sorted()
				.collect(Collectors.joining(","));
		assertEquals("test2,test3", connectedItemsString);
	}

	/**
	 * test1 <-> test2 <-> test3
	 * \<-> test4
	 */
	@Test
	public void getPathTest1() {
		final var directedGraph = new BiDirectionalGraph<String>();
		final var testVertex1 = directedGraph.addVertex("test1");
		final var testVertex2 = directedGraph.addVertex("test2");
		testVertex1.addEdge(testVertex2);
		final var testVertex3 = directedGraph.addVertex("test3");
		testVertex2.addEdge(testVertex3);
		final var testVertex4 = directedGraph.addVertex("test4");
		testVertex2.addEdge(testVertex4);
		final var path = directedGraph.getPath(testVertex1, testVertex4);
		final var pathString = path.stream()
				.map(Vertex::getItem)
				.collect(Collectors.joining(" -> "));
		assertEquals("test1 -> test2 -> test4", pathString);
	}

	/**
	 * test1 <-> test2 <-> test3 <->/
	 * \             /
	 * \ <-> test4
	 * \<-> test5
	 */
	@Test
	public void getPathTest2() {
		final var directedGraph = new BiDirectionalGraph<String>();
		final var testVertex1 = directedGraph.addVertex("test1");
		final var testVertex2 = directedGraph.addVertex("test2");
		testVertex1.addEdge(testVertex2);
		final var testVertex3 = directedGraph.addVertex("test3");
		testVertex2.addEdge(testVertex3);
		final var testVertex4 = directedGraph.addVertex("test4");
		testVertex2.addEdge(testVertex4);
		testVertex4.addEdge(testVertex3);
		final var testVertex5 = directedGraph.addVertex("test5");
		testVertex4.addEdge(testVertex5);
		final var path = directedGraph.getPath(testVertex1, testVertex5);
		final var pathString = path.stream()
				.map(Vertex::getItem)
				.collect(Collectors.joining(" -> "));
		assertTrue("test1 -> test2 -> test3 -> test4 -> test5".equals(pathString)
				|| "test1 -> test2 -> test4 -> test5".equals(pathString));
	}

}
