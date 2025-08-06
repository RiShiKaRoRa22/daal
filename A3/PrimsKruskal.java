import java.io.File;
import java.util.*;


public class PrimsMST {
    public static void primMST(int[][] graph) {
        int V = graph.length;
        boolean[] visited = new boolean[V];
        int[] key = new int[V];         
        int[] parent = new int[V];      

        Arrays.fill(key, Integer.MAX_VALUE);
        key[0] = 0;   
        parent[0] = -1;

        for (int count = 0; count < V - 1; count++) {
            int u = minKey(key, visited);
            visited[u] = true;

            for (int v = 0; v < V; v++) {
                if (graph[u][v] != 0 && !visited[v] && graph[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = graph[u][v];
                }
            }
        }

        System.out.println("Edge \tWeight");
        for (int i = 1; i < V; i++)
            System.out.println(parent[i] + " - " + i + "\t" + graph[i][parent[i]]);
    }

    private static int minKey(int[] key, boolean[] visited) {
        int min = Integer.MAX_VALUE, minIndex = -1;
        for (int v = 0; v < key.length; v++) {
            if (!visited[v] && key[v] < min) {
                min = key[v];
                minIndex = v;
            }
        }
        return minIndex;
    }
}



class Edge implements Comparable<Edge> {
    int src, dest, weight;
    public Edge(int s, int d, int w) {
        src = s;
        dest = d;
        weight = w;
    }

    public int compareTo(Edge other) {
        return this.weight - other.weight;
    }
}

class DisjointSet {
    int[] parent;

    public DisjointSet(int n) {
        parent = new int[n];
        for (int i = 0; i < n; i++)
            parent[i] = i;
    }

    public int find(int u) {
        if (parent[u] != u)
            parent[u] = find(parent[u]);
        return parent[u];
    }

    public void union(int u, int v) {
        int uRoot = find(u);
        int vRoot = find(v);
        parent[uRoot] = vRoot;
    }
}

public class KruskalsMST {
    public static void kruskalMST(int[][] adjMatrix) {
        int V = adjMatrix.length;
        List<Edge> edges = new ArrayList<>();


        for (int i = 0; i < V; i++) {
            for (int j = i + 1; j < V; j++) {
                if (adjMatrix[i][j] != 0) {
                    edges.add(new Edge(i, j, adjMatrix[i][j]));
                }
            }
        }
        Collections.sort(edges);

        DisjointSet ds = new DisjointSet(V);

        System.out.println("Edge \tWeight");

        for (Edge edge : edges) {
            int uRoot = ds.find(edge.src);
            int vRoot = ds.find(edge.dest);

            if (uRoot != vRoot) {
                System.out.println(edge.src + " - " + edge.dest + "\t" + edge.weight);
                ds.union(uRoot, vRoot);
            }
        }
    }
}



class Solution{
    public static void main(String args[]){
        File fileobj= new File("graph.txt");
        Scanner sc= new Scanner(fileobj);
        List<int[]> matrix= new ArrayList<>();
        
        
        while(sc.hasNextLine()){
            String line=sc.nextline();
            String[] tokens= line.split("\\s+");
            int[] row= new int[tokens.length];
            int i=0;
            for(String s: tokens){
                row[i]=Integer.parseInt(s);
                i++;
            }
            matrix.add(row);
            
            int[][] adjmat= matrix.toArray(new int[0][]);
        }
        
        
    }
}
    
