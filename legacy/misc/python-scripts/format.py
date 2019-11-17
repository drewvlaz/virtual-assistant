# This program will format sentences for training a naive bayes classifier
import json

# Number of sentences this session
# repeat = int(input("# of sentences: "))
print("Press 0 to terminate")

# Append to file for easy copy-paste
f = open("sentences.txt", "a")

sentence = ""

def write_to_json(sentence):
    list = sentence.lower().split(" ")
    f.write("{")
    for word in list:
        if(word != list[len(list) - 1]):
            f.write("\"" + word + "\", ")
        else:
            f.write("\"" + word + "\"")
    f.write("},\n")
    



while(1):
    sentence = input("Sentence: ")
    if(sentence == "0"):
        break
    list = sentence.lower().split(" ")
    # json.dumps outputs list with double quotes instead of single
    # f.write(json.dumps(list) + ",\n")
    write_to_json(sentence)

f.close()