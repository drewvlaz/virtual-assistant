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

#include "MultinomialNB.h"

void MultinomialNB::AddTrainingData(std::string label, std::vector<std::string> sentences) {
    std::vector<std::vector<std::string>> split_sentences;
    for(std::string sentence : sentences) {
        split_sentences.push_back(Split(sentence));
    }
    m_training_data.push_back({label, split_sentences});
}

void MultinomialNB::ReadInTrainingData(std::string file_name) {
    std::ifstream file(file_name);       // Connection to file
    Json::Value data_set;               // Recieves contents of file
    // Transfer file contents to JSON object
    file >> data_set;
    // For each data set, add label and sentences to m_training_data
    for(const auto &label : data_set.getMemberNames()) {
        std::vector<std::vector<std::string>> clean_data;
        for(const auto &sentence : data_set[label]) {
            clean_data.push_back(Split(sentence.toStyledString()));
        }
        m_training_data.push_back({label, clean_data});
    }
}

void MultinomialNB::ReadInTrainingData() {
    ReadInTrainingData("../src/data.json");
}

void MultinomialNB::PrepareData() {
    for(Category &category : m_training_data) {
        for(const std::vector<std::string> &phrase : category.phrases) {
            for(const std::string &word : phrase) {
                if(!VocabContains(word)) {
                    m_vocabulary.push_back(word);
                }
                ++category.bag_of_words[word];
                ++category.word_count;
            }
            ++m_phrase_count;
        }
    }
}

void MultinomialNB::Train() {
    for(Category &category : m_training_data) {
        for(const std::vector<std::string> &phrase : category.phrases) {
            for(const std::string &word : phrase) {
                category.probabilities[word] = {
                    // calculate probability of a word given a category
                    // add 1 to numerator and add vocab size to denominator for laplace smoothing
                    static_cast<double>(category.bag_of_words[word] + 1)
                    / (category.word_count + m_vocabulary.size())
                };
            }
        }
    }
}

std::string MultinomialNB::Classify(std::string sentence) {
    std::vector<std::string> split_string {Split(sentence)};
    m_category_probabilities.resize(m_training_data.size());

    for(int i=0; i<m_training_data.size(); ++i) {
        // vectors initialize to 0, since multiplying, set it to 1
        m_category_probabilities.at(i) = 1;
        for(const std::string &word: split_string) {
            // check to see if training data of a category contains the target word
            if(m_training_data.at(i).bag_of_words[word]) {
                // P(c|X)  *=  P(x1|c)P(x2|c)...P(xn|c)
                // multiply probability of the target word given the category
                m_category_probabilities.at(i) *= m_training_data.at(i).probabilities[word];
            }
            else {
                m_category_probabilities.at(i) *= static_cast<double>(1) / m_vocabulary.size();
            }
        }
        // P(c|X) *= P(c)
        // P(c) is the num of phrases in category / total num of phrases
        m_category_probabilities.at(i) *= m_training_data.at(i).phrases.size() / static_cast<double>(m_phrase_count);
    }
    return m_training_data.at(Max(m_category_probabilities)).label;
}

void MultinomialNB::DisplayCategoryPercentages() {
    double sum {0};
    for(double probability : m_category_probabilities) {
        sum += probability;
    }

    for(int i=0; i<m_category_probabilities.size(); ++i) {
        double percentage = m_category_probabilities.at(i) / sum * 100;
        std::cout << m_training_data.at(i).label << " " << percentage << "\n";
    }
}

bool MultinomialNB::VocabContains(std::string word) {
    // std::find returns an iterator to element if found, 
    // else returns end of the vector in O(n) time
    return std::find(
        m_vocabulary.begin(),
        m_vocabulary.end(),
        word
    ) != m_vocabulary.end();
}

std::vector<std::string> MultinomialNB::Split(std::string sentence) {
    std::string clean_sentence;                 // Sentence without punctuation
    for(const auto &c : sentence) {
        if(!ispunct(c)) {
            clean_sentence.push_back(c);
        }
    }
    std::string buffer;                         // Buffer string
    std::stringstream stream {clean_sentence};  // Insert string into a stream
    std::vector<std::string> tokens;            // Vector to hold words
    while (stream >> buffer){
        tokens.push_back(buffer);
    }
    return tokens;
}

int MultinomialNB::Max(std::vector<double> values) {
    double max {values.at(0)};
    double num;
    int index {0};
    for(int i=0; i<values.size(); ++i) {
        num = values.at(i);
        if(num > max) {
            max = num;
            index = i;
        }
    }
    return index;
}