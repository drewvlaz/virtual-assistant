# A Simple Virtual Assistant

***Notice: Still in development and currently translating to Java***

## Overview
This is a virtual assistant aimed for students powered by Naive Bayes.
Actions currently in development are checking grades, greetings, telling jokes,
searching the internet, and checking the weather.

## Building

### Linux and Mac
```
mkdir build && cd build
cmake ..
make
```
The VirtualAssistant executable will be created in the bin/ directory

### Windows
Good luck

## Directory Structure
* data
  * data for training and jokes dataset
* dependencies
  * C++ libraries used in the project
* include
  * all of the header files
* misc
  * miscellaneous files relevant to the development process
* spike
  * developmental features
* src
  * main source files for project

```
virtual-assistant
.
├── data
│   ├── api_keys.json
│   ├── clean_jokes.txt
│   ├── data.json
│   └── raw_joke_dataset.txt
├── dependencies
│   ├── curl
│   └── jsoncpp
├── include
│   ├── Actions.h
│   ├── CommonFunctions.h
│   └── MultinomialNB.h
├── misc
│   ├── algorithms
│   │   ├── calculating_primes.cc
│   │   └── euclid_algorithm.cc
│   ├── python-scripts
│   │   ├── format.py
│   │   └── naive_bayes_test.py
│   └── sentences.txt
├── spike
│   ├── a.out
│   ├── CommonFunctions.cc
│   ├── CommonFunctions.h
│   ├── scraper.cc
│   ├── weather.cc
│   └── weather.json
├── src
│   ├── Actions.cc
│   ├── CommonFunctions.cc
│   ├── main.cc
│   └── MultinomialNB.cc
├── .gitignore
├── CMakeLists.txt
├── CODE_OF_CONDUCT.md
├── CONTRIBUTING.md
├── LICENSE
├── README.md


```
