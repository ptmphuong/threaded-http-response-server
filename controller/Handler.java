package controller;
import model.*;

import model.RequestType;
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
    private RequestType apiType;
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

    public RequestType getApiType() {
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
//            StartLine startLine = reader.readStartLine();
            this.apiType = reader.getRequestType();

            // PROCESS
            setResponse(reader);
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

    private void setResponse(Reader reader) throws IOException {
        switch (this.apiType) {
            case GET_STATUS: {
                String html = StatusHtml.buildHtml(this.evalTimeList, this.getTimeTimeList, exprList, LocalDateTime.now());
                this.response = new Response(STATUS_OK, html);
                break;
            }
            case GET_TIME: {
                String serverTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
                this.response = new Response(STATUS_OK, serverTime);
                break;
            }
            case EVAL_EXPR: {
                Expression expression = reader.readExpression();
                this.expr = expression.getExpression();
                if (expression.isValid()) {
                    this.response = new Response(STATUS_OK, expression.getResultString());
                } else {
                    this.response = new Response(STATUS_400, "illegal expression");
                }
                break;
            }
            case NOT_FOUND: {
                this.response = new Response(STATUS_404, "not found");
            }

        }
    }
}

