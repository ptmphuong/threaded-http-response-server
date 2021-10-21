package view;

import api.*;
import java.time.LocalDateTime;

public class StatusHtml {

    public static String buildHtml(RequestTimeList evalTimeList, RequestTimeList getTimeTimeList, ExpressionList expressionList, LocalDateTime timeNow) {
        ApiCallStats evalNum = new ApiCallStats(evalTimeList, timeNow);
        ApiCallStats getTimeNum = new ApiCallStats(getTimeTimeList, timeNow);
        return concatAllStrings(evalNum, getTimeNum, expressionList);
    }

    private static String concatAllStrings(ApiCallStats evalNum, ApiCallStats getTimeNum, ExpressionList expressionList) {
        String countTitle = "\t<h1>API count information</h1>\n";
        String evalTitle = "\t<h3>/api/evalexpression</h3>\n";
        String evalNumStr = formatCallNum(evalNum);
        String getTimeTitle = "<h3>/api/gettime</h3>";
        String getTimeNumStr = formatCallNum(getTimeNum);
        String exprListTitle = "<h1>Last 10 expressions</h1>";
        String exprListStr = formatExprList(expressionList);
        String html = countTitle + evalTitle + evalNumStr + getTimeTitle + getTimeNumStr + exprListTitle + exprListStr;
        return html;
    }

    private static String formatCallNum(ApiCallStats callNum) {
        String res = String.format(
                "\t<ul>\n" +
                "\t  <li>last minute: %d</li>\n" +
                "\t  <li>last hour: %d</li>\n" +
                "\t  <li>last 24 hours: %d</li>\n" +
                "\t  <li>lifetime: %d</li>\n" +
                "\t</ul>\n",
                callNum.getInLastMin(), callNum.getInLastHr(), callNum.getInLast24Hr(), callNum.getInLifetime()
        );
        return res;
    }

    private static String formatExprList(ExpressionList expressionList) {
        String formatted = "";
        for (String expr: expressionList.getExprList()) {
            String newE = "<li>" + expr + "</li>";
            formatted += newE;
        }
        return "<ul>" + formatted + "</ul>";
    }

}
