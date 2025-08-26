import java.util.*;

class CityRoads {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt(); // number of nodes
        int M = sc.nextInt(); // number of edges

        int[][] graph = new int[N + 1][N + 1];
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (i == j) graph[i][j] = 0;
                else graph[i][j] = 1000000000; 
            }
        }

        for (int i = 0; i < M; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int t = sc.nextInt();
            graph[u][v] = t;
            graph[v][u] = t; 
        }

        int S = sc.nextInt(); 

        int[] dist = new int[N + 1];    
        boolean[] visited = new boolean[N + 1]; 

        for (int i = 1; i <= N; i++) {
            dist[i] = graph[S][i];
        }
        dist[S] = 0;
        visited[S] = true;

        for (int count = 1; count < N; count++) {
            int u = -1;
            int minDist = 1000000000;
            for (int i = 1; i <= N; i++) {
                if (!visited[i] && dist[i] < minDist) {
                    minDist = dist[i];
                    u = i;
                }
            }

            if (u == -1) break;
            visited[u] = true;

            for (int v = 1; v <= N; v++) {
                if (!visited[v] && graph[u][v] < 1000000000) {
                    if (dist[u] + graph[u][v] < dist[v]) {
                        dist[v] = dist[u] + graph[u][v];
                    }
                }
            }
        }

        for (int i = 1; i <= N; i++) {
            if (i == S) continue;
            if (dist[i] >= 1000000000) System.out.print("-1 ");
            else System.out.print(dist[i] + " ");
        }
    }
}
