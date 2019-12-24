package seniorproject;

import java.util.Scanner;
import java.io.IOException;

import org.json.simple.parser.ParseException;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        // Declare variables
        Scanner in = new Scanner(System.in);
        MultinomialNB model = new MultinomialNB();
        String sentence, label;

        model.addTrainingData("./src/main/resources/data.csv");
        model.prepareData();
        model.train();

        System.out.print("Enter a sentence: ");
        sentence = in.nextLine();
        in.close();

        label = model.classify(sentence);
        model.DisplayCategoryProbabilities();

        // Spacing
        System.out.println("\n");

        // Execute user's request
        switch (label) {
            case "jokes":
                System.out.println(Actions.getJoke());
                break;
            case "grades":
                System.out.println("Grades pending");
                break;
            case "weather":
                System.out.println(Actions.getWeatherSummary());
                break;
            case "greeting":
                System.out.println(Actions.getGreeting());
                break;
            case "search":
                System.out.println("What would you like me to look-up? ");
                break;
        }

    }
}
