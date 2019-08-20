#include <iostream>
#include <fstream>
#include <vector>
#include <random>

int main() {
    std::fstream file("./data/jokes.txt");
    std::string line;
    std::vector<std::string> jokes;

    if(!file) {
        std::cout << "Cant open\n";
    }

    while(getline(file, line)) {
        jokes.push_back(line.substr(line.find("\"")));
    }

    std::random_device dev;
    std::mt19937 rng(dev());
    std::uniform_int_distribution<std::mt19937::result_type> randgen(0,1621);

    std::cout << jokes.at(randgen(rng)) << std::endl;
}