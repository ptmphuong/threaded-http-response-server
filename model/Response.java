package model;

import java.util.HashMap;

public class Response {

    private int statusCode;
    private String responseBody;

    private HashMap<Integer, String> messageMap = new HashMap();

    public Response(int statusCode, String message) {
        this.statusCode = statusCode;
        this.responseBody = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getString() {
        this.setMessageMap();
        if (!this.messageMap.containsKey(statusCode)) throw new IllegalArgumentException("Cannot find status code");
        String statusStr = this.messageMap.get(statusCode);
        int messageLength = this.responseBody.getBytes().length;
        return buildResponseString(this.statusCode, statusStr, messageLength, this.responseBody);
    }

    private String buildResponseString(int statusCode, String statusMessage, int messageLength, String message) {
        String res = String.format("HTTP/1.1 %d %s\r\n" +
                        "Content-Type: text/html\r\n" +
                        "Content-Length: %d\r\n" +
                        "\r\n" +
                        "%s",
                statusCode, statusMessage, messageLength, message);
        return res;
    }

    private void setMessageMap() {
        final String okStr = "OK";
        final String notFoundStr = "Not Found";
        final String badRequestStr = "Bad Request";

        final int okCode = 200;
        final int notFoundCode = 404;
        final int badRequestCode = 400;

        this.messageMap.put(okCode, okStr);
        this.messageMap.put(notFoundCode, notFoundStr);
        this.messageMap.put(badRequestCode, badRequestStr);
    }
}
