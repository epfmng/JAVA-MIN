# JAVAMIN
The following files are about a studying Java project

On the main page, the numbers of clients, vehicles and rents are showed.

About the clients :
It is possible :
- to create a client
- to delete a client
- to update a client

The details about a client are available if there is a reservation linked to a client, but the data aren't visible

There is no constraints on clients whether the fact that a client cannot be deleted if a reservation made by this client exists

When a client is created, it's id is the last id which exists plus one.
An id client will not be used again after its client is deleted, if the database is not run again.

About the vehicles :
It is possible :
- to create a vehicle
- to delete a vehicle
- to update a vehicle

The details about a vehicle are available if there is a reservation linked to a vehicle, but the data aren't visible

There is no constraints on vehicles whether the fact that a vehicle cannot be deleted if a reservation made by this client exists

When a vehicle is created, it's id is the last id which exists plus one.
An id vehicle will not be used again after its vehicle is deleted, if the database is not run again.

About the rents :
It is possible :
- to create a rent
- to delete a rent

Tests :
For the client, tests are available for the basics functions (delete, update, create, findAll, count)

Difficulties encountered :
- The software issues, a lot of time during the classes where spent to resolve these issues
- Because I never coded with Java before, I did not have the basics knowledges and had to spent a lot of time understanding every part of the code to code by myself (asking a lot of questions to other students really help me)