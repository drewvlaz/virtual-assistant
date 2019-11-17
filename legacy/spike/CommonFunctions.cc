// Definitions of various functions used throughout the project
// All in a common place for ease of access

#include "CommonFunctions.h" 

Json::Value CommonFunctions::ReadInJson(std::string filename) {
    // Read in a json file
    std::fstream file(filename);    // Connection to file
    Json::Value object;             // Recieves contents of file
    // Transfer file contents to JSON object
    file >> object;
    return object;
}

std::string CommonFunctions::Clean(std::string input) {
    // Clean a string of any punctuation
    std::string clean_input;
    for (const auto &c : input) {
        if (!ispunct(c)) {
            clean_input.push_back(c);
        }
    }
    return clean_input;
}

std::string CommonFunctions::GetJoke() {
    // Get a joke from a text file
    std::fstream file("../data/clean_jokes.txt");
    std::string line;
    std::vector<std::string> jokes;

    if (!file) {
        std::cout << "Cant open joke file\n";
        return "There are no jokes.";
    }
    else {
        while (getline(file, line)) {
            // Remove begining numbers and quotes first
            line = line.substr(line.find("\"") + 1);
            line = line.substr(0, line.length() - 1);
            jokes.push_back(line);
        }

        std::random_device dev;
        std::mt19937 rng(dev());
        std::uniform_int_distribution<std::mt19937::result_type> randgen(0,50);

        return jokes.at(randgen(rng));
    }
}