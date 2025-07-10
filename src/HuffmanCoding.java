import java.util.*;

class HuffmanNode {
    char ch;
    int freq;
    HuffmanNode left, right;

    HuffmanNode(char ch, int freq) {
        this.ch = ch;
        this.freq = freq;
    }

    HuffmanNode(int freq, HuffmanNode left, HuffmanNode right) {
        this.freq = freq;
        this.left = left;
        this.right = right;
    }

    // Узел-лист?
    boolean isLeaf() {
        return left == null && right == null;
    }
}

public class HuffmanCoding {
    static class FrequencyComparator implements Comparator<HuffmanNode> {
        public int compare(HuffmanNode a, HuffmanNode b) {
            return a.freq - b.freq;
        }
    }

    // Построение дерева Хаффмана
    public static HuffmanNode buildTree(Map<Character, Integer> freqMap) {
        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>(new FrequencyComparator());

        for (var entry : freqMap.entrySet()) {
            pq.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }

        while (pq.size() > 1) {
            HuffmanNode left = pq.poll();
            HuffmanNode right = pq.poll();
            HuffmanNode parent = new HuffmanNode(left.freq + right.freq, left, right);
            pq.add(parent);
        }

        return pq.poll();
    }

    public static void generateCodes(HuffmanNode root, String code, Map<Character, String> codeMap) {
        if (root == null) return;

        if (root.isLeaf()) {
            codeMap.put(root.ch, code);
        }

        generateCodes(root.left, code + "0", codeMap);
        generateCodes(root.right, code + "1", codeMap);
    }

    public static void main(String[] args) {
        String text = "beep boop beer";

        Map<Character, Integer> freqMap = new HashMap<>();
        for (char ch : text.toCharArray()) {
            freqMap.put(ch, freqMap.getOrDefault(ch, 0) + 1);
        }

        HuffmanNode root = buildTree(freqMap);

        Map<Character, String> codeMap = new HashMap<>();
        generateCodes(root, "", codeMap);

        System.out.println("Коды Хаффмана:");
        for (var entry : codeMap.entrySet()) {
            System.out.println("'" + entry.getKey() + "': " + entry.getValue());
        }

        StringBuilder encoded = new StringBuilder();
        for (char ch : text.toCharArray()) {
            encoded.append(codeMap.get(ch));
        }

        System.out.println("\nЗакодированная строка:");
        System.out.println(encoded.toString());
    }
}
