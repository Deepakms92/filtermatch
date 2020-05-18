# Filter Matches 
This project is to get filter matches based on a specific criteria(s).

## Technologies & frameworks:
* Java 8, Spring Boot, H2, Junit, Mockito, Maven ,Docker

## Running the application:
* git clone https://github.com/Deepakms92/filtermatch.git
* cd filtermatch
* mvn clean install
* The above gives you the target folder.
* Go to target folder and run the jar created by java -jar filtermatch-0.0.1-SNAPSHOT.jar


## Things Done:
* Endpoint to insert all the data from given json.
* Endpoint to get the matches based on the filter criteria(All filters included).
* Swagger enpoint.
* DockerFile to deploy the application on cloud.
* Covered most of the test cases (above 90 % coverage).

## Assumptions:
* The latitude and the longitude would be given or taken by some means from the front end.
* The latitude and longitude value would be proper.

## Improvements
* Better exception handling when the API doesn't return the value.
* Fetching the user name and his location for fetching distance (endpoint for this)
* Helm packaging .
* Better test cases.
* Front end development 


