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

#ifndef MULTINOMIALNB_H
#define MULTINOMIALNB_H

#include <iostream>
#include <algorithm>    // std::find
#include <map>          // std::map
#include <vector>       // std::vector
#include <sstream>      // std::stringstream
#include <fstream>      // std::fstream
//#include <json/json.h>  // json objects
#include <../dependencies/jsoncpp/include/json/json.h>  // json objects

class MultinomialNB {
private:
    struct Category {
        std::string label;                                  // identifies category
        std::vector<std::vector<std::string> > phrases;     // vector of phrases containing a list of words
        std::map<std::string, double> probabilities;        // map of word to probability given the category
        std::map<std::string, int> bag_of_words;            // map of word to number of apperances in data
        int word_count;                                     // total word count from all phrases
    };
    std::vector<Category> m_training_data;                  // contains category labels and examples for training
    std::vector<std::string> m_vocabulary;                  // vector of unique words across all categories
    std::vector<double> m_category_probabilities;           // probability input is of each category
    int m_phrase_count;

public:
    MultinomialNB() {}

    auto GetTrainingData() { return &m_training_data; }
    auto GetVocabulary() { return m_vocabulary; }
    auto GetCategoryProbabilities() { return m_category_probabilities; }

    void AddTrainingData(std::string label, std::vector<std::string> sentences);
    void ReadInTrainingData(std::string file_name);
    void ReadInTrainingData();  // Default file name: ../src/data.json
    void PrepareData();
    void Train();
    std::string Classify(std::string sentence);
    void DisplayCategoryPercentages();
    bool VocabContains(std::string word);
    std::vector<std::string> Split(std::string sentence);
    int Max(std::vector<double> values);

};

#endif