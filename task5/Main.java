import java.io.*;
import java.util.*;

/**
 * КТ_9, задача 5. Путь вправо/вниз с максимальной суммой и не более чем k «экранами» для отрицательных клеток.
 * Идея: dp[i][j][t] — максимальная сумма на пути до клетки (i, j), если использовано не более t экранов.
 *   Перед клеткой: base[t] = max(сверху[t], слева[t]).   В клетке со значением a:
 *   dp[t] = base[t] + a;  если a < 0 и t >= 1, можно применить экран: dp[t] = max(dp[t], base[t-1]) (клетка считается нулём).
 * Храним только предыдущую строку. Время и память O(n*m*(k+1)) по времени, O(m*(k+1)) по памяти.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        int n = nextInt(), m = nextInt(), K = nextInt();
        int W = K + 1;
        long[] prev = new long[m * W], cur = new long[m * W];
        long[] base = new long[W];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                long a = nextLong();
                if (i == 0 && j == 0) {
                    Arrays.fill(base, 0);
                } else {
                    for (int t = 0; t < W; t++) {
                        long best = Long.MIN_VALUE;
                        if (i > 0) best = prev[j * W + t];
                        if (j > 0) best = Math.max(best, cur[(j - 1) * W + t]);
                        base[t] = best;
                    }
                }
                for (int t = 0; t < W; t++) {
                    long v = base[t] + a;
                    if (a < 0 && t > 0) v = Math.max(v, base[t - 1]);
                    cur[j * W + t] = v;
                }
            }
            long[] tmp = prev; prev = cur; cur = tmp;
        }
        System.out.println(prev[(m - 1) * W + K]);
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
