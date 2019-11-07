import java.util.ArrayList;

public class MultinomialNB {
    // Instance variables
    private ArrayList<Category> trainingData;
    private ArrayList<String> vocabulary;
    private ArrayList<Double> categoryProbabilities; 
    private int phraseCount;

    // Constructor
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
}