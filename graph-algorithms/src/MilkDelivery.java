// Title: Milk Delivery (Question 1 solution)
// Name: Zeineddin zidan
// Id: 99621968516
// Description: Computes minimum travel time from city 1 to city N with roads and
//              a subway system connecting K cities with constant cost T between any two subway cities.
//              Uses Dijkstra and an efficient technique to account for subway transfers
//              without creating O(K^2) edges.

import java.io.*;
import java.util.*;

public class MilkDelivery {

    static class Edge {
        // simple edge container for adjacency list
        int to;
        int w;

        // stores neighbor and road weight
        Edge(int to, int w) { 
            this.to = to; 
            this.w = w; 
        }
    }

    static final long INF = Long.MAX_VALUE / 4;

    // Main workflow: reads input, runs Dijkstra, applies subway optimization, outputs result
    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);

        int N = fs.nextInt();
        int M = fs.nextInt();
        int K = fs.nextInt();
        int T = fs.nextInt();

        List<Integer> subwayCities = new ArrayList<>(K);
        boolean[] isSubway = new boolean[N + 1];

        for (int i = 0; i < K; ++i) {
            int s = fs.nextInt();
            subwayCities.add(s);
            isSubway[s] = true;
        }

        List<Edge>[] adj = new ArrayList[N + 1];
        for (int i = 1; i <= N; ++i) adj[i] = new ArrayList<>();

        for (int i = 0; i < M; ++i) {
            int x = fs.nextInt();
            int y = fs.nextInt();
            int w = fs.nextInt();
            adj[x].add(new Edge(y, w));
            adj[y].add(new Edge(x, w));
        }

        // First Dijkstra from city 1
        long[] dist = dijkstra(N, adj, 1);

        if (dist[N] == INF && K == 0) {
            System.out.println(-1);
            return;
        }

        // finding best distance to any subway city via ordinary roads
        long bestSub = INF;
        for (int s : subwayCities) {
            if (dist[s] < bestSub) bestSub = dist[s];
        }

        // apply subway relaxation
        PriorityQueue<Pair> pq = new PriorityQueue<>();
        boolean[] seen = new boolean[N + 1];

        for (int s : subwayCities) {
            long candidate = bestSub == INF ? INF : bestSub + T;
            if (candidate < dist[s]) {
                dist[s] = candidate;
                pq.add(new Pair(s, dist[s]));
            }
        }

        // Dijkstra propagation from improved subway nodes
        while (!pq.isEmpty()) {
            Pair cur = pq.poll();
            int u = cur.node;
            long d = cur.dist;

            if (d != dist[u]) continue;

            for (Edge e : adj[u]) {
                int v = e.to;
                long nd = d + e.w;

                if (nd < dist[v]) {
                    dist[v] = nd;
                    pq.add(new Pair(v, nd));
                }
            }
        }

        long ans = dist[N];
        System.out.println(ans >= INF ? -1 : ans);
    }

    // Runs classic Dijkstra from a source node
    static long[] dijkstra(int N, List<Edge>[] adj, int src) {
        final long INF = Long.MAX_VALUE / 4;

        long[] dist = new long[N + 1];
        Arrays.fill(dist, INF);
        dist[src] = 0;

        PriorityQueue<Pair> pq = new PriorityQueue<>();
        pq.add(new Pair(src, 0L));

        while (!pq.isEmpty()) {
            Pair p = pq.poll();
            int u = p.node;
            long d = p.dist;

            if (d != dist[u]) continue;

            for (Edge e : adj[u]) {
                int v = e.to;
                long nd = d + e.w;

                if (nd < dist[v]) {
                    dist[v] = nd;
                    pq.add(new Pair(v, nd));
                }
            }
        }
        return dist;
    }

    static class Pair implements Comparable<Pair> {
        // simple container for PQ (node, distance)
        int node;
        long dist;

        // holds node and its current best distance
        Pair(int node, long dist) { 
            this.node = node; 
            this.dist = dist; 
        }

        // compare by distance for Dijkstra PQ
        public int compareTo(Pair o) { 
            return Long.compare(this.dist, o.dist); 
        }
    }

    static class FastScanner {
        // fast input reading utility
        BufferedReader br;
        StringTokenizer st;

        // initializes reader
        FastScanner(InputStream is) { 
            br = new BufferedReader(new InputStreamReader(is)); 
        }

        // returns next token
        String next() throws IOException {
            while (st == null || !st.hasMoreElements())
                st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        // reads integer
        int nextInt() throws IOException { 
            return Integer.parseInt(next()); 
        }

        // reads long
        long nextLong() throws IOException { 
            return Long.parseLong(next()); 
        }
    }
}
