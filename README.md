# wiki-table-parser
Simple Wikipedia table parser
## Introduction
This is a Spring Boot based Java application to generate chart from a Wiki URL given as input. Spring Boot is used keeping in mind it's cloud friendly and the application can be extended to accept requests and send responses via Http calls.

* The thought process for the solution was mainly based on the requirements provided and then making necessary assumptions to visualize a solution
* The application is designed keeping in mind that requirements might evolve and the application can be extended to support that. Eg: Change to the Line chart style, introducing new chart types

### Design thought process

## Assumptions
* Reasonable assumption was made to decide which column in the table wil be forming the X axis and which will be  Y axis.
* It's assumed it's required to extract Wiki pages with only one table. 
* Some table information contains additional details, so necessary data formatting required before generating the chart. Generifying this formatting is not required for this application at this stage. Eg: The height in the sample Wiki page has data both in meter & inches.

## Requirements
* JRE 1.8 or above needed to execute the program.
* Maven or an IDE with Maven support required to execute the Test cases

## Execute the program
* For ease of checking the program execution, a ZIP version of the runtime is attached to the GitHub. It's not ideal to upload a Zip file to Git, but htis is just for the purpose of sharing the runtime easily.
* Extract the Zip file WikiTableParser.zip
* In Windows PC, Double click the WikiTableParser.bat
* For Mac/ Linux environments, execute the below command from a terminal
  * Go to the directory extracted 
  * java -jar wiki-table-parser-0.0.1-SNAPSHOT.jar
  * Sample test configurations are provided in the data directory and the steps and expected results are provided in the TestSteps.txt file.
  
 ### Help executing the application
  * The settings are passed via property file which is placed under config\application.properties path.
    * url:The full URL of the Wiki page
    * wikiTableTag: The Html tag used to identify the table in the Wiki page
    * xDataPatternString.regexp: The regex pattern which will help extract proper X axis values for the chart
    * xDataColumnNumber: The column of the table for X data. 0 indicates first column and so on.
    * xDataGroupPosition: Use this according to the regex pattern used. Which group from regex pattern gives the required X data.
    * yDataColumnNumber: The column of the table for Y data. 0 indicates first column and so on.
    * yDataPatternString.regexp: The regex pattern which will help extract proper Y axis values for the chart
    * yDataGroupPosition: Use this according to the regex pattern used. Which group from regex pattern gives the required Y data.
  
## Maven Commands
* Number of unit test cases are written to cover happy path & edge cases.
* Compile: mvn compile
* Test: mvn test
* Packaging: mvn package
  * The Jar file is saved under target directory
## Limitations/ Further enhancements
While this is just an intereting small application, there are possibilities to improve this further.
* Enhance further for the program to find the data required for the chart by itslef, without requiring the regex, colummn positions to be configured.
* Extract Table headings and use them as graph axis titles.
* Using proper logger libraries

### Author
Sudarsan Gopal 
