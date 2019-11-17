#include "Actions.h" 

std::string Actions::GetJoke() {
    // Get a joke from a text file
    std::fstream file("../data/clean_jokes.txt");
    std::string line;
    std::vector<std::string> jokes;

    if(!file) {
        return "Can't find any jokes.";
    }
    else {
        while(getline(file, line)) {
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