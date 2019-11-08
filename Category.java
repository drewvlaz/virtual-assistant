/* This class will be used like a struct.
It will keep track of the characteristics 
of each category in the training data */

import java.util.ArrayList;
import java.util.HashMap;

public class Category {
    // Instance variables
    private String label;
    private ArrayList<String[]> phrases = new ArrayList<>();
    private HashMap<String, Double> probabilities = new HashMap<String, Double>();
    private HashMap<String, Integer> bagOfWords = new HashMap<String, Integer>();
    int totalWordCount = 1;

    // Constructor
    public Category(String label, ArrayList<String[]> phrases){
        this.label = label;
        this.phrases = phrases;
    }

    // Accessor methods
    public String getLabel() {
        return label;
    }

    public ArrayList<String[]> getPhrases() {
        return phrases;
    }

    public HashMap<String, Double> getProbabilities() {
        return probabilities;
    }

    public HashMap<String, Integer> getBagOfWords() {
        return bagOfWords;
    }

    public int getWordCount() {
        return totalWordCount;
    }

    // Increment word count for word and total word count
    // @param word: word to add count of
    public void incrementBagOfWords(String word) {
        int count = bagOfWords.containsKey(word) ? bagOfWords.get(word) : 0;
        bagOfWords.put(word, count + 1);
        totalWordCount++;
    }

    // Get count of each word in data set and adds it to vocabulary
    // @param vocabulary: list containing all unique words in data set
    public void countWords(ArrayList<String> vocabulary) {
        for (String[] phrase : phrases) {
            for (String word : phrase) {
                if (!vocabulary.contains(word)) {
                    vocabulary.add(word);
                }
                // Count number of times each word appears
                incrementBagOfWords(word);
            }
        }
    }

    // Calculate the probability of each word given the current category
    // @param vocabulary: list containing all unique words in data set
    public void calculateProbabilities(ArrayList<String> vocabulary) {
        for (String[] phrase : phrases) {
            for (String word : phrase) {
                // Calculate probability of a word given a category
                // Add 1 to numerator and add vocab size to denominator for laplace smoothing
                probabilities.put(
                    word,
                    (double)(bagOfWords.get(word) + 1) / (totalWordCount + vocabulary.size())
                );
            }
        }
    }
}