import java.io.*;
import java.util.*;

public class TreasureIsland {
    static final int INF = Integer.MAX_VALUE / 2;
    static final int MOD = (int) 1e9 + 7;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        char[][] grid = new char[n][];
        for (int i = 0; i < n; i++) {
            grid[i] = br.readLine().toCharArray();
        }

        int[][] d1 = new int[n][m];
        for (int i = 0; i < n; i++) {
            Arrays.fill(d1[i], INF);
        }
        d1[0][0] = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (i == 0 && j == 0) continue;
                if (grid[i][j] == '#') continue;
                int up = (i > 0) ? d1[i-1][j] : INF;
                int left = (j > 0) ? d1[i][j-1] : INF;
                int min = Math.min(up, left);
                if (min != INF) {
                    d1[i][j] = min + 1;
                }
            }
        }

        if (d1[n-1][m-1] == INF) {
            System.out.println(0);
            return;
        }

        int[][] d2 = new int[n][m];
        for (int i = 0; i < n; i++) {
            Arrays.fill(d2[i], INF);
        }
        d2[n-1][m-1] = 0;
        for (int i = n-1; i >= 0; i--) {
            for (int j = m-1; j >= 0; j--) {
                if (i == n-1 && j == m-1) continue;
                if (grid[i][j] == '#') continue;
                int down = (i < n-1) ? d2[i+1][j] : INF;
                int right = (j < m-1) ? d2[i][j+1] : INF;
                int min = Math.min(down, right);
                if (min != INF) {
                    d2[i][j] = min + 1;
                }
            }
        }

        long[][] ways1 = new long[n][m];
        ways1[0][0] = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (i == 0 && j == 0) continue;
                if (grid[i][j] == '#') continue;
                ways1[i][j] = 0;
                if (i > 0 && d1[i-1][j] + 1 == d1[i][j]) {
                    ways1[i][j] = (ways1[i][j] + ways1[i-1][j]) % MOD;
                }
                if (j > 0 && d1[i][j-1] + 1 == d1[i][j]) {
                    ways1[i][j] = (ways1[i][j] + ways1[i][j-1]) % MOD;
                }
            }
        }

        long[][] ways2 = new long[n][m];
        ways2[n-1][m-1] = 1;
        for (int i = n-1; i >= 0; i--) {
            for (int j = m-1; j >= 0; j--) {
                if (i == n-1 && j == m-1) continue;
                if (grid[i][j] == '#') continue;
                ways2[i][j] = 0;
                if (i < n-1 && d2[i+1][j] + 1 == d2[i][j]) {
                    ways2[i][j] = (ways2[i][j] + ways2[i+1][j]) % MOD;
                }
                if (j < m-1 && d2[i][j+1] + 1 == d2[i][j]) {
                    ways2[i][j] = (ways2[i][j] + ways2[i][j+1]) % MOD;
                }
            }
        }

        long totalWays = ways1[n-1][m-1] % MOD;
        boolean found = false;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (i == 0 && j == 0) continue;
                if (i == n-1 && j == m-1) continue;
                if (d1[i][j] + d2[i][j] != d1[n-1][m-1]) continue;
                long product = (ways1[i][j] * ways2[i][j]) % MOD;
                if (product == totalWays) {
                    found = true;
                    break;
                }
            }
            if (found) break;
        }

        System.out.println(found ? 1 : 2);
    }
}