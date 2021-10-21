package model;

import model.Expression;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Reader {
    private static final byte NEW_LINE = (byte) '\n';
    private static final int BUFFER_SIZE = 16;
    private InputStream in;
    private StartLine startLine;
    private Expression expression;

    public Reader(InputStream in) throws IOException {
        this.in = in;
    }

    public StartLine readStartLine() throws IOException {
        String str = processStartLine();
        this.startLine = new StartLine(str);
        return this.startLine;
    }

    public Expression readExpression() throws IOException {
        String exprString = this.processExpression();
        this.expression = new Expression(exprString);
        return this.expression;
    }

    private String processExpression() throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        int exprLen = this.processExprLength();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        int loadTime = exprLen/BUFFER_SIZE;
        if (exprLen % BUFFER_SIZE == 0) loadTime--;

        int len;
        for (int i = 0; i <= loadTime; i++) {
            len = in.read(buffer);
            outputStream.write(buffer, 0, len);
        }

        return outputStream.toString();
    }

    private String processStartLine() throws IOException {
        String res = "";
        int curByte = 0;
        while (curByte != NEW_LINE) {
            curByte = in.read();
            res += (char) curByte;
        }
        return res;
    }

    private int processExprLength() throws IOException {
        String headers = "";
        int curByte = 0;

        while (!headers.endsWith("\r\n\r\n")) {
            curByte = in.read();
            headers += (char)curByte;
        }

        String[] headerArr = headers.split("Content-Length: ");
        String lengthStr = "";
        for (char c: headerArr[1].toCharArray()) {
            if (Character.isDigit(c)) {
                lengthStr += c;
            } else {
                break;
            }
        }

        return Integer.valueOf(lengthStr);
    }
}
