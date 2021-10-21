import controller.ThreadedEchoServer;
import java.io.IOException;

/**
 * Connection cannot close in Chrome (I still can't figure out why). But it works properly on Postman and Safari.
 *
 * For api/evalexpression stats include both success requests and bad requests on the Status page.
 * That means bad requests are also counted in the last min/hour/24hrs/lifetime count, and listed in the expression list.
 *
 * Program structure:
 *  Main runs ThreadedEchoServer
 *      ThreadEchoServer spawns Handler when there's a connection
 *      Handler's information is updated to ExpressionList, RequestTimeList - eval, RequestTimeList - getTime when done.
 *
 *          In Handler, Reader parse StartLine => decide what request type it is.
 *
 */

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        ThreadedEchoServer server = new ThreadedEchoServer();
        server.runServer();
    }
}
