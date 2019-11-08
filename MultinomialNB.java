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
// equally and indepently to the class (c), hence the name
// Because this is calculated for each class with a given
// feature set, the denominator remains constant and can
// therefore be ignored

import java.util.ArrayList;
import java.util.Collections;

public class MultinomialNB {
    // Instance variables
    private ArrayList<Category> trainingData = new ArrayList<>();
    private ArrayList<String> vocabulary = new ArrayList<>();
    private ArrayList<Double> categoryProbabilities = new ArrayList<>(); 
    private int phraseCount = 0;

    // Constructor
    public MultinomialNB() {}
    public MultinomialNB(ArrayList<Category> trainingData) {
        this.trainingData = trainingData;
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

    // Add training data
    // @param label: label of data category
    // @param sentences: training data sentence examples
    public void addTrainingData(String label, ArrayList<String> sentences) {
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
        // Split sentence into words
        String[] words = sentence.split(" ");

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
        return trainingData.get(
            categoryProbabilities.indexOf(
                Collections.max(
                    categoryProbabilities
                )
            )
        ).getLabel();
    }

    // Display the probabilities for each category
    public void DisplayCategoryProbabilities() {
        double sum = 0;
        
        for (double probability : categoryProbabilities) {
            sum += probability;
        }

        for (int i = 0; i < categoryProbabilities.size(); i++) {
            double percentage = categoryProbabilities.get(i) / sum * 100;
            System.out.printf("%s: %.2f%%%n", trainingData.get(i).getLabel(), percentage);
        }
    }
}