package app;

import java.util.Scanner;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        // Declare variables
        Scanner in = new Scanner(System.in);
        MultinomialNB model = new MultinomialNB();
        String sentence;

        model.addTrainingData("./src/main/resources/data.csv");
        model.prepareData();
        model.train();

        System.out.print("Enter a sentence: ");
        sentence = in.nextLine();
        in.close();
        model.classify(sentence);
        model.DisplayCategoryProbabilities();

        System.out.println(Actions.getJoke());
    }
}
