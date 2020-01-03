package app;

import java.util.Scanner;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
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
        System.out.println();

        // Execute user's request
        switch (label) {
            case "jokes":
                System.out.println(Actions.getJoke());
                break;
            case "grades":
                System.out.println("Grades pending");
                break;
            case "weather":
                System.out.println("Retrieving weather data");
                break;
            case "greeting":
                System.out.println(Actions.getGreeting());
                break;
            default:
                System.out.println("Hmm... let me look that up");
                break;
        }
    }
}
