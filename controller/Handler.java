package controller;
import model.*;

import api.ApiType;
import api.ExpressionList;
import api.RequestTimeList;
import model.Reader;
import view.StatusHtml;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Handler implements Runnable {
    private Socket clientSocket;
    private final int STATUS_OK = 200;
    private final int STATUS_404 = 404;
    private final int STATUS_400 = 400;

    private LocalDateTime handleTime;
    private String expr = "";
    private ApiType apiType = ApiType.OTHER;
    private RequestTimeList evalTimeList;
    private RequestTimeList getTimeTimeList;
    private ExpressionList exprList;
    private Response response;

    public Handler(Socket clientSocket, RequestTimeList evalTimeList, RequestTimeList getTimeTimeList, ExpressionList exprList) {
        this.clientSocket = clientSocket;
        this.evalTimeList = evalTimeList;
        this.getTimeTimeList = getTimeTimeList;
        this.exprList = exprList;
    }

    public String getExpr() {
        return this.expr;
    }

    public ApiType getApiType() {
        return this.apiType;
    }

    public LocalDateTime getHandleTime() {
        return this.handleTime;
    }

    @Override
    public void run() {
        String client = String.format("[%s:%d]", clientSocket.getInetAddress(), clientSocket.getPort());
        System.out.println(String.format("Handle client %s", client));
        this.handleTime = LocalDateTime.now();

        try {
            OutputStream out = clientSocket.getOutputStream();
            InputStream in = clientSocket.getInputStream();

            // READ
            Reader reader = new Reader(in);
            StartLine startLine = reader.readStartLine();

            // PROCESS
            updateResponse(startLine, reader);
            String responseStr = this.response.getString();

            // WRITE
            out.write(responseStr.getBytes());

            // CLOSE CONNECTION
            System.out.println(String.format("Close connection %s\n", client));
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateResponse(StartLine startLine, Reader reader) throws IOException {
        if (startLine.isGetStatus()) {
            String html = StatusHtml.buildHtml(this.evalTimeList, this.getTimeTimeList, exprList, LocalDateTime.now());
            this. response = new Response(STATUS_OK, html);
        } else if (startLine.isGetTime()) {
            String serverTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
            this.response = new Response(STATUS_OK, serverTime);
            this.apiType = ApiType.GET_TIME;
        } else if (startLine.isEvalExpr()) {
            Expression expression = reader.readExpression();
            this.expr = expression.getExpression();
            this.apiType = ApiType.EVAL_EXPR;
            if (expression.isValid()) {
                this.response = new Response(STATUS_OK, expression.getResultString());
            } else {
                this.response = new Response(STATUS_400, "illegal expression");
            }
        } else {
            this.response = new Response(STATUS_404, "not found");
        }
    }
}

