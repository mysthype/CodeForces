import java.util.*;

public class FoxAndNames {
    static final int ALPHABET_SIZE = 26;
    static List<Integer>[] graph = new ArrayList[ALPHABET_SIZE];
    static int[] inDegree = new int[ALPHABET_SIZE];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();
        String[] names = new String[n];

        for (int i = 0; i < ALPHABET_SIZE; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < n; i++) {
            names[i] = sc.nextLine();
        }

        if (!buildGraph(names, n)) {
            System.out.println("Impossible");
            return;
        }

        String order = topologicalSort();
        if (order == null) {
            System.out.println("Impossible");
        } else {
            System.out.println(order);
        }
    }

    static boolean buildGraph(String[] names, int n) {
        Arrays.fill(inDegree, 0);

        for (int i = 0; i < n - 1; i++) {
            String s1 = names[i], s2 = names[i + 1];
            int minLength = Math.min(s1.length(), s2.length());
            boolean found = false;

            for (int j = 0; j < minLength; j++) {
                if (s1.charAt(j) != s2.charAt(j)) {
                    int u = s1.charAt(j) - 'a';
                    int v = s2.charAt(j) - 'a';
                    graph[u].add(v);
                    inDegree[v]++;
                    found = true;
                    break;
                }
            }

            if (!found && s1.length() > s2.length()) {
                return false; // Invalid case (prefix issue)
            }
        }
        return true;
    }

    static String topologicalSort() {
        Queue<Integer> queue = new LinkedList<>();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < ALPHABET_SIZE; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }

        while (!queue.isEmpty()) {
            int u = queue.poll();
            result.append((char) ('a' + u));

            for (int v : graph[u]) {
                if (--inDegree[v] == 0) {
                    queue.add(v);
                }
            }
        }

        return result.length() == ALPHABET_SIZE ? result.toString() : null;
    }
}
