# A Simple Virtual Assistant

***Notice: Still in development***

## Overview
This is a virtual assistant aimed for students powered by Naive Bayes.
Actions currently in development are checking grades, exchanging greetings, telling jokes,
searching the internet, and checking the weather.

## Demo
![Simple Demo](/assets/Demo.gif)

## Files
There are also some files that need to be created for full functionality.
Create a keys.json file in the following format to gain weather, grades, and music functionality.
```
{
    "weather": "Dark Sky Weather API"
    "grade_username": "your username",
    "grade_password": "your password",
    "spotify_username": "you username",
    "spotify_password": "your password"
}
```
<!-- <p align="center">
  <img src="/assets/keys_example.png" alt="Keys Example" width="450"/>
</p> -->



## Building

This project uses Gradle to handle build configuration. Be sure to have it installed on your system.

### Linux, Mac, and Windows
<!-- <p align="center">
  <img src="/assets/building.png" alt="Build commands" width="450"/>
</p> -->
```
gradle shadowjar
java -jar build/libs/virtual-assistant-1.0-all.jar
```
