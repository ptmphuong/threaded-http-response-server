package model;

public class StartLine {
    private String method;
    private String target;
//    private String

    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String EVAL_EXPR = "/api/evalexpression";
    private static final String GET_TIME = "/api/gettime";
    private static final String GET_STATUS = "/status.html";

    public StartLine(String startLine) {
        String[] strings = startLine.split(" ");
        this.method = strings[0];
        this.target = strings[1];
    }

    public boolean isEvalExpr() {
        return this.method.equals(POST) && this.target.equals(EVAL_EXPR);
    }

    public boolean isGetTime() {
        return this.method.equals(GET) && this.target.equals(GET_TIME);
    }

    public boolean isGetStatus() {
        return this.method.equals(GET) && this.target.equals(GET_STATUS);
    }

    public boolean isValid() {
        return isEvalExpr() || isGetTime() || isGetStatus();
    }

    @Override
    public String toString() {
        return "model.StartLine{" +
                "method='" + method + '\'' +
                ", target='" + target + '\'' +
                '}';
    }
}
