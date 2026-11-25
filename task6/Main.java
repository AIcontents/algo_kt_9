import java.io.*;
import java.util.*;

/**
 * КТ_9, задача 6. Выбрать ровно k вершин, образующих связную часть дерева, с максимальной суммой ценностей.
 * Идея: динамика по дереву (рюкзак на поддеревьях). dp[v][j] — максимальная сумма связного множества из j вершин,
 * лежащего в поддереве v и содержащего v. Детей присоединяем по одному: либо не берём ничего из поддерева ребёнка c,
 * либо берём b >= 1 его вершин (dp[c][b]) — они связаны с v через сам c.
 * Ответ — максимум dp[v][k] по всем v (v — самая верхняя вершина выбранного множества).
 * Размеры ограничены k, поэтому время O(n * k), рекурсия глубиной не больше n <= 300.
 */
public class Main {
    static final long NEG = Long.MIN_VALUE / 4;
    static int k;
    static long[] val;
    static int[] head, nxt, to;
    static long best = Long.MIN_VALUE;

    static long[] dfs(int v, int parent) {
        long[] dp = {NEG, val[v]};                       // dp[j]: связное множество из j вершин с вершиной v
        for (int e = head[v]; e != -1; e = nxt[e]) {
            int c = to[e];
            if (c == parent) continue;
            long[] child = dfs(c, v);
            int size = Math.min(k, (dp.length - 1) + (child.length - 1));
            long[] nd = new long[size + 1];
            Arrays.fill(nd, NEG);
            for (int a = 1; a < dp.length; a++) {
                if (dp[a] == NEG) continue;
                nd[a] = Math.max(nd[a], dp[a]);          // из поддерева c ничего не берём
                for (int b = 1; b < child.length && a + b <= k; b++) {
                    if (child[b] == NEG) continue;
                    nd[a + b] = Math.max(nd[a + b], dp[a] + child[b]);
                }
            }
            dp = nd;
        }
        if (k < dp.length) best = Math.max(best, dp[k]);
        return dp;
    }

    public static void main(String[] args) throws IOException {
        int n = nextInt();
        k = nextInt();
        val = new long[n + 1];
        for (int i = 1; i <= n; i++) val[i] = nextLong();
        head = new int[n + 1];
        nxt = new int[2 * n];
        to = new int[2 * n];
        Arrays.fill(head, -1);
        int cnt = 0;
        for (int i = 0; i < n - 1; i++) {
            int u = nextInt(), v = nextInt();
            to[cnt] = v; nxt[cnt] = head[u]; head[u] = cnt++;
            to[cnt] = u; nxt[cnt] = head[v]; head[v] = cnt++;
        }
        dfs(1, 0);
        System.out.println(best);
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
