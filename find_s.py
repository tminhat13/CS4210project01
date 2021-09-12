#-------------------------------------------------------------------------
# AUTHOR: Nhat Tran
# FILENAME: find_s.py
# SPECIFICATION: Find a maximally specific hypothesis that fits the data following the strategy of Find-S algorithm
# FOR: CS 4210- Assignment #1
# TIME SPENT: 10 hours
#-----------------------------------------------------------*/

#IMPORTANT NOTE: DO NOT USE ANY ADVANCED PYTHON LIBRARY TO COMPLETE THIS CODE SUCH AS numpy OR pandas. You have to work here only with standard vectors and arrays

#importing some Python libraries
import csv

num_attributes = 4
db = []
print("\n The Given Training Data Set \n")

#reading the data in a csv file
with open('contact_lens.csv', 'r') as csvfile:
  reader = csv.reader(csvfile)
  for i, row in enumerate(reader):
      if i > 0: #skipping the header
         db.append (row)
         print(row)

print("\n The initial value of hypothesis: ")
hypothesis = ['0'] * num_attributes #representing the most specific possible hypothesis
print(hypothesis)

#find the first positive training data in db and assign it to the vector hypothesis

db.sort(key=lambda x: x[4], reverse=True) #sort the positive training data
hypothesis = db[0].copy() #get the first element also the first hypothesis
hypothesis.pop(4)  #remove the last column
print("\n The first positive training data in db")
print(hypothesis)

#find the maximally specific hypothesis according to your training data in db and assign it to the vector hypothesis (special characters allowed: "0" and "?")

for k in range(len(db)):
    if db[k][4]=="Yes":
        for j in range(len(hypothesis)):
            if hypothesis[j]!=db[k][j]:
                hypothesis[j]="?"

print("\n The Maximally Specific Hypothesis for the given training examples found by Find-S algorithm:\n")
print(hypothesis)