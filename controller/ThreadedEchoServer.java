package controller;

import model.RequestType;
import api.ExpressionList;
import api.RequestTimeList;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

public class ThreadedEchoServer {
    public static final int SERVER_PORT = 8080;

    private ExpressionList expressionList = new ExpressionList();
    private RequestTimeList evalTimeList = new RequestTimeList();
    private RequestTimeList getTimeTimeList = new RequestTimeList();

    public void runServer() throws IOException, InterruptedException {
        System.out.println("Starting Threaded echo server");

        ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
        try {
            System.out.println("Start to accept incoming connections");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                Handler handler = new Handler(clientSocket, evalTimeList, getTimeTimeList, expressionList);
                Thread thread = new Thread(handler);
                thread.start();
                thread.join();
                updateRequestInfo(handler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            serverSocket.close();
        }
    }

    private void updateRequestInfo(Handler handler) {
        LocalDateTime handleTime = handler.getHandleTime();
        RequestType apiType = handler.getApiType();
        switch (apiType) {
            case GET_TIME: {
                this.getTimeTimeList.add(handleTime);
                break;
            }
            case EVAL_EXPR: {
                this.evalTimeList.add(handleTime);
                this.expressionList.add(handler.getExpr());
                break;
            }
        }
    }

}
