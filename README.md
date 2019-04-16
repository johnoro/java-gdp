# Introduction

The top 100 economies in the world are provided in the JSON file. We will be reviewing some of this data and logging when certain data is accessed.

# Instructions

- [x] Set up an H2 data base to hold the data for countries and their Gross Domestic Projects. Use the provided JSON file to upload your data. The data contains two fields
* The Country Name
* The GDP in millions

- [x] Set up a Rabbit Message Queue to hold logging information
* Different end points will send messages to the queue
* Set up a class to consume the logs - this will represent another server that manages logging

## Recommended Steps
1. Create a new project
2. Add Jackson Dependencies
3. Build Queue and Exchange
4. Populate the rest of the Application class
5. Write the Message Listener (Consumer)
6. Write Data class
7. Write Exception class
8. Write Repository interface
9. Write Controller class!

## Expose the following end points

### GET
- [x] /names - return using the JSON format all of the countries alphabetized by name

- [x] /economy - return using the JSON format all of the countries sorted from most to least GDP

- [x] /total - return the sum of all GDPs using the JSON format with country name being returned as Total

- [x] /gdp/{country name} - return using the JSON format the record for that country. Must be spelled as in the data!  
- [x] Log that someone looked up this country

### POST

- [x] /gdp - loads the data from the provided JSON file


