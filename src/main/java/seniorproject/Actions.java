package seniorproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

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
    // @return a random joke from file
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

    // Get a weather summary
    // @return a weather summary from the DarkSky api
    public static String getWeatherSummary() throws IOException, ParseException {
        // Access information for a summary
        JSONObject weather = downloadWeather();
        JSONObject currently = (JSONObject)weather.get("currently");
        String summary = (
            "Current Weather"
            + "\n\tSummary: " + currently.get("summary")
            + "\n\tTemperature: " + currently.get("temperature") + " " + (char)176 + "F"
            + "\n\tFeels Like: " + currently.get("apparentTemperature") + " " + (char)176 + "F"
            + "\n\tChance of Rain: " + currently.get("precipProbability") + " %"
            + "\n\tHumidity: " + ((Double)currently.get("humidity") * 100) + " %"
        );

        return summary;
    }

    // Downloads weather information from the DarkSky api
    // @return JSONObject of the weather information
    private static JSONObject downloadWeather() throws IOException, ParseException {
        // Read in key to access weather api
        String keyPath = "./src/main/resources/api_keys.json";
        JSONParser parser = new JSONParser();
        JSONObject keys = (JSONObject)parser.parse(new FileReader(keyPath));
        
        // Concatenate url address
        String weatherKey = keys.get("weather").toString();
        String address = "https://api.darksky.net/forecast/" + weatherKey + "/40.957130,-74.737640";

        // Grab json file from api
        String weatherPath = "./src/main/resources/weather.json";
        Document doc = Jsoup.connect(address).ignoreContentType(true).get();
        BufferedWriter writer = new BufferedWriter(new FileWriter(weatherPath));
        writer.write(doc.text());
        writer.close();

        return (JSONObject)parser.parse(new FileReader(weatherPath));
    }
}