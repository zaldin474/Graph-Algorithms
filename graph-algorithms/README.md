# Graph Algorithms

A collection of Java implementations of classic **graph algorithms** covering bipartiteness, topological sorting, and minimum spanning trees.

## Algorithms Implemented

### 1. Graph Bipartiteness (2-Coloring)
- BFS-based algorithm to determine if a graph is bipartite
- Detects and reconstructs odd-length cycles

### 2. Topological Sort
- DFS-based topological ordering of directed acyclic graphs

### 3. Road Lighting Optimization
- Minimum Spanning Tree (Kruskal's algorithm with Union-Find)
- Finds maximum road length that can be turned off while keeping connectivity

### 4. Milk Delivery (Shortest Path)
- Dijkstra's algorithm for shortest path in weighted graphs

## Concepts Demonstrated
- BFS and DFS traversal
- Union-Find (Disjoint Set Union)
- Kruskal's MST algorithm
- Dijkstra's shortest path

## How to Run
```
javac src/*.java -d bin/
java -cp bin Main < input.txt
```
