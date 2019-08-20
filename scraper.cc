#include<iostream>
#include<../dependencies/Curl/include/curl/curl.h> 

int main(int argc, char **argv) {
    CURL *curl;
    curl = curl_easy_init();
    curl_easy_setopt(curl, CURLOPT_URL, argv[1]);

}

