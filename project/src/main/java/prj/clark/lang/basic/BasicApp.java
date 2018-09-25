package prj.clark.lang.basic;

import prj.clark.lang.basic.env.BasicEvaluator;
import prj.clark.lang.basic.env.Evaluator;

import java.io.IOException;
import java.util.Scanner;

public class BasicApp {
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        Evaluator eval = new BasicEvaluator();

        while (true) {
            System.out.print(" >>> ");
            String input = in.nextLine();
            System.out.println("\t" + eval.eval(input));
        }
    }
}
