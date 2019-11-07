
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        // Declare variables
        Scanner in = new Scanner(System.in);
        MultinomialNB model = new MultinomialNB();
        String sentence, label;
        ArrayList<String> grades = new ArrayList<>();
            grades.addAll(
                Arrays.asList(
                    "what are my grades like",
                    "how did i do on that test",
                    "what is my english grade like",
                    "how are my grades",
                    "what did i score on that quiz",
                    "did i turn in my homework",
                    "grade grades score scores, test, class",
                    "math english science subject subjects",
                    "i hope i did well on that test quiz",
                    "what do my grades look like",
                    "how are my grades doing",
                    "show me my grades",
                    "i want to see my grades now",
                    "can i see my grades",
                    "has my teacher updated my grades",
                    "how did i do on the homework assignment"
            )
        );

        ArrayList<String> weather = new ArrayList<>();
            grades.addAll(
                Arrays.asList(
                    "how is the weather",
                    "is it rainy outside",
                    "tell me is it sunnny outside",
                    "what is the weather like for the week",
                    "how cloudy is it",
                    "should i stay inside today",
                    "is it thundering and lightening",
                    "when will it stop raining",
                    "weather outside sunny rainy",
                    "can you tell me the weather",
                    "raining weather hot cold temperature",
                    "cloudy inside today outside tomorrow week"
            )
        );

        model.addTrainingData(
            "grades",
            grades
        );
        model.addTrainingData(
            "weather",
            weather
        );

        model.prepareData();
        model.train();

        System.out.print("Enter a sentence: ");
        sentence = in.nextLine();
        label = model.classify(sentence);
        System.out.println(label);
    }
}