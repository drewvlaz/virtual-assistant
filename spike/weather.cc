#include <iostream>
#include <curl/curl.h>
#include "CommonFunctions.h"

int main() {
    Json::Value key = CommonFunctions::ReadInJson("../data/api_keys.json");

    for(const auto &label : key.getMemberNames()) {
        if(label == "weather"){
            std::cout << CommonFunctions::Clean(key[label].toStyledString()) << "\n";
        }
    }
}