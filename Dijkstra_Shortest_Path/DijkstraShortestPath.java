import java.io.*;
import java.util.*;

public class DijkstraShortestPath {
    static class Pair implements Comparable<Pair> {
        int node;
        long weight;
        Pair(int n, long w) { node = n; weight = w; }
        public int compareTo(Pair other) {
            return Long.compare(this.weight, other.weight);
        }
    }

    public static void main(String[] args) throws IOException {
        // Fast input
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // Adjacency list representation
        List<Pair>[] graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) graph[i] = new ArrayList<>();

        // Reading edges
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            graph[u].add(new Pair(v, w));
            graph[v].add(new Pair(u, w)); // Undirected graph
        }

        // Run optimized Dijkstra
        int[] parent = new int[n + 1];
        long[] dist = dijkstra(graph, n, parent);

        // If no path to node 'n'
        if (dist[n] == Long.MAX_VALUE) {
            System.out.println("-1");
        } else {
            printPath(parent, n);
        }
    }

    static long[] dijkstra(List<Pair>[] graph, int n, int[] parent) {
        PriorityQueue<Pair> pq = new PriorityQueue<>();
        long[] dist = new long[n + 1];
        Arrays.fill(dist, Long.MAX_VALUE);
        Arrays.fill(parent, -1);

        // Start from node 1
        dist[1] = 0;
        pq.add(new Pair(1, 0));

        while (!pq.isEmpty()) {
            Pair current = pq.poll();
            int u = current.node;
            long d = current.weight;

            // Ignore stale entries in PQ
            if (d > dist[u]) continue;

            // Process all neighbors
            for (Pair neighbor : graph[u]) {
                int v = neighbor.node;
                long weight = neighbor.weight;

                if (dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    parent[v] = u;
                    pq.add(new Pair(v, dist[v]));
                }
            }
        }
        return dist;
    }

    static void printPath(int[] parent, int target) {
        List<Integer> path = new ArrayList<>();
        for (int v = target; v != -1; v = parent[v]) {
            path.add(v);
        }
        Collections.reverse(path);
        for (int v : path) {
            System.out.print(v + " ");
        }
        System.out.println();
    }
}
