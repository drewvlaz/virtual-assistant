package assistant;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
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
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeDriver;

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

    // Get a greeting
    // @return a random greeting from file
    public static String getPoliteResponse() throws IOException {
        ArrayList<String> politeResponse = readFile("./src/main/resources/polite_responses.txt");

        return politeResponse.get((int)(Math.random() * politeResponse.size()));
    }

    // Get a weather summary
    // @return a weather summary from the DarkSky api
    public static String getWeatherSummary() throws IOException, ParseException {
        DecimalFormat df = new DecimalFormat("#,###");
        DecimalFormat dfPercent = new DecimalFormat("#%");
        // Access information for a summary
        JSONObject weather = downloadWeather();
        JSONObject currently = (JSONObject)weather.get("currently");
        String weatherSummary = (
            "Current Weather:\n"
            + "\n\tSummary: " + currently.get("summary")
            + "\n\tTemperature: " + df.format(currently.get("temperature")) + " " + (char)176 + "F"
            + "\n\tFeels Like: " + df.format(currently.get("apparentTemperature")) + " " + (char)176 + "F"
            + "\n\tChance of Rain: " + dfPercent.format(currently.get("precipProbability"))
            + "\n\tHumidity: " + dfPercent.format(currently.get("humidity"))
        );

        return weatherSummary;
    }

    // Downloads grades from portal
    // @return string of grades for each class 
    public static String getGrades() throws IOException, ParseException {
        try {
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
            // String gradeSummary = Calendar.getInstance().getTime() + "\n";
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

            // Remove extra white space 
            gradeSummary = gradeSummary.trim();

            // Write to file
            BufferedWriter bw = new BufferedWriter(new FileWriter("./src/main/resources/grades.txt"));
            bw.write(gradeSummary);
            bw.close();

            // return gradeSummary.substring(gradeSummary.indexOf("\n") + 1);
            return gradeSummary;
        }
        catch (UnknownHostException e) {
            // Can't access portal => use previous grades
            ArrayList<String> cachedGrades = readFile("./src/main/resources/grades.txt");
            String gradeSummary = "";

            for (String grade : cachedGrades) {
                gradeSummary += grade + "\n";
            }

            return gradeSummary.trim();
        }
    }

    // Looks up user input on google
    // @param thing to look up
    // @return String of summary
    public static String lookUp(String input) throws IOException, ParseException {
        try {
            // Set appropriate path to webdriver
            String driverPath = "./src/main/resources/webdrivers/";
            if (isWindows()) {
                driverPath += "chromedriver.exe";
            }
            else if (isLinux()) {
                driverPath += "chromedriver_linux";
            }
            else if (isMac()) {
                driverPath += "chromedriver_mac";
            }
            System.setProperty("webdriver.chrome.driver", driverPath);

            // Create webriver
            WebDriver driver = new ChromeDriver();
            // driver.manage().window().setPosition(new Point(100,200));
            // driver.manage().window().setSize(new Dimension(550,680));

            // Navigate to google and search term
            driver.get("https://www.google.com");
            WebElement searchBox = driver.findElement(By.name("q"));
            searchBox.sendKeys(input);
            searchBox.submit();

            return "Done";
        }
        catch (Exception e) {
            // Can't access internet
            return "Hmm... something went wrong. Please try again later.";
        }
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
        String weatherPath = "./src/main/resources/weather.json";

        // Grab json file from api
        try {
            Document doc = Jsoup.connect(address).validateTLSCertificates(false).ignoreContentType(true).get();
            BufferedWriter writer = new BufferedWriter(new FileWriter(weatherPath));
            writer.write(doc.text());
            writer.close();
        }
        catch (UnknownHostException e) {
            // Can't acces weather api => use previous weather
        }

        return (JSONObject)parser.parse(new FileReader(weatherPath));
    }

    // Get a key from json file
    // @param element name to retrieve
    // @return element's value
    private static String getJsonFromKey(String element) throws IOException, ParseException {
        // Read in keys
        String keyPath = "./src/main/resources/keys.json";
        JSONParser parser = new JSONParser();
        JSONObject keys = (JSONObject)parser.parse(new FileReader(keyPath));

        return keys.get(element).toString();
    }

    // Checks if current os is windows
    // @return boolean whether its windows
    private static boolean isWindows() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.indexOf("win") >= 0;
    }

    // Checks if current os is linux
    // @return boolean whether its linux
    private static boolean isLinux() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0;
    }

    // Checks if current os is mac
    // @return boolean whether its mac
    private static boolean isMac() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.indexOf("mac") >= 0;
    }
}