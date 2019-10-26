/* This class will be used like a struct.
It will keep track of the characteristics 
of each category in the training data */

import java.util.ArrayList;
import java.util.HashMap;

public class Category {
    // Instance variables
    private String label;
    private ArrayList<ArrayList<String>> phrases;
    private HashMap<String, Double> probabilities;
    private HashMap<String, Integer> bagOfWords;
    int wordCount;

    // Constructor
    public Category() {

    }
}