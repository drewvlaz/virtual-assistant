package app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Actions {

    // Read in a file
    // @param file: String of file location
    // @return text: Array of text in file
    public static ArrayList<String> readFile(String file) throws IOException {
        BufferedReader csv = new BufferedReader(new FileReader(file));
        String line;
        ArrayList<String> text = new ArrayList<>();

        while ((line = csv.readLine()) != null) {
            text.add(line);
        }
        csv.close();

        return text;
    }

    // Get a joke
    // @return joke: random joke from file
    public static String getJoke() throws IOException {
        ArrayList<String> jokes = readFile("./src/main/resources/jokes.txt");
        String joke;

        joke = jokes.get((int)(Math.random() * jokes.size()));
        return joke.substring(joke.indexOf("\"") + 1, joke.length() - 1);
    }

    // Get a greeting
    // @return a random greeting from file
    public static String getGreeting() throws IOException {
        ArrayList<String> greetings = readFile("./src/main/resources/greetings.txt");

        return greetings.get((int)(Math.random() * greetings.size()));

    }
}