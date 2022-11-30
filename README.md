# Weather API Team Project

A weather info application which gives the current weather and
forecast data about any given city in `json` format.

## Meet the Team
#### Julia Ruzu  
Kood: 201361IADB  
Uni-ID: juruzu 
#### Marion Claudia Striž  
Kood: 206260IADB  
Uni-ID: mastri

## Tech Stack
Source code is written in Java 17.   
Gradle is used for package management and building - 
all utilized packages are included in `build.gradle` file under dependencies.

The app uses [OpenWeatherMap API](https://openweathermap.org/api) and
requires and internet connection.

## Instructions

To successfully do anything with this application, this repository
needs to be cloned to your local machine.

Using a command line tool, navigate to the app source catalog and run
the relevant commands shown below.

### Run Tests

```shell
# Windows:
gradlew test

# Unix:
./gradlew test
```

### Build the Application

Building the app creates the compiled class files (in `build/classes`), as well as packaging the
application and creating a jar file (in `build/libs`).

Tests are also run as a part of the build.

```shell
# Windows:
gradlew build

# Unix:
./gradlew build
```

### Run the Application

#### Using Gradle
The app can be run using Gradle, in which case running the `build` command
before-hand is not necessary - build is performed anyway when this command is run.

Application output is shown after the `Task :run` line.

```shell
# Windows:
gradlew run --args={your city here}

# Unix:
./gradlew run --args={your city here}

# Example:
# ./gradlew run --args=tallinn
```

#### Using .jar Packaged Application
After running the Gradle [build](#build-the-application)  command, the app can also be run using
the generated `jar` file. This file is placed by default in the `build/libs` folder,
but can be safely moved wherever is convenient.
```shell
# This command works when in app root folder and jar 
# has not been moved - replace path as required

# Windows:
java -jar build\libs\weather-api-project.jar {your-city-here}

# Unix:
java -jar build/libs/weather-api-project.jar {your-city-here}

# Example:
# java -jar build\libs\weather-api-project.jar tallinn
```


