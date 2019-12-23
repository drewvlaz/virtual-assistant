package seniorproject;

import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Weather {
    public static void main(String[] args) throws IOException, ParseException {
        String summary = getWeatherSummary();
        System.out.println(summary);
    }

    // Downloads weather information from the DarkSky api
    // @return JSONObject of the weather information
    public static JSONObject downloadWeather() throws IOException, ParseException {
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
}