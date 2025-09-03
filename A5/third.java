import java.util.*;

class EscapeIsland {
    static class Edge {
        int to, weight;
        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    static int[] dijkstra(int N, List<List<Edge>> graph, int source) {
        int[] dist = new int[N + 1];
        boolean[] visited = new boolean[N + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;

        for (int count = 0; count < N; count++) {
            int u = -1;
            int minDist = Integer.MAX_VALUE;
            for (int i = 1; i <= N; i++) {
                if (!visited[i] && dist[i] < minDist) {
                    minDist = dist[i];
                    u = i;
                }
            }

            if (u == -1) break;
            visited[u] = true;

            for (Edge edge : graph.get(u)) {
                int v = edge.to;
                int weight = edge.weight;
                if (!visited[v] && dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                }
            }
        }
        return dist;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int M = sc.nextInt();

        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < M; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int w = sc.nextInt();
            graph.get(u).add(new Edge(v, w));
            graph.get(v).add(new Edge(u, w));
        }

        int S = sc.nextInt();
        int V = sc.nextInt();

        int[] distS = dijkstra(N, graph, S);
        int[] distV = dijkstra(N, graph, V);

        List<Integer> safeNodes = new ArrayList<>();
        for (int i = 1; i <= N; i++) {
            if (distS[i] < distV[i]) {
                safeNodes.add(i);
            }
        }

        if (safeNodes.isEmpty()) {
            System.out.println("None");
        } else {
            Collections.sort(safeNodes);
            for (int node : safeNodes) {
                System.out.print(node + " ");
            }
        }
    }
}