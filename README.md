# Weather API Team Project

## Team members
- Nimi: Julia Ruzu  
Kood: 201361IADB  
Uni-ID: juruzu
- Nimi: Marion Claudia Striž  
Kood: 206260IADB  
Uni-ID: mastri

## Tech Stack
Source code in Java 17. 
Gradle for package management and building.

## Running the Application

To run the application, insert the cities you want weather info about in the command shown below.
If more than one, separate with space and surround with quotation marks.
```shell
# Windows:
gradlew run --args={your cities here}

# Unix:
./gradlew run --args={your cities here}

# Examples:
# ./gradlew run --args=tallinn
# ./gradlew run --args="tallinn berlin"
```