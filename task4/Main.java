import java.io.*;
import java.util.*;

/**
 * КТ_9, задача 4. Минимальная стоимость объединения соседних групп в одну (стоимость = суммарный размер).
 * Идея: интервальная динамика. dp[l][r] — минимальная стоимость собрать фрагменты l..r в одну группу:
 *   dp[l][r] = min по k (dp[l][k] + dp[k+1][r]) + сумма(l..r) — последним объединяем левую и правую части.
 * Суммы отрезков берём из префиксных сумм. Время O(n^3 / 6) (n <= 500), память O(n^2).
 */
public class Main {
    public static void main(String[] args) throws IOException {
        int n = nextInt();
        long[] pre = new long[n + 1];
        for (int i = 0; i < n; i++) pre[i + 1] = pre[i] + nextLong();

        long[][] dp = new long[n][n];
        for (int len = 2; len <= n; len++) {
            for (int l = 0; l + len <= n; l++) {
                int r = l + len - 1;
                long best = Long.MAX_VALUE;
                for (int k = l; k < r; k++) {
                    long cand = dp[l][k] + dp[k + 1][r];
                    if (cand < best) best = cand;
                }
                dp[l][r] = best + pre[r + 1] - pre[l];
            }
        }
        System.out.println(dp[0][n - 1]);
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
