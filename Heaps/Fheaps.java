import java.io.*;
import java.util.*;

public class Fheaps {
    static final int N = 300000 + 1000;
    static final int Lim = 20;
    static List<Integer>[] G = new ArrayList[N];
    static int[][] f = new int[N][Lim + 1];
    static int[] g = new int[N];
    static int[] dep = new int[N];
    static long ans = 0;
    static int n;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        for (int i = 0; i <= n; i++) {
            G[i] = new ArrayList<>();
        }
        for (int i = 1; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            G[u].add(v);
            G[v].add(u);
        }
        Dp(1, 0);
        Dfs(1, 0);
        for (int i = 1; i <= n; i++) {
            ans += dep[i];
            for (int j = 1; j <= Lim; j++) {
                if (f[i][j] != 0) {
                    ans += f[i][j] - 1;
                }
            }
        }
        System.out.println(ans);
    }

    static void Dp(int u, int fa) {
        for (int v : G[u]) {
            if (v != fa) {
                Dp(v, u);
            }
        }
        f[u][1] = n;
        for (int i = 2; i <= Lim; i++) {
            int len = 0;
            for (int v : G[u]) {
                if (v != fa) {
                    g[++len] = f[v][i - 1];
                }
            }
            Arrays.sort(g, 1, len + 1);
            reverse(g, 1, len);
            for (int k = len; k >= 1; k--) {
                if (g[k] >= k) {
                    f[u][i] = k;
                    break;
                }
            }
        }
    }

    static void Dfs(int u, int fa) {
        dep[u] = 1;
        for (int v : G[u]) {
            if (v != fa) {
                Dfs(v, u);
                dep[u] = Math.max(dep[u], dep[v] + 1);
                for (int i = 1; i <= Lim; i++) {
                    f[u][i] = Math.max(f[u][i], f[v][i]);
                }
            }
        }
    }

    static void reverse(int[] arr, int start, int end) {
        while (start < end) {
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }
    }
}