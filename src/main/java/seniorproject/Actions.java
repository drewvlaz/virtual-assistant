package seniorproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.text.DecimalFormat;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;


public class Actions {

    // Read in a file
    // @param file: String of file location
    // @return text: Array of text in file
    public static ArrayList<String> readFile(String file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        ArrayList<String> text = new ArrayList<>();

        while ((line = br.readLine()) != null) {
            text.add(line);
        }
        br.close();

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
        DecimalFormat df = new DecimalFormat("#,###");
        DecimalFormat dfPercent = new DecimalFormat("#%");
        // Access information for a summary
        JSONObject weather = downloadWeather();
        JSONObject currently = (JSONObject)weather.get("currently");
        String summary = (
            "Current Weather"
            + "\n\tSummary: " + currently.get("summary")
            + "\n\tTemperature: " + df.format(currently.get("temperature")) + " " + (char)176 + "F"
            + "\n\tFeels Like: " + df.format(currently.get("apparentTemperature")) + " " + (char)176 + "F"
            + "\n\tChance of Rain: " + dfPercent.format(currently.get("precipProbability"))
            + "\n\tHumidity: " + dfPercent.format(currently.get("humidity"))
        );

        return summary;
    }

    public static String getGrades() throws IOException, ParseException {
        // Inintialize web client options and connect to url
        String url = "https://portal.svsd.net/students";
        WebClient client = new WebClient();
        client.getOptions().setJavaScriptEnabled(true);
        client.getOptions().setCssEnabled(false);
        client.getOptions().setUseInsecureSSL(true);
        HtmlPage page = client.getPage(url);

        // Locate login form
        HtmlForm loginForm = page.getFormByName("frm_Students");
        HtmlTextInput usernameField = loginForm.getInputByName("txt_Username");
        HtmlPasswordInput passwordField = loginForm.getInputByName("txt_Password");

        // Enter credentials and login
        String username = getJsonFromKey("grade_username");
        String password = getJsonFromKey("grade_password");
        usernameField.type(username);
        passwordField.type(password);

        // Refresh page and get grades
        page = client.getPage("https://portal.svsd.net/students/grades.asp");
        // System.out.println(page.asXml());
        Document doc = Jsoup.parse(page.asXml());
        Elements elems = doc.body().getElementsByTag("span");
        List<String> text = elems.eachText();
        String gradeSummary = "";
        client.close();

        // Parse the subject name and grade for each class
        for (int i = 0; i < text.size() - 3; i++) {
            // Grade element is always located 3 elements after the name
            String subject = text.get(i);
            String grade = text.get(i + 3);
            if (subject.contains("[") && grade.contains("%")) {
                gradeSummary += subject.substring(5) + ": " + grade + "\n";
            }
        }

        return gradeSummary.trim();
    }

    // Downloads weather information from the DarkSky api
    // @return JSONObject of the weather information
    private static JSONObject downloadWeather() throws IOException, ParseException {
        // Read in key to access weather api and concatenate url
        JSONParser parser = new JSONParser();
        String weatherKey = getJsonFromKey("weather");
        String latitude = "40.682201";
        String longitude = "-80.104919";
        String address = "https://api.darksky.net/forecast/" + weatherKey + "/" + latitude + "," + longitude;
        // System.out.println(address);

        // Grab json file from api
        String weatherPath = "./src/main/resources/weather.json";
        Document doc = Jsoup.connect(address).validateTLSCertificates(false).ignoreContentType(true).get();
        BufferedWriter writer = new BufferedWriter(new FileWriter(weatherPath));
        writer.write(doc.text());
        writer.close();

        return (JSONObject)parser.parse(new FileReader(weatherPath));
    }

    // Get a key from json file
    // @param element name to retrieve
    // @return element's value
    private static String getJsonFromKey(String element) throws IOException, ParseException {
        // Read in keys
        String keyPath = "./src/main/resources/api_keys.json";
        JSONParser parser = new JSONParser();
        JSONObject keys = (JSONObject)parser.parse(new FileReader(keyPath));

        return keys.get(element).toString();
    }
}
