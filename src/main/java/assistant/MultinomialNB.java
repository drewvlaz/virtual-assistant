// This program is a multinomial Naive Bayes classifier for
// for text classification
//
// The Naive Bayes classifier is based off of Bayes Theorem:
//
//                      P(B|A)P(A)
//           P(A|B)  =  ----------
//                         P(B)
//
// Implementation:
//
//          P(c|X)  =  P(x1|c)P(x2|c)...P(xn|c)P(c)
//
// Naive Bayes assumes each feature of set (X) contributes
// equally and indepently to the class (c), hence the name.
// Because this is calculated for each class with a given
// feature set, the denominator (P(B)) remains constant 
// because it is the given input to be classified and can
// therefore be disregarded in the calculations

package assistant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MultinomialNB {
    // Instance variables
    private ArrayList<Category> trainingData = new ArrayList<Category>();
    private ArrayList<String> vocabulary = new ArrayList<String>();
    private ArrayList<Double> categoryProbabilities = new ArrayList<Double>();
    private int phraseCount = 0;

    // Constructor
    public MultinomialNB() {}
    public MultinomialNB(String path) {
        readTrainingData(path);
    }

    // Accessor methods
    public ArrayList<Category> getTrainingData() {
        return trainingData;
    }

    public ArrayList<String> getVocabulary() {
        return vocabulary;
    }

    public ArrayList<Double> getCategoryProbabilities() {
        return categoryProbabilities;
    }

    // Reads in training data from json file and parses it
    // @param path to training data
    public void readTrainingData(String path) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject file = (JSONObject)parser.parse(new FileReader(path));
            String[] labels = clean(parseJson(file.get("labels")));

            // For each label, read in the training and add it to model
            for (String label : labels) {
                String[] sentences = clean(parseJson(file.get(label)));
                addTrainingData(label, sentences);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    // Parse json object
    // @param object
    // @return object parsed as array
    private static String[] parseJson(Object obj) {
        String temp = "" + obj;
        return temp.split(",");
    }

    // Add training data
    // @param label: label of data category
    // @param sentences: training data sentence examples
    public void addTrainingData(String label, String[] sentences) {
        // Holds the words of each sentence
        ArrayList<String[]> phrases = new ArrayList<>();

        for (String sentence : sentences) {
            phrases.add(sentence.split(" "));
        }

        trainingData.add(new Category(label, phrases));
    }

    // Prepare the data by counting words and updating the vocabulary
    public void prepareData() {
        for (Category category : trainingData) {
            // Get count of each word in data set and adds it to vocabulary
            category.countWords(vocabulary);
            // Count number of phrases there are total from all data sets
            phraseCount += category.getPhrases().size();
        }
    }

    // Train model
    public void train() {
        for (Category category : trainingData) {
            category.calculateProbabilities(vocabulary);
        }
    }

    // Classify user input
    // @param sentence: sentence to classify to a category
    public String classify(String sentence) {
        // Reset probabilities
        categoryProbabilities = new ArrayList<Double>();

        // Split sentence into words
        String[] words = clean(sentence).split(" ");

        for (Category category : trainingData) {
            // Initialize to 1 since multiplying
            double probability = 1;

            for (String word : words) {
                // If category contains the word
                if (category.getBagOfWords().containsKey(word)) {
                    // P(c|X)  *=  P(x1|c)P(x2|c)...P(xn|c)
                    // Multiply probability of the target word given the category
                    probability *= category.getProbabilities().get(word);
                }
                else {
                    // If word not in category, multiply by standard value
                    probability *= 1.0 / vocabulary.size();
                }
            }
            // P(c|X) *= P(c)
            // P(c) is the num of phrases in category / total num of phrases
            probability *= (double)category.getPhrases().size() / phraseCount;

            // Update category probability
            categoryProbabilities.add(probability);
        }

        // Return the category name with highest probability
        if (confidentModel()) {
            return trainingData.get(
                categoryProbabilities.indexOf(
                    Collections.max(
                        categoryProbabilities
                    )
                )
            ).getLabel();
        }
        else {
            return "Unknown";
        }
    }

    // Calculate probabilities from user input and format
    // @return probabilities of each class
    public String getFormattedProbabilities() {
        DecimalFormat df = new DecimalFormat("0.00%");
        double[] probabilities = getPredictionProbabilities();
        String formattedProbs = "";

        for (int i = 0; i < categoryProbabilities.size(); i++) {
            formattedProbs += trainingData.get(i).getLabel() + ": " + df.format(probabilities[i]) + "\n";
        }
        
        return formattedProbs;
    }

    private boolean confidentModel() {
        double[] probabilities = getPredictionProbabilities();
        boolean confident = false;

        for (double prob : probabilities) {
            if (prob > .4) {
                confident = true;
                break;
            }
        }

        return confident;
    }

    // Calculate the probabilities for each category
    // @return formatted String of probabilities for each category
    private double[] getPredictionProbabilities() {
        double[] probabilities = new double[trainingData.size()];
        double sum = 0;

        for (double probability : categoryProbabilities) {
            sum += probability;
        }

        for (int i = 0; i < categoryProbabilities.size(); i++) {
            probabilities[i] = categoryProbabilities.get(i) / sum;
        }

        return probabilities;
    }

    // Clean the input data by removing special characters
    // and converting to lowercase
    // @param sentence: input to clean
    private String clean(String input) {
        String clean = "";
        input = input.toLowerCase().trim();

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isLetter(c) || c == ' ') {
                clean += c;
            }
        }

        return clean;
    }

    // Clean the input data by removing special characters
    // and converting to lowercase
    // @param sentence: input to clean
    private String[] clean(String[] input) {
        for (int i = 0; i < input.length; i++) {
            input[i] = clean(input[i]);
        }

        return input;
	}
}
