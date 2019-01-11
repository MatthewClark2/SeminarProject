package prj.clark.lang.basic;

import prj.clark.lang.basic.env.BasicEvaluator;
import prj.clark.lang.basic.env.Evaluator;

import java.io.IOException;
import java.util.Scanner;

public class BasicApp {
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        Evaluator eval = new BasicEvaluator();

        System.out.println("Welcome to this simplistic REPL. Type !! or EOF to exit.");

        while (true) {
            displayPrompt();

            // Since checking if more input is available is a blocking operation, we do it in the same breath that we
            // assign the next read line.
            String input = in.hasNextLine() ? in.nextLine().trim() : "!!";

            if (input.equals("!!")) {
                break;
            } else if (! input.isEmpty()) {
                try {
                    System.out.println("\t" + eval.eval(input));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private static void displayPrompt() {
        System.out.print(" >>> ");
    }
}
