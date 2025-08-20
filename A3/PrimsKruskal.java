import java.util.*;
import java.io.*;

public class PrimsKruskal{
    static int[][] graph;
    static int V;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner fileScanner = new Scanner(new File("graph.txt"));
        V = fileScanner.nextInt();
        graph = new int[V][V];
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                graph[i][j] = fileScanner.nextInt();
                if (graph[i][j] == 0 && i != j) {
                    graph[i][j] = 9999;
                }
            }
        }
        fileScanner.close();

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Prim's Algorithm");
            System.out.println("2. Kruskal's Algorithm");
            System.out.println("3. Exit");
            System.out.print("Choose (1-3): ");

            int choice = sc.nextInt();
            System.out.println();

            switch (choice) {
                case 1:
                    System.out.println("Prim's Algorithm:");
                    primMST();
                    break;

                case 2:
                    System.out.println("Kruskal's Algorithm:");
                    kruskalMST();
                    break;

                case 3:
                    System.out.println("Exiting.");
                    sc.close();
                    return;

                default:
                    System.out.println("Choose 1-3.");
            }
        }
    }

    static void primMST() {
        int[] parent = new int[V];
        int[] cost = new int[V];
        boolean[] inMST = new boolean[V];

        for (int i = 0; i < V; i++) {
            cost[i] = 9999;
        }
        cost[0] = 0;
        parent[0] = -1;

        int totalCost = 0;
        int edgeComparisons = 0;

        for (int count = 0; count < V; count++) {
            int minCost = 9999;
            int u = -1;
            for (int v = 0; v < V; v++) {
                if (!inMST[v] && cost[v] < minCost) {
                    minCost = cost[v];
                    u = v;
                }
            }

            if (u == -1) {
                System.out.println("Graph is disconnected.");
                return;
            }

            inMST[u] = true;
            totalCost += cost[u];

            System.out.println("Stage " + (count + 1) + ": Node " + u +
                               " (from " + (parent[u] == -1 ? "none" : parent[u]) +
                               ", cost " + cost[u] + "), Total cost: " + totalCost);

            for (int v = 0; v < V; v++) {
                if (graph[u][v] != 9999 && !inMST[v]) {
                    edgeComparisons++;
                    if (graph[u][v] < cost[v]) {
                        cost[v] = graph[u][v];
                        parent[v] = u;
                    }
                }
            }
        }

        System.out.println("Total MCST cost: " + totalCost);
        System.out.println("Edge comparisons: " + edgeComparisons);
    }

    static class Edge {
        int u, v, weight;

        Edge(int u, int v, int weight) {
            this.u = u;
            this.v = v;
            this.weight = weight;
        }
    }

    static int find(int[] parent, int i) {
        if (parent[i] != i) {
            parent[i] = find(parent, parent[i]);
        }
        return parent[i];
    }

    static void union(int[] parent, int[] rank, int x, int y) {
        int xroot = find(parent, x);
        int yroot = find(parent, y);

        if (rank[xroot] < rank[yroot]) {
            parent[xroot] = yroot;
        } else if (rank[xroot] > rank[yroot]) {
            parent[yroot] = xroot;
        } else {
            parent[yroot] = xroot;
            rank[xroot]++;
        }
    }

    static void kruskalMST() {
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            for (int j = i + 1; j < V; j++) {
                if (graph[i][j] != 9999) {
                    edges.add(new Edge(i, j, graph[i][j]));
                }
            }
        }

        Collections.sort(edges, new Comparator<Edge>() {
            public int compare(Edge e1, Edge e2) {
                return e1.weight - e2.weight;
            }
        });

        int[] parent = new int[V];
        int[] rank = new int[V];
        for (int i = 0; i < V; i++) {
            parent[i] = i;
            rank[i] = 0;
        }

        int totalCost = 0;
        int edgesUsed = 0;
        int stage = 1;

        for (Edge edge : edges) {
            int x = find(parent, edge.u);
            int y = find(parent, edge.v);

            if (x != y) {
                union(parent, rank, x, y);
                totalCost += edge.weight;

                System.out.println("Stage " + stage + ": Edge " + edge.u + "-" + edge.v +
                                   ", cost " + edge.weight + ", Total cost: " + totalCost);
                stage++;
                edgesUsed++;

                if (edgesUsed == V - 1) {
                    break;
                }
            }
        }

        if (edgesUsed < V - 1) {
            System.out.println("Graph is disconnected.");
            return;
        }

        System.out.println("Total MCST cost: " + totalCost);
    }
}