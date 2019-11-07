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

    // Prepare the data
    public void prepareData() {
        for (Category category : trainingData) {
            for (String[] phrase : category.getPhrases()) {
                for (String word : phrase) {
                    if (!vocabulary.contains(word)) {
                        vocabulary.add(word);
                    }
                    // Count number of times each word appears
                    category.incrementBagOfWords(word);
                }
                // Count number of phrases there are in data set
                phraseCount++;
            }
        }
    }
}