## CoderHack
# Problem Statement
Develop a RESTful API service using Spring Boot to manage the Leaderboard for a Coding Platform while using MongoDB to persist the data.
# Problem Description
While coding platforms usually host multiple contests while maintaining numerous leaderboards, this assignment requires you to design a service for managing the leaderboard of a specific contest. Assume the platform has only one contest with a single leaderboard. The platform also gives virtual awards to the users called Badges based on their score.

## Requirements
The API must handle CRUD operations for competing user registrations
Each user has the following fields: 
User ID (Unique Identifier)
Username
Score (0 <= Score <= 100)
Badges (Code Ninja, Code Champ, Code Master)
User registration requests must have a User ID and Username
The score must be 0, and the badges must be empty initially after the registration
Updation through PUT requests is only allowed for Score
Badges must be awarded based on the score:
1 <= Score <= 30 -> Code Ninja
30 <= Score <= 60 -> Code Champ
60 <= Score <= 100 -> Code Master
A user can only have a maximum of three unique badges
{Code Ninja, Code Champ, Code Master} -> Vali
{Code Ninja} -> Valid
{Code Ninja, Code Champ, Code Master, Code Ninja} -> Invalid
User retrieval must be sorted based on the score
Sorting should have the time complexity of O(nlogn)
Include basic JUnit test cases to verify the operations


We will follow object modelling pattern for this crud project

Rough Idea:
One class have must : id, name
initial score is 0 and badge is empty

# Entity Class 
User-> id,name ,email,score,Badge
# Controller class
contain all the api

# Service class 
contain buisness logic

# Repository class
aceess the information from db.

 POST create user
 PUT update the score
 GET get detail according to desc order
 GET get detail by id 
 DELETE delete the user.

# here i have attached postman link
[Postman Collection ](https://winter-flare-851632.postman.co/workspace/kajal~ed0bd169-879d-4182-a30e-8d7e212ef617/request/15511430-9fd1d636-4a70-4911-a658-a4180ea70268)





