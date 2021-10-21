package model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Expression {
    private String expression;
    private Set<Character> operationSet = new HashSet<>(Arrays.asList('+', '-'));
    private Set<Character> parathesesSet = new HashSet<>(Arrays.asList('(', ')'));

    public Expression(String expression) {
        this.expression = expression;
    }

    public boolean isValid() {
        if (this.expression.length() == 0) return false;
        if (this.expression.startsWith("+")) return false;
        if (this.expression.endsWith("-") || this.expression.endsWith("+")) return false;
        for (char c: this.expression.toCharArray()) {
            if (!(operationSet.contains(c) || parathesesSet.contains(c) || Character.isDigit(c))) {
                System.out.println("Bad expression. Poison char: " + c);
                return false;
            }
        }
        return true;
    }

    public String getExpression() {
        return this.expression;
    }

    public int getResult() {
        if (!this.isValid()) return 0;
        return this.eval(this.expression);
    }

    public String getResultString() {
        return String.valueOf(this.getResult());
    }

    private int eval(String s) {
        final char ADD = '+';
        final char DEDUCT = '-';
        int result = 0;
        int curInd = 0;

        int numSoFar = 0;
        char op = '+';

        s = s + " "; // dummy space at the end

        while (curInd < s.length()) {
            char curChar = s.charAt(curInd);
            if (parathesesSet.contains(curChar)) {
                curInd++;
                continue;
            }
            if (Character.isDigit(curChar)) {
                int num = Character.getNumericValue(curChar);
                numSoFar = numSoFar*10 + num;
            } else {
                if (op == ADD) result += numSoFar;
                else if (op == DEDUCT) result -= numSoFar;
                numSoFar = 0;
                op = curChar;
            }
            curInd++;
        }
        return result;
    }
}
