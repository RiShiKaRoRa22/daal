import java.util.*;

class MultiDijkstra {
    static class Edge {
        int to, weight;
        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int M = sc.nextInt();
        int K = sc.nextInt();

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

        int[] safeZones = new int[K];
        for (int i = 0; i < K; i++) {
            safeZones[i] = sc.nextInt();
        }

        int[] dist = new int[N + 1];
        boolean[] visited = new boolean[N + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);

        for (int safeZone : safeZones) {
            dist[safeZone] = 0;
        }

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

        for (int i = 1; i <= N; i++) {
            System.out.print((dist[i] == Integer.MAX_VALUE ? -1 : dist[i]) + " ");
        }
    }
}