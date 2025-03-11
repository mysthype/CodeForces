import java.io.*;
import java.util.*;

public class PaintingFence {
    static int[] heights;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        heights = new int[n];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            heights[i] = Integer.parseInt(st.nextToken());
        }

        System.out.println(minStrokes(0, n - 1, 0));
    }

    static int minStrokes(int l, int r, int baseHeight) {
        if (l > r) return 0;

        // Find the minimum height in the range [l, r]
        int minIndex = l;
        for (int i = l; i <= r; i++) {
            if (heights[i] < heights[minIndex]) {
                minIndex = i;
            }
        }

        int minHeight = heights[minIndex];

        // Option 1: Using horizontal strokes to clear "minHeight - baseHeight"
        int horizontalStrokes = minHeight - baseHeight;

        // Option 2: Use vertical strokes (1 per plank)
        int verticalStrokes = r - l + 1;

        // Recursively solve the left and right unpainted parts
        int leftStrokes = minStrokes(l, minIndex - 1, minHeight);
        int rightStrokes = minStrokes(minIndex + 1, r, minHeight);

        // Return the minimum strokes needed
        return Math.min(verticalStrokes, horizontalStrokes + leftStrokes + rightStrokes);
    }
}
