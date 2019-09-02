// Definitions of various functions used throughout the project
// All in a common place for ease of access

#ifndef COMMON_FUNCTIONS_H
#define COMMON_FUNCTIONS_H

#include <iostream>
#include <fstream>
#include <vector>
#include <random>
#include <json/json.h>

namespace CommonFunctions {
    Json::Value ReadInJson(std::string filename);
    std::string Clean(std::string input);
}

#endif