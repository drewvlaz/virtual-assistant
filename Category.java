/* This class will be used like a struct.
It will keep track of the characteristics 
of each category in the training data */

import java.util.ArrayList;
import java.util.HashMap;

public class Category {
    // Instance variables
    private String label;
    private ArrayList<String[]> phrases;
    private HashMap<String, Double> probabilities;
    private HashMap<String, Integer> bagOfWords;
    int totalWordCount;

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
}