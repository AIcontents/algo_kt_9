import java.io.*;
import java.util.*;

/**
 * КТ_9, задача 2. Количество маршрутов из (1,1) в (n,m) только вправо/вниз, обходя заблокированные клетки.
 * Идея: dp[i][j] = dp[i-1][j] + dp[i][j-1] для свободной клетки, 0 для заблокированной (по модулю 10^9+7).
 * Храним одну строку: dp[j] до обновления — значение «сверху», dp[j-1] — «слева». Время O(n*m), память O(m).
 */
public class Main {
    public static void main(String[] args) throws IOException {
        final long MOD = 1_000_000_007L;
        int n = nextInt(), m = nextInt();
        long[] dp = new long[m];
        for (int i = 0; i < n; i++) {
            readToken();
            for (int j = 0; j < m; j++) {
                if (tok[j] == '#') {
                    dp[j] = 0;
                } else if (i == 0 && j == 0) {
                    dp[0] = 1;
                } else {
                    long v = dp[j];                  // сверху (для первой строки там 0)
                    if (j > 0) v += dp[j - 1];       // слева
                    dp[j] = v % MOD;
                }
            }
        }
        System.out.println(dp[m - 1]);
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
