import java.util.ArrayList;

public class MultinomialNB {
    // Instance variables
    private ArrayList<Category> trainingData;
    private ArrayList<String> vocabulary;
    private ArrayList<Double> categoryProbabilities; 
    private int phraseCount = 0;

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
    public void Train() {
        for (Category category : trainingData) {
            category.calculateProbabilities(vocabulary);            
        }
    }

    // Classify user input
    // @param sentence: sentence to classify to a category
    public void Classify(String sentence) {
        for (Category category : trainingData) {

        }
    }
}