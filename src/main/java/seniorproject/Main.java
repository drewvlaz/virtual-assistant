package seniorproject;

import java.util.Scanner;
import java.io.IOException;

import org.json.simple.parser.ParseException;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        // Declare variables
        Scanner sc = new Scanner(System.in);
        MultinomialNB model = new MultinomialNB();
        String sentence, label;

        model.addTrainingData("./src/main/resources/data.csv");
        model.readJson("./src/main/resources/data.json");
        model.prepareData();
        model.train();

        System.out.print("Enter a sentence: ");
        sentence = sc.nextLine();
        sc.close();

        label = model.classify(sentence);
        String probabilities = model.getFormattedProbabilities();
        System.out.println(probabilities);

        // Spacing
        System.out.println("\n");

        // Execute user's request
        switch (label) {
            case "jokes":
                System.out.println(Actions.getJoke());
                break;
            case "grades":
                System.out.println(Actions.getGrades());
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
            default:
                System.out.println("Hmm, I don't understand what you're asking");
                break;
        }
    }
}
