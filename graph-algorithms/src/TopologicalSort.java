import java.util.*;
class TopologicalSort {
    private final boolean[] visited;
    private final boolean[] onStack;
    private final int[] edgeTo;
    private final List<Integer> reversePost;
    private List<Integer> cycle;

    public TopologicalSort(Graph G) {
        int V = G.V();
        visited = new boolean[V];
        onStack = new boolean[V];
        edgeTo = new int[V];
        reversePost = new ArrayList<>();

        // Process vertices in ascending order for deterministic output
        for (int v = 0; v < V; v++)
            if (!visited[v]) dfs(G, v);
    }

    private void dfs(Graph G, int v) {
        onStack[v] = true;
        visited[v] = true;
        for (int w : G.adj(v)) {
            if (cycle != null) return;
            else if (!visited[w]) {
                edgeTo[w] = v;
                dfs(G, w);
            } else if (onStack[w]) {
                cycle = new ArrayList<>();
                cycle.add(w);
                for (int x = v; x != w; x = edgeTo[x]) cycle.add(x);
                cycle.add(w);
                Collections.reverse(cycle);
                return;
            }
        }
        onStack[v] = false;
        reversePost.add(v);
    }

    public boolean hasCycle() { return cycle != null; }
    public Iterable<Integer> cycle() { return cycle; }
    public Iterable<Integer> order() {
        if (hasCycle()) return null;
        List<Integer> order = new ArrayList<>(reversePost);
        Collections.reverse(order);
        return order;
    }
}