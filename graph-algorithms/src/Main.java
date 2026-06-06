 import java.util.*;
 public class Main{
 public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        Graph G = new Graph(N);

        for (int i = 0; i < M; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            G.addEdge(u, v);
        }

        TopologicalSort topo = new TopologicalSort(G);

        if (topo.hasCycle()) {
            System.out.println("Not schedulable");
            System.out.print("Cycle: ");
            List<Integer> cyc = (List<Integer>) topo.cycle();
            for (int i = 0; i < cyc.size(); i++) {
                System.out.print(cyc.get(i));
                if (i < cyc.size() - 1) System.out.print(" ");
            }
        } else {
            System.out.println("Schedulable");
            System.out.print("Order: ");
            List<Integer> ord = (List<Integer>) topo.order();
            for (int i = 0; i < ord.size(); i++) {
                System.out.print(ord.get(i));
                if (i < ord.size() - 1) System.out.print(" ");
            }
        }
        
        sc.close();
    }
}