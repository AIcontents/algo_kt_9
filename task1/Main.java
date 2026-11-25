import java.io.*;
import java.util.*;

/**
 * КТ_9, задача 1. Минимальное число палиндромов, на которые можно разрезать строку.
 * Идея: dp[i] — минимальное число частей для префикса длины i. Палиндром s[l..r] даёт dp[r+1] = min(dp[r+1], dp[l] + 1).
 * Все палиндромы находим расширением от каждого центра (нечётные и чётные). Центры обходим слева направо,
 * поэтому к моменту использования dp[l] оно уже окончательное. Время O(n^2) (n <= 4000), память O(n).
 */
public class Main {
    public static void main(String[] args) throws IOException {
        readToken();
        byte[] s = Arrays.copyOf(tok, tokLen);
        int n = s.length;

        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int c = 0; c < n; c++) {
            for (int l = c, r = c; l >= 0 && r < n && s[l] == s[r]; l--, r++) {        // нечётные палиндромы
                dp[r + 1] = Math.min(dp[r + 1], dp[l] + 1);
            }
            for (int l = c, r = c + 1; l >= 0 && r < n && s[l] == s[r]; l--, r++) {    // чётные палиндромы
                dp[r + 1] = Math.min(dp[r + 1], dp[l] + 1);
            }
        }
        System.out.println(dp[n]);
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
