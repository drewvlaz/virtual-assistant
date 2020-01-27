package seniorproject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Login {
    public static void main(String[] args) throws IOException {
        // Inintialize web client options and connect to url
        String url = "https://portal.svsd.net/students";
        WebClient client = new WebClient();
        client.getOptions().setJavaScriptEnabled(true);
        client.getOptions().setCssEnabled(false);
        client.getOptions().setUseInsecureSSL(true);
        HtmlPage page = client.getPage(url);

        // Locate login form
        HtmlForm loginForm = page.getFormByName("frm_Students");
        HtmlTextInput username = loginForm.getInputByName("txt_Username");
        HtmlPasswordInput password = loginForm.getInputByName("txt_Password");

        // Enter credentials and login
        username.type("vlasnikds");
        password.type("gUhz@9600\n");

        // Refresh page and get grades
        page = client.getPage("https://portal.svsd.net/students/grades.asp");
        Document doc = Jsoup.parse(page.asXml());
        Elements elems = doc.body().getElementsByTag("span");
        List<String> text = elems.eachText();

        // Parse the subject name and grade for each class
        for (int i = 0; i < text.size() - 3; i++) {
            // Grade element is always located 3 elements after the name
            String subject = text.get(i);
            String grade = text.get(i + 3);
            if (subject.contains("[") && grade.contains("%")) {
                System.out.println(subject.substring(5) + ": " + grade);
            }
        }
    }
}
