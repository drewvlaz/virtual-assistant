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
    for(const auto &c : input) {
        if(!ispunct(c)) {
            clean_input.push_back(c);
        }
    }
    return clean_input;
}