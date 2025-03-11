import java.io.*;
import java.util.*;

public class BuildingPermutation {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        Long[] a = new Long[n]; // Use Long[] instead of long[] for mergesort

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            a[i] = Long.parseLong(st.nextToken());
        }

        // Sort using mergesort (O(n log n) guaranteed)
        Arrays.sort(a);

        long totalMoves = 0;
        for (int i = 0; i < n; i++) {
            totalMoves += Math.abs(a[i] - (i + 1));
        }

        System.out.println(totalMoves);
    }
}