# A Simple Virtual Assistant

***Notice: Still in development***

## Overview
This is a virtual assistant aimed for students powered by Naive Bayes.
Actions currently in development are checking grades, exchanging greetings, telling jokes,
searching the internet, and checking the weather.

## Demo
![Simple Demo](/assets/Messaging_Demo.webm)

## Files
There are also some files that need to be created for full functionality.
Create a keys.json file in the following format to gain weather and grades retrieval functionality.
```
{
    "weather": "Dark Sky API Key",
    "grade_username": "username",
    "grade_password": "password"
}
```

## Building

This project uses Gradle to handle build configuration. Be sure to have it installed on your system.

### Linux, Mac, and Windows
```
gradle shadowjar
java -jar build/libs/virtual-assistant-1.0-all.jar
```