package chapter3;

import java.util.Scanner;
import java.util.Stack;

/**
 * Created by intellij IDEA
 * Author:Raven
 * Date:2017/12/16
 * Time:15:26
 */
public class calc_postfix {
    public static void main(String[] args) {
        System.out.println(evalPostFix());
    }
    public static double evalPostFix() {
        Stack<Double> s = new Stack<>();
        String token;
        Double a, b, result = 0.0;
        boolean isNumber;

        Scanner sc = new Scanner(System.in);
        token = sc.next();
        while (token.charAt(0) != '=') {
            try {
                isNumber = true;
                result = Double.parseDouble(token);
            } catch (Exception e) {
                isNumber = false;
            }
            if (isNumber) {
                s.push(result);
            } else {
                switch (token.charAt(0)) {
                    case '+':
                        a = s.pop();
                        b = s.pop();
                        s.push(a + b);
                        break;
                    case '-':
                        a = s.pop();
                        b = s.pop();
                        s.push(a - b);
                        break;
                    case '*':
                        a = s.pop();
                        b = s.pop();
                        s.push(a * b);
                        break;
                    case '/':
                        a = s.pop();
                        b = s.pop();
                        s.push(a / b);
                        break;
                    case '^':
                        a = s.pop();
                        b = s.pop();
                        s.push(Math.exp(a * Math.log(b)));
                        break;
                    default:
                }
            }
            token = sc.next();
        }
        return s.peek();
    }
}
