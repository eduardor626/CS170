# Eduardo Rocha
# Machine Learning Classification Project 2
import pandas as pd
import random


# separating the search part from the cross validation part
def leave_one_out_class_validation(data,current_set,feature_to_add):
    # This is a stub only for testing
    value = random.randint(0, 10)
    return value

def feature_search_demo(data):
    print("in search")
    for row in data:
        print(row+"\n")
        #print("On the " +str(index)+"th level of the search tree\n")

def main():
    data = pd.read_csv('sample_text.txt',header=None, delimiter = " ")
    print(data)
    #feature_search_demo(data)


if __name__ == "__main__":
    main()