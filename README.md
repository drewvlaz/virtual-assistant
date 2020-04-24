# A Simple Virtual Assistant


## Overview
This is a virtual assistant aimed for students powered by Naive Bayes.
Actions available include responding to greetings and praise, telling jokes, 
retrieving the weather, checking grades, playing music, and looking things up.

## Demo
![Simple Demo](/assets/Demo.gif)

## Files
There are also some files that need to be created for full functionality.
Create a ```keys.json``` file in ```/src/main/resources``` using the following format 
for weather, grades, and music functionality.
```javascript
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
Also be sure to have javafx installed. Instructions can be found [here](https://openjfx.io/index.html).

### Linux, Mac, and Windows
<!-- <p align="center">
  <img src="/assets/building.png" alt="Build commands" width="450"/>
</p> -->
```bash
git clone https://github.com/drewvlaz/virtual-assistant && cd virtual-assistant
gradle shadowjar && java -jar build/libs/virtual-assistant-1.0-all.jar
```
