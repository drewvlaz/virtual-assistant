#include <iostream>
#include "MultinomialNB.h"
#include "Actions.h" 
#include "CommonFunctions.h"

enum Classification {
    grades,
    greeting,
    jokes,
    search,
    weather
};

void ExecuteUserRequest(Classification classification) {
    switch (classification) {
        case greeting:
            std::cout << "Hello, my name is TBD" << "\n";
            break;
        
        default:
            break;
        }
}

int main() {
    MultinomialNB model;

    model.ReadInTrainingData();
    model.PrepareData();
    model.Train();

    enum Classification test = greeting;
    ExecuteUserRequest(test);

    std::cout << Actions::GetJoke() << "\n\n";

    std::string sentence;
    std::cout << "Enter a sentence: ";
    std::getline(std::cin, sentence);
    std::string prediction = model.Classify(sentence);
    std::cout << sentence << " -> " << prediction << "\n\n";
    model.DisplayCategoryPercentages();

    return 0;
}