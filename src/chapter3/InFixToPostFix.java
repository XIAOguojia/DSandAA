package chapter3;

import java.util.Scanner;
import java.util.Stack;

/**
 * Created by intellij IDEA
 * Author:Raven
 * Date:2017/12/17
 * Time:11:15
 */

public class InFixToPostFix {
    public static void main(String[] args) {
        Infixtopostfix();
    }

    /**
     * 将中缀表达式装换为后缀表达式
     */
    public static void Infixtopostfix() {
        Stack<Character> s = new Stack<>();
        String expression;
        Character token;
        int i = 0;
        Scanner sc = new Scanner(System.in);
        expression = sc.next();
        while ((token = expression.charAt(i++)) != '=') {
            if (token >= 'a' && token <= 'z') {
                System.out.print(token + " ");
            } else {
                switch (token) {
                    case ')':
                        while (!s.empty() && s.peek() != '(') {
                            System.out.print(s.pop() + " ");
                        }
                        s.pop();
                        break;
                    case '(':
                        s.push(token);
                        break;
                    case '^':
                        while (!s.empty() && !(s.peek() == '^' || s.peek() == '(')) {
                            System.out.print(s.pop());
                        }
                        s.push(token);
                        break;
                    case '*':
                    case '/':
                        while (!s.empty() && s.peek() != '+' && s.peek() != '-' && s.peek() != '(') {
                            System.out.print(s.pop());
                        }
                        s.push(token);
                        break;
                    case '+':
                    case '-':
                        while (!s.empty() && s.peek() != '*' && s.peek() != '/' && s.peek() != '(') {
                            System.out.print(s.pop() + " ");
                        }
                        s.push(token);
                        break;
                    default:
                }
            }
        }

        while (!s.empty()) {
            System.out.print(s.pop());
        }
        System.out.println();
    }
}