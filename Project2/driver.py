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
# if backwards_search set to True, we do it differently
def leave_one_out_class_validation(data,current_set_of_features,feature_to_add,backwards_search=False):
    number_correctly_classified = 0
    copy_data = deepcopy(data)
    copy_check = deepcopy(current_set_of_features)
    
    if backwards_search is not False:
        copy_check.remove(feature_to_add)
    else:
        copy_check.append(feature_to_add)
    #print(copy_check)

    num_of_features = len(data.loc[0])-1

    # don't check features we are not interested in
    for i in range(len(copy_data)):
        for k in range(1,num_of_features):
            if k not in copy_check:
                copy_data.iloc[i,k] = 0
    
    # iterate through all the rows
    for i in range(len(copy_data)):
        # grab the data we need from each row object
        object_to_classify = copy_data.loc[i]
        label_object_to_classify = object_to_classify.loc[0]
        
        nearest_neighbor_distance = math.inf
        nearest_neighbor_location = math.inf

        for k in range(len(copy_data)):
            if k != i:
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
    return accuracy

def backward_elimination_demo(data,flag=False):

    best_so_far_accuracy = 0
    num_of_features = len(data.loc[0])-1
    backwards_search = True

    # setup all the features initially
    for feature in range(num_of_features):
        current_set_of_features.append(feature+1)

    # for each row in the data of features
    for i in range(len(data)) :
        level = data.loc[i]
        feature_to_delete_at_this_level = None
        current_accuracy = 0

    # for each feature in that level
        for j in range(1,len(level)):
             if j in current_set_of_features:
                accuracy = leave_one_out_class_validation(data,current_set_of_features,j,backwards_search)
                copy_set = deepcopy(current_set_of_features)
                copy_set.remove(j)
                print('Using feature(s)'+ str(copy_set)+" accuracy is:"+str(accuracy))

                if flag == True and accuracy > current_accuracy:
                    feature_to_delete_at_this_level = j
                    current_accuracy = accuracy  
                #check every feature at that level for the best feature
                if accuracy >= best_so_far_accuracy :
                    best_so_far_accuracy = accuracy
                    #because we have better accuracy without this feature
                    feature_to_delete_at_this_level = j

        if feature_to_delete_at_this_level is not None:
            current_set_of_features.remove(feature_to_delete_at_this_level)
            print("On level "+str(len(data.loc[0])-1)+" I deleted "+str(feature_to_delete_at_this_level)+" to current set")
        else:
            print('\nStopping the search because we didnt find better accuracy here.')
            break
    
    print("Finished searching..")
    print("Best Accuracy = "+ str(best_so_far_accuracy)+ " with the features "+str(current_set_of_features))



# this will search our adjacency matrix for the most valuable features
# params: data(data coming in), flag(to go through all the features), backwards(to do backward elimination instead)
def feature_search_demo(data,flag=False):

    best_so_far_accuracy = 0
    num_of_features = len(data.loc[0])-1

    # for each row in the data of features
    for i in range(len(data)) :
        print(" ------------------------------- ")
        print("I am on the "+ str(i+1) +"th level of the search tree\n") 
        level = data.loc[i]
        feature_to_add_at_this_level = None
        current_accuracy = 0

        #for each feature in that level
        for j in range(1,len(level)):
            if j not in current_set_of_features:
                #feature = level[j] (checking specific feature)
                accuracy = leave_one_out_class_validation(data,current_set_of_features,j)
                copy_set = deepcopy(current_set_of_features)
                copy_set.append(j)
                print('Using feature(s)'+ str(copy_set)+" accuracy is:"+str(accuracy))

                if flag == True and accuracy > current_accuracy:
                     feature_to_add_at_this_level = j
                     current_accuracy = accuracy

                    #check every feature at that level for the best feature
                if accuracy >= best_so_far_accuracy :
                    best_so_far_accuracy = accuracy
                    feature_to_add_at_this_level = j
                    

        if feature_to_add_at_this_level is not None:
            current_set_of_features.append(feature_to_add_at_this_level)
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
        print("Time to find best features: "+str(end-start))
    elif choice == 2:
        print("Backward Elimination chosen")
        # starting time
        start = time.time()
        backward_elimination_demo(data,True)
        end = time.time()
        print("Time to find best features: "+str(end-start))

    else:
        print("Error in input! Please select 1 or 2")
            
def main():
    print(" ------------------------------- ")
    data = welcome()
    user_choice(data)

if __name__ == "__main__":
    main()