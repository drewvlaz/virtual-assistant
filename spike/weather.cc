#include <iostream>
#include <map>
#include <curl/curl.h>
#include "CommonFunctions.h"

void RetrieveWeather(){
    // Recieve weather information from the Dark Sky API (1000 calls/day)
    Json::Value api_keys {CommonFunctions::ReadInJson("../data/api_keys.json")};
    std::string url {"https://api.darksky.net/forecast/"};
    std::string key;
    std::string coordinates {"/40.957130,-74.737640"};

    for (const auto &label : api_keys.getMemberNames()) {
        if (label == "weather") {
            url += CommonFunctions::Clean(api_keys[label].toStyledString());
        }
        else {
            std::cout << "Can't retrieve api keys" << std::endl;
        }
    }
    // Get ride of trailing \r from toStyledString
    url = url.substr(0, url.length() - 1);
    url += coordinates;

    CURL *curl {curl_easy_init()};
    FILE *fp;
    CURLcode res;
    char outfilename[FILENAME_MAX] {"weather.json"};
    
    if (curl) {
        fp = fopen(outfilename,"wb");
        curl_easy_setopt(curl, CURLOPT_URL, url.c_str());
        curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, NULL);
        curl_easy_setopt(curl, CURLOPT_WRITEDATA, fp);
        res = curl_easy_perform(curl);
        curl_easy_cleanup(curl);
        fclose(fp);
    }
}

void DisplayWeather() {
    // Display relevant weather information
    Json::Value weather_data {CommonFunctions::ReadInJson("weather.json")};
    std::map<std::string, std::string> weather_info = {
        {"Summary", "summary"},
        {"Temperature", "temperature"},
        {"Feels like", "apparentTemperature"},
        {"Chance of Rain", "precipProbability"},
        {"Humidity", "humidity"}
    };

    // std::cout << weather_data["daily"]["summary"];

    for (const auto &label : weather_info) {
        std::cout << label.first << ": " << weather_data["currently"][label.second] << std::endl;
    }
           
    // std::cout << weather_info.key_comp() << std::endl;
}

int main() {
    RetrieveWeather();
    DisplayWeather();
    return 0;
}