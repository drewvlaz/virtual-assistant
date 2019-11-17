import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        // Declare variables
        Scanner in = new Scanner(System.in);
        MultinomialNB model = new MultinomialNB();
        String sentence, label;

        model.addTrainingData("../data/data.csv");
        model.prepareData();
        model.train();

        System.out.print("Enter a sentence: ");
        sentence = in.nextLine();
        in.close();
        label = model.classify(sentence);
        model.DisplayCategoryProbabilities();
    }
}
