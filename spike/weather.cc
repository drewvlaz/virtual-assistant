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
    CURLcode result;
    char outfilename[FILENAME_MAX] {"weather.json"};
    
    if (curl) {
        fp = fopen(outfilename,"wb");
        curl_easy_setopt(curl, CURLOPT_URL, url.c_str());
        curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, NULL);
        curl_easy_setopt(curl, CURLOPT_WRITEDATA, fp);
        // Request webpage, result stores return code
        result = curl_easy_perform(curl);
        curl_easy_cleanup(curl);
        fclose(fp);
    }
}

void DisplayWeather() {
    // Display relevant weather information
    Json::Value weather_data {CommonFunctions::ReadInJson("weather.json")};
    std::array<std::string, 5> weather_summary;

    // Clean data and add it to array
    weather_summary.at(0) = "Summary: " + weather_data["currently"]["summary"].toStyledString();
    weather_summary.at(1) = 
        "Temperature: "
        + weather_data["currently"]["temperature"].toStyledString().substr(0, find("."))
        + "\n";
    weather_summary.at(2) = 
        "Feels like: " 
        + weather_data["currently"]["apparentTemperature"].toStyledString().substr(0, 2)
        + "\n";



    for (const auto &info : weather_summary) {
        std::cout << info;
    }

    // Maps are unordered
    // std::map<std::string, std::string> weather_info = {
    //     {"Summary", "summary"},
    //     {"Temperature", "temperature"},
    //     {"Feels like", "apparentTemperature"},
    //     {"Chance of Rain", "precipProbability"},
    //     {"Humidity", "humidity"}
    // };
    // for (const auto &label : weather_info) {
    //     std::cout << label.first << ": " << weather_data["currently"][label.second] << std::endl;
    // }
}

int main() {
    RetrieveWeather();
    DisplayWeather();
    return 0;
}