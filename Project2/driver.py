# Eduardo Rocha
# Machine Learning Classification Project 2
import pandas as pd
import numpy as np
import random
import math
from copy import deepcopy
import time

current_set_of_features = []
best_set_of_features = []

# separating the search part from the cross validation part
# validation function telling us how good a state will be
# 
def leave_one_out_class_validation(data,current_set_of_features,feature_to_add):
    number_correctly_classified = 0

    copy_data = deepcopy(data)
    copy_check = deepcopy(current_set_of_features)
    copy_check.append(feature_to_add)
    print(copy_check)
    num_of_features = len(data.loc[0])-1

    for i in range(len(copy_data)):
        for k in range(1,num_of_features):
            if k not in copy_check:
                copy_data.iloc[i,k] = 0
    
    # iterate through all the rows
    for i in range(len(copy_data)):
        # grab the data we need from each row object
        object_to_classify = copy_data.loc[i]
        label_object_to_classify = object_to_classify.loc[0]
        #print("Looping over i, at the "+str(i+1)+" location.")
        #print("The "+str(i+1)+"th object is in class "+str(label_object_to_classify))
        
        nearest_neighbor_distance = math.inf
        nearest_neighbor_location = math.inf

        for k in range(len(copy_data)):
            #print("k ="+str(k)+" i = "+str(i))
            if k != i:
                #print("Ask if "+str(i+1)+" is nearest neighbor with "+str(k+1))
                x = object_to_classify.iloc[1:] #getting current row or object 
                y = copy_data.iloc[k,1:] #getting the others from the data set 
                distance = np.sqrt(np.sum([(a-b)*(a-b) for a, b in zip(x, y)]))

                if distance < nearest_neighbor_distance:
                    nearest_neighbor_distance = distance
                    nearest_neighbor_location = k
                    nearest_neighbor_label = copy_data.loc[nearest_neighbor_location,0]

        if label_object_to_classify == nearest_neighbor_label:
            number_correctly_classified = number_correctly_classified+1

    accuracy = number_correctly_classified/len(copy_data)
    #print("Accuracy = "+str(accuracy))
    return accuracy

# this will search our adjacency matrix for the most valuable features
def feature_search_demo(data,flag=False):

    best_so_far_accuracy = 0
    num_of_features = len(data.loc[0])-1
    
    #print("Num of features = "+str(num_of_features))

    # for each row in the data of features
    for i in range(len(data)) :
        print(" ------------------------------- ")
        print("I am on the "+ str(i+1) +"th level of the search tree\n") 
        level = data.loc[i]
        #print(level)
        feature_to_add_at_this_level = None
        current_accuracy = 0


        #for each feature in that level
        for j in range(1,len(level)):
            if j not in current_set_of_features:
                #print("-- Considering adding the "+ str(j)+" feature")
                feature = level[j]
                #print(feature)
                accuracy = leave_one_out_class_validation(data,current_set_of_features,j)
                #setting up the print set
                print_set = deepcopy(current_set_of_features)
                print_set.append(j)
                print('Using feature(s)'+ str(print_set)+" accuracy is:"+str(accuracy))


                if flag == True and accuracy > current_accuracy:
                     feature_to_add_at_this_level = j
                     current_accuracy = accuracy
                    #check every feature at that level for the best feature
                if accuracy > best_so_far_accuracy :
                    best_so_far_accuracy = accuracy
                    feature_to_add_at_this_level = j
                    best_set_of_features.append(j)

        if feature_to_add_at_this_level is not None:
            current_set_of_features.append(feature_to_add_at_this_level)
            #best_set_of_features.append(feature_to_add_at_this_level)
            print("On level "+str(i+1)+" I added feature "+str(feature_to_add_at_this_level)+" to current set")
        else:
            print('\nStopping the search because we didnt find better accuracy here.')
            break
    
    print("Finished searching..")
    print("Best Accuracy = "+ str(best_so_far_accuracy)+ " with the features "+str(current_set_of_features))

def welcome():
    print("Welcome to Eduardo Rocha's Feature Selection Algorithm")
    print("Type in the name of the file to test : ")
    text_file = str(input())
    data = pd.read_csv(text_file,header=None, delimiter = "  ",engine='python')
    print("Data Frame coming in:\n")
    print(data)
    num_of_features = len(data.loc[0])-1
    print("This dataset has "+str(num_of_features)+" features (not including the class attribute), with "+str(len(data))+" instances.")
    return data

def user_choice(data):

    print("Type the number of the algorithm you want to run.")
    print("1) Forward Selection \n2) Backward Elimination")
    choice = int(input())
    if choice == 1:
        print("Forward Selection chosen")
        # starting time
        start = time.time()
        feature_search_demo(data,False)
        end = time.time()
        print("Time to find best features: "+str(start-end))
    elif choice == 2:
        print("Backward Elimination chosen")
    else:
        print("Error in input! Please select 1 or 2")
            
def main():
    

    print(" ------------------------------- ")
    data = welcome()
    user_choice(data)
    #feature_search_demo(data)

if __name__ == "__main__":
    main()