import java.io.*;
import java.util.*;

public class MinimalString {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();
        int n = s.length();

        int[] freq = new int[26];  // Frequency of remaining characters in s
        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        Stack<Character> t = new Stack<>();
        StringBuilder u = new StringBuilder();
        int minChar = 0; // Tracks the smallest available character

        for (char c : s.toCharArray()) {
            freq[c - 'a']--;  // Decrease count since we're processing this char
            t.push(c);

            // Update the smallest available character
            while (minChar < 26 && freq[minChar] == 0) {
                minChar++;
            }

            // Pop from t while the top is the smallest lexicographically valid choice
            while (!t.isEmpty() && t.peek() - 'a' <= minChar) {
                u.append(t.pop());
            }
        }

        System.out.println(u.toString());
    }
}
