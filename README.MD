**Simple Graph lib:**

Should support 2 types of graphs - directed and undirected with 3 operations:

1) addVertex - adds vertex to the graph

2) addEdge - adds edge to the graph

3) getPath - returns a list of edges between 2 vertices (path doesn’t have to be optimal)

4) Vertices should be of a user defined type.

_Questions to be ready to answer (don’t have to implement):_

a) Add weighted edges support in your lib.

b) Add traverse function that will take a user defined function and apply it on every vertex of the graph.

c) Make you graphs thread safe.

**How to use:**

Create graph builder   
```java
var builder = new GraphBuilder<>();
//builder.directed(true);
var graph = builder.build();
```

Add vertex
```java
var vertex = graph.addVertex("test1");
```

Add edge
```java
var vertex1 = graph.addVertex(1);
var vertex2 = graph.addVertex(2);
//edge from vertex1 to vertex2 
vertex1.addEdge(vertex2);
```

Get item
```java
var item = vertex.getItem();
```

Find
```java
final var vertex = graph.addVertex("test1");
final var id = vertex.getId();
vertex.equals(graph.findById(id)); //true
```

```java
final var vertex = graph.addVertex(1);
final var vertices = graph.find(1);
vertex.equals(vertices.stream().findFirst().get()); //true
```
