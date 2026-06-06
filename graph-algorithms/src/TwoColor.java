import java.util.*;
/**
 * The TwoColor class determines whether a given undirected graph is bipartite (2-colorable).
 * 
 * It uses **BFS (Breadth-First Search)** to traverse each component of the graph:
 * - It alternates colors between neighboring vertices.
 * - If it ever finds two adjacent vertices with the same color, that means
 *   an **odd-length cycle** exists, which makes the graph **not bipartite**.
 * - If such a case occurs, it reconstructs and returns that odd cycle.
 */
public class TwoColor {

	 private boolean[] marked; 		// Tracks visited vertices
	 private boolean[] color;		// Stores color assigned to each vertex (true/false)
	 private boolean isTwoColorable = true; // Flag that becomes false if odd cycle is found

	 
	 /** 
	     * Constructor initializes arrays but doesn't start BFS.
	     * BFS will be called manually for each disconnected component.
	     */
	 public TwoColor(Graph G)
	   {
	      marked = new boolean[G.V()];
	      
	      color = new boolean[G.V()];
	      
//	      parent = new int[G.V()];
//	      
//	      for (int s = 0; s < G.V(); s++)
//	         if (!marked[s])
//	             bfs(G, s,marked,parent);
	   }
	 
	 /**
	     * Performs a BFS starting from vertex 's' to check bipartiteness of the component.
	     * 
	     * Steps:
	     * 1. Start BFS from vertex 's'.
	     * 2. Assign alternating colors (true/false) to adjacent vertices.
	     * 3. If an edge connects two vertices of the same color → odd cycle found → not bipartite.
	     * 4. If odd cycle detected, reconstruct and return it as an ArrayList.
	     */
	 
	 public ArrayList<Integer> bfs(Graph G, int s,boolean [] marked, int[] parent) {
		 
		 Queue <Integer> q = new LinkedList<>(); // creating a queue
		 
		 marked[s] = true; 			//mark first vertex as visited  
		 parent[s]= -1; 			//start(root) has no parent 
		 color[s]= false;			//first color assignment
		 q.add(s); 					//enqueue it
		 
		
		 while (!q.isEmpty()){ 						// Continue while there are vertices in queue 
			   int v = q.remove();					 // Dequeue vertex to process (FIFO)
			   
			 for(int w : G.adj(v)) {				// Explore all adjacent vertices of v
				 
				 if (!marked[w]) { 					
					 
					 parent[w] = v;					// Set parent of w
					 marked[w] = true;				// Mark w as visited
					 color[w] = !color[v]; 			// Assign opposite color to w (assign different colors to neighbors)
					 q.add(w);						// Enqueue w for processing (add neighbor to queue to process it). 'it's not recursive so we don't use : bfs(G,w);' 
				   
				 } else if (color[w] == color[v]) { 	// if they have same color on both ends of an edge then thats an odd cycle (same-colored neighbors → odd cycle)
					 isTwoColorable = false;
					 return OddCycleReconsturtion(v,w,parent) ; // Return reconstructed cycle
				    }
			 	   } 
		 		  }		return null;		// If BFS completes with no conflict → bipartite
	 			}	

	 /**
	     * Helper method that reconstructs an odd-length cycle once found.
	     * 
	     * The idea:
	     * - Trace back parents from both vertices (v and w) until they meet at a common ancestor.
	     * - Combine both paths to form the cycle.
	     * 
	     * v one vertex in the same-colored edge
	     * w the other vertex in the same-colored edge
	     * parent parent array from BFS
	     * "returns" ArrayList representing the odd cycle
	     */
	 private ArrayList<Integer> OddCycleReconsturtion (int v, int w, int[] parent) { //helper method to store the oddCycles 
		 
		 ArrayList<Integer> pathW = new ArrayList<>(); // path from w to root 
		 ArrayList<Integer> pathV = new ArrayList<>(); // path from v to root
		 int x= v;
		 
		 while (x!= -1) { 	// Trace back from v to root
			 pathV.add(x);
			 x= parent[x];
			 			}
		 
		 x = w;	
		 while (x!= -1) {   // Trace back from w to root
			 pathW.add(x);
			 x= parent[x];
		 }
		 
		 // Find the lowest common ancestor (last common vertex)
		 
		 int iV = pathV.size()-1;
		 int iW = pathW.size()-1;
		 
		 while (iV >= 0 && iW >= 0 && pathV.get(iV).equals(pathW.get(iW))) {
			 iV--;
			 iW--; 
		 }
		 
		 iV++;
		 iW++;
	
		 // Merge paths to form the cycle
		 ArrayList<Integer> cycle = new ArrayList<>();
		 
		// Add vertices from v up to (but not beyond) the common ancestor
		 for (int i=0; i <= iV; i++) cycle.add(pathV.get(i)); 
		 
		// Add vertices from w’s path back down to w (reverse direction) 
		 for (int j=iW-1; j >= 0; j--) cycle.add(pathW.get(j)); 
	
		 cycle.add(v); 
		 
		 return cycle;
		 
	 }

	 
	 //Returns true if the component is bipartite, false otherwise.    
	 public boolean isBipartite() {
		 return isTwoColorable;
	 }
}
