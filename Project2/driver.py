# Eduardo Rocha
# Machine Learning Classification Project 2
import pandas as pd
import numpy as np
import random
import math


current_set_of_features = []

# separating the search part from the cross validation part
# validation function telling us how good a state will be
# 
def leave_one_out_class_validation(data,current_set_of_features,feature_to_add):
    number_correctly_classified = 0

    # iterate through all the rows
    for i in range(len(data)):
        # grab the data we need from each row object
        object_to_classify = data.loc[i]
        label_object_to_classify = object_to_classify.loc[0]
        print("Looping over i, at the "+str(i+1)+" location.")
        print("The "+str(i+1)+"th object is in class "+str(label_object_to_classify))
        
        nearest_neighbor_distance = math.inf
        nearest_neighbor_location = math.inf

        for k in range(len(data)):
            print("k ="+str(k)+" i = "+str(i))
            if k != i:
                print("Ask if "+str(i+1)+" is nearest neighbor with "+str(k+1))
                x = object_to_classify.iloc[1:] #getting current row or object 
                y = data.iloc[k,1:] #getting the others from the data set 
                distance = np.sqrt(np.sum([(a-b)*(a-b) for a, b in zip(x, y)]))

                if distance < nearest_neighbor_distance:
                    nearest_neighbor_distance = distance
                    nearest_neighbor_location = k
                    nearest_neighbor_label = data.loc[nearest_neighbor_location,0]
        if label_object_to_classify == nearest_neighbor_label:
            number_correctly_classified = number_correctly_classified+1

    accuracy = number_correctly_classified/len(data)
    print("Accuracy = "+str(accuracy))
    return accuracy

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
                print("-- Considering adding the "+ str(j)+" feature")
                feature = level[j]
                print(feature)
                accuracy = leave_one_out_class_validation(data,current_set_of_features,j)

                #check every feature at that level for the best feature
                if accuracy > best_so_far_accuracy :
                    best_so_far_accuracy = accuracy
                    feature_to_add_at_this_level = j
                    print("feature value = "+str(feature))

        current_set_of_features.append(feature_to_add_at_this_level)
        print("On level "+str(i+1)+" I added feature "+str(feature_to_add_at_this_level)+" to current set")


def main():
    data = pd.read_csv('sample_text.txt',header=None, delimiter = "  ")
    print("Data Frame coming in:\n")
    print(data)
    print(" ------------------------------- ")
    #feature_search_demo(data)
    leave_one_out_class_validation(data,current_set_of_features,3)

if __name__ == "__main__":
    main()