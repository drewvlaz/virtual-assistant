weather = [
    ["what", "is", "the", "weather", "like"],
    ["is", "it", "rainy", "outside"],
    ["is", "it", "sunny", "outside"],
    ["what", "is", "the", "weather", "like", "for", "the", "week"],
    ["how", "cloudy", "is", "it"],
    ["should", "i", "stay", "inside", "today"],
    ["when", "will", "it", "stop", "raining"],
    ["weather", "outside", "sunny", "rainy"],
    ["raining", "weather", "hot", "cold", "temperature"],
    ["cloudy", "inside", "today", "tomorrow", "week", "day"],
]

grades = [
    ["what", "are", "my", "grades", "like"],
    ["how", "did", "i", "do", "on", "that", "test"],
    ["what", "is", "my", "english", "grade", "like"],
    ["how", "are", "my", "grades"],
    ["what", "did", "i", "score", "on", "that", "quiz"],
    ["did", "i", "turn", "in", "my", "homework"],
    ["grade", "grades", "score", "scores", "test", "class"],
    ["math", "english", "science", "subject", "subjects"],
]

categories = [weather, grades]

def calculate_vocabulary(categories):
    vocabulary = [[], []]
    for i in range(len(categories)):
        for sentence in categories[i]:
            for word in sentence:
                if word not in vocabulary:
                    vocabulary[i].append(word)

    return vocabulary

def count_word_instances(search, category):
    count = 0
    for sentence in category:
        for word in sentence:
            if word == search:
                count += 1

    return count

def count_num_words(category):
    count = 0
    for sentence in category:
        for word in sentence:
            count += 1

    return count
 


def get_probabilities(category):
    vocabulary = len(calculate_vocabulary(categories)[0]) + len(calculate_vocabulary(categories)[1]) 
    word_count = count_num_words(category)
    probabilities = {}
    for sentence in category:
        for word in sentence:
            instances = count_word_instances(word, category)
            probability = (instances + 1) / (word_count + vocabulary)
            probabilities[word] = probability

    return probabilities


weather_probs = get_probabilities(weather)
grades_probs = get_probabilities(grades)
probs = [weather_probs, grades_probs]

# print(get_probabilities(weather))
# print(get_probabilities(grades))


def calculate_category(sentence):
    category_probs = [1, 1]
    sentence = sentence.lower().split(" ")
    vocabulary = calculate_vocabulary(categories)
    for i in range(len(categories)):
        for word in sentence:
            if word in vocabulary[i]:
                category_probs[i] *= probs[i][word]

            else:
                category_probs[i] *= 1 / (len(vocabulary[0]) + len(vocabulary[1]))

    
    return category_probs


sentence = input("Sentence: " )
results = calculate_category(sentence)

print(results[0]/(results[0] + results[1]) * 100)
print(results[1]/(results[0] + results[1]) * 100)

if(results[0] > results[1]):
    print("Category: weather")
else:
    print("Category: grades")