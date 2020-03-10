package org.decembrist.graph;

import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DirectedGraphTest {

	@Test
	public void addVertexTest() {
		final var directedGraph = new DirectedGraph<String>();
		final var testVertex1 = directedGraph.addVertex("test1");
		final var testVertex2 = directedGraph.addVertex("test2");
		final var testVertex3 = directedGraph.addVertex("test3");
		testVertex1
				.addEdge(testVertex2)
				.addEdge(testVertex3);

		assertEquals(3, directedGraph.size());
		assertTrue(testVertex1 instanceof DefaultVertex);
		var defaultVertex1 = (DefaultVertex<String>) testVertex1;
		assertEquals(2, defaultVertex1.connected.size());
		assertEquals("test1", defaultVertex1.getItem());
		final var connectedItemsString = defaultVertex1.connected.stream()
				.map(Vertex::getItem)
				.sorted()
				.collect(Collectors.joining(","));
		assertEquals("test2,test3", connectedItemsString);
	}

	@Test
	public void hasIdTest() {
		final var directedGraph = new DirectedGraph<String>();
		final var testVertex1 = directedGraph.addVertex("test1");
		final var id = testVertex1.getId();
		assertTrue(directedGraph.hasId(id));
		assertFalse(directedGraph.hasId("fake"));
	}

	@Test
	public void findByIdTest() {
		final var directedGraph = new DirectedGraph<Integer>();
		final var testVertex1 = directedGraph.addVertex(1);
		final var id = testVertex1.getId();
		assertEquals(testVertex1, directedGraph.findById(id));
	}

	@Test
	public void findTest() {
		final var directedGraph = new DirectedGraph<Integer>();
		final var testVertex1 = directedGraph.addVertex(1);
		final var vertices = directedGraph.find(1);
		assertEquals(1, vertices.size());
		assertEquals(testVertex1, vertices.stream().findFirst().get());
	}

	/**
	 * test1 -> test2 -> test3
	 *               \-> test4
	 */
	@Test
	public void getPathTest1() {
		final var directedGraph = new DirectedGraph<String>();
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
	 * test1 -> test2 -> test3 <-/
	 *              \           /
	 *               \-> test4
	 *                       \-> test5
	 */
	@Test
	public void getPathTest2() {
		final var directedGraph = new DirectedGraph<String>();
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
		assertEquals("test1 -> test2 -> test4 -> test5", pathString);
	}

	/**
	 * test1 <-> test2 -> test3 <-/
	 * 				\           /
	 *               \<-> test4
	 *                		 \ <- test5
	 */
	@Test
	public void getPathTest3() {
		final var directedGraph = new DirectedGraph<String>();
		final var testVertex1 = directedGraph.addVertex("test1");
		final var testVertex2 = directedGraph.addVertex("test2");
		testVertex1.addEdge(testVertex2);
		testVertex2.addEdge(testVertex1);
		final var testVertex3 = directedGraph.addVertex("test3");
		testVertex2.addEdge(testVertex3);
		final var testVertex4 = directedGraph.addVertex("test4");
		testVertex2.addEdge(testVertex4);
		testVertex4
				.addEdge(testVertex2)
				.addEdge(testVertex3);
		final var testVertex5 = directedGraph.addVertex("test5");
		testVertex5.addEdge(testVertex4);
		var path = directedGraph.getPath(testVertex1, testVertex5);
		assertEquals(0, path.size());
		path = directedGraph.getPath(testVertex5, testVertex1);
		final var pathString = path.stream()
				.map(Vertex::getItem)
				.collect(Collectors.joining(" -> "));
		assertEquals("test5 -> test4 -> test2 -> test1", pathString);
	}

}
