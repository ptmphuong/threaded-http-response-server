package api;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ExpressionList {
    private LinkedList<String> exprList = new LinkedList<>();
    private int maxSize = 10;

    public ExpressionList() {}

    public synchronized List<String> add(String expr) {
        if (this.exprList.size() == maxSize) {
            exprList.removeFirst();
        }
        exprList.addLast(expr);
        return this.exprList;
    }

    public LinkedList<String> getExprList() {
        return this.exprList;
    }
}
