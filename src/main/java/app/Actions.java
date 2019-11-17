package app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Actions {
    public static String getJoke() throws IOException {
        BufferedReader csv = new BufferedReader(new FileReader("./src/main/resources/jokes.txt"));
        String line, joke;
        ArrayList<String> jokes = new ArrayList<>();

        while ((line = csv.readLine()) != null) {
            jokes.add(line);
        }
        csv.close();

        joke = jokes.get((int)(Math.random() * jokes.size()));
        return joke.substring(joke.indexOf("\"") + 1, joke.length() - 1);
    }
}