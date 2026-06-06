// Title: Road Lighting Optimization (Question 2 solution)
// Description: Computes maximum road length that can be turned off
//              while keeping each graph connected (total - MST).
import java.io.*;
import java.util.*;

public class RoadLighting {

    static class Edge implements Comparable<Edge> {
        int u, v;
        long w;

        // Constructor for an edge
        Edge(int u, int v, long w) { 
            this.u = u; 
            this.v = v; 
            this.w = w; 
        }

        // Compare edges by weight
        public int compareTo(Edge o) {
            return Long.compare(this.w, o.w);
        }
    }

    static class UnionFind {
        int[] parent;
        int[] rank;

        // Initialize Union-Find structure
        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; ++i) parent[i] = i;
        }

        // Find root with path compression
        int find(int x) {
            if (parent[x] != x) parent[x] = find(parent[x]);
            return parent[x];
        }

        // Merge two sets
        boolean union(int a, int b) {
            int ra = find(a), rb = find(b);
            if (ra == rb) return false;
            if (rank[ra] < rank[rb]) parent[ra] = rb;
            else if (rank[ra] > rank[rb]) parent[rb] = ra;
            else { parent[rb] = ra; rank[ra]++; }
            return true;
        }
    }

    // Main program loop: read graphs & compute savings
    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        StringBuilder sb = new StringBuilder();

        while (true) {
            int m = fs.nextInt();
            int n = fs.nextInt();
            if (m == 0 && n == 0) break;

            List<Edge> edges = new ArrayList<>(n);
            long total = 0L;

            // Read all edges
            for (int i = 0; i < n; ++i) {
                int x = fs.nextInt();
                int y = fs.nextInt();
                long z = fs.nextLong();
                edges.add(new Edge(x, y, z));
                total += z;
            }

            Collections.sort(edges);
            UnionFind uf = new UnionFind(m);

            long mst = 0L;
            int used = 0;

            // Kruskal's algorithm
            for (Edge e : edges) {
                if (uf.union(e.u, e.v)) {
                    mst += e.w;
                    used++;
                    if (used == m - 1) break;
                }
            }

            long saving = total - mst;
            sb.append(saving).append('\n');
        }

        System.out.print(sb.toString());
    }

    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        // Constructor: wrap input stream
        FastScanner(InputStream is) { 
            br = new BufferedReader(new InputStreamReader(is)); 
        }

        // Read next token
        String next() throws IOException {
            while (st == null || !st.hasMoreElements()) {
                String line = br.readLine();
                if (line == null) return null;
                st = new StringTokenizer(line);
            }
            return st.nextToken();
        }

        // Read int
        int nextInt() throws IOException { 
            return Integer.parseInt(next()); 
        }

        // Read long
        long nextLong() throws IOException { 
            return Long.parseLong(next()); 
        }
    }
}
