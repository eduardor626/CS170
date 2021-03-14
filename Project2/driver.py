# Eduardo Rocha
# Machine Learning Classification Project 2
import pandas as pd
import random


current_set_of_features = []

# separating the search part from the cross validation part
def leave_one_out_class_validation(data,current_set_of_features,feature_to_add):
    # This is a stub only for testing
    value = random.randint(0, 10)
    return value

# this will search our adjacency matrix for the most valuable features
def feature_search_demo(data):
    # for each row in the data of features
    for i in range(len(data)) :
        print(" ------------------------------- ")
        print("I am on the "+ str(i+1) +"th level of the search tree\n") 
        level = data.loc[i]
        print(level)
        feature_to_add_at_this_level = []
        best_so_far_accuracy = 0

        #for each feature in that level
        for j in range(1,len(level)):
            if j not in current_set_of_features:
                print("Considering adding the "+ str(j)+" feature")
                feature = level[j]
                print(feature)
                accuracy = leave_one_out_class_validation(data,current_set_of_features,j+1)

                #check every feature at that level for the best feature
                if accuracy > best_so_far_accuracy :
                    best_so_far_accuracy = accuracy
                    feature_to_add_at_this_level = j
                    print("feature value = "+str(feature))

        current_set_of_features.append(feature_to_add_at_this_level)
        print("On level "+str(i+1)+" I added feature "+str(feature_to_add_at_this_level)+" to current set")

def main():
    data = pd.read_csv('sample_text.txt',header=None, delimiter = " ")
    print("Data Frame coming in:\n")
    print(data)
    print(" ------------------------------- ")
    feature_search_demo(data)

if __name__ == "__main__":
    main()