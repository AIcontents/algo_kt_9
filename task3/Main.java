import java.io.*;
import java.util.*;

/**
 * КТ_9, задача 3. Независимое множество максимальной суммарной ценности в дереве (ценности могут быть отрицательными).
 * Идея: динамика по дереву. take[v] — лучший результат в поддереве v, если v выбрана; skip[v] — если не выбрана.
 *   take[v] = v[v] + сумма skip[ребёнка];   skip[v] = сумма max(take[ребёнка], skip[ребёнка]).
 * Обход без рекурсии (дерево может быть «цепочкой» из 200 000 вершин): порядок BFS, затем с конца к корню.
 * Ответ max(take[1], skip[1]) — не меньше 0, так как можно не выбирать ничего. Время O(n), суммы в long.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        int n = nextInt();
        long[] take = new long[n + 1], skip = new long[n + 1];
        for (int i = 1; i <= n; i++) take[i] = nextLong();
        int[] head = new int[n + 1], nxt = new int[2 * (n - 1) + 1], to = new int[2 * (n - 1) + 1];
        Arrays.fill(head, -1);
        int cnt = 0;
        for (int i = 0; i < n - 1; i++) {
            int u = nextInt(), v = nextInt();
            to[cnt] = v; nxt[cnt] = head[u]; head[u] = cnt++;
            to[cnt] = u; nxt[cnt] = head[v]; head[v] = cnt++;
        }

        int[] parent = new int[n + 1], order = new int[n];
        int qh = 0, qt = 0;
        order[qt++] = 1;
        while (qh < qt) {                               // порядок BFS от корня 1
            int v = order[qh++];
            for (int e = head[v]; e != -1; e = nxt[e]) {
                if (to[e] != parent[v]) {
                    parent[to[e]] = v;
                    order[qt++] = to[e];
                }
            }
        }
        for (int i = n - 1; i >= 0; i--) {              // дети обрабатываются раньше родителей
            int v = order[i], p = parent[v];
            if (p != 0) {
                take[p] += skip[v];
                skip[p] += Math.max(take[v], skip[v]);
            }
        }
        System.out.println(Math.max(take[1], skip[1]));
    }

    // ===================== быстрый ввод =====================
    private static final InputStream IN = System.in;
    private static final byte[] BUF = new byte[1 << 16];
    private static int bufLen = 0, bufPtr = 0;
    private static byte[] tok = new byte[64];   // последний прочитанный токен
    private static int tokLen = 0;

    private static int readByte() throws IOException {
        if (bufPtr == bufLen) {
            bufLen = IN.read(BUF, 0, BUF.length);
            bufPtr = 0;
            if (bufLen <= 0) { bufLen = 0; return -1; }
        }
        return BUF[bufPtr++] & 0xFF;
    }

    private static long nextLong() throws IOException {
        int c = readByte();
        while (c != '-' && (c < '0' || c > '9')) {
            if (c == -1) throw new EOFException("неожиданный конец ввода");
            c = readByte();
        }
        boolean neg = false;
        if (c == '-') { neg = true; c = readByte(); }
        long r = 0;
        while (c >= '0' && c <= '9') { r = r * 10 + (c - '0'); c = readByte(); }
        return neg ? -r : r;
    }

    private static int nextInt() throws IOException { return (int) nextLong(); }

    /** Читает токен (последовательность непробельных символов) в tok[0..tokLen). false — конец ввода. */
    private static boolean readToken() throws IOException {
        int c = readByte();
        while (c != -1 && c <= ' ') c = readByte();
        if (c == -1) return false;
        tokLen = 0;
        while (c > ' ') {
            if (tokLen == tok.length) tok = Arrays.copyOf(tok, tokLen * 2);
            tok[tokLen++] = (byte) c;
            c = readByte();
        }
        return true;
    }

    private static String next() throws IOException {
        return readToken() ? new String(tok, 0, tokLen, java.nio.charset.StandardCharsets.ISO_8859_1) : null;
    }
}
