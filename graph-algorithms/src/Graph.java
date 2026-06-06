import java.util.*;

class Graph {
    private final int V;
    private final List<Integer>[] adj;

    @SuppressWarnings("unchecked")
    public Graph(int V) {
        this.V = V;
        adj = (List<Integer>[]) new List[V];
        for (int i = 0; i < V; i++) adj[i] = new ArrayList<>();
    }

    public void addEdge(int u, int v) {
        if (u < 0 || u >= V || v < 0 || v >= V || u == v) return;
        if (!adj[u].contains(v)) adj[u].add(v);
    }

    public int V() { return V; }

    public Iterable<Integer> adj(int v) {
        List<Integer> list = new ArrayList<>(adj[v]);
        Collections.sort(list);
        return list;
    }
}