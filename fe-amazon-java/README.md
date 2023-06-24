# Swapcard QA Test
This is the result of Java section in [Swapcard-Corporation-sdet-automation-test](..%2F..%2FAppData%2FLocal%2FTemp%2FSwapcard-Corporation-sdet-automation-test.url) by Eduardo Nazaré

## Requirements:
- OpenJDK 1.8
- Maven 3.8.1
- Chrome (browser default option)

## About Serenity BDD
Serenity BDD is a Java tool for end-to-end and acceptance tests, using Selenium, RestAssured, JUnit, Cucumber, etc out-of-the-box, allowing user to build test cases quickly in a range of different requirements and system interactions.

## Project directory structure
The Project follows SerenityBDD standard project architecture as following
```Gherkin
src
  + test
    + java                        Test runners and supporting code
      + enums                     All enums used
      + pages                     Page Object Model classes
      + runners                   Runners to catch all test cases
      + stepDefinitions           Step definitions from feature files
      + steps                     All steps that user is able to perform
    + resources
      + features                  Feature files
        + Amazon                  Feature file subdirectory for Amazon
             ProductRating.feature
```

## The Page Object Model implementation
The solution follows POM, mapping all usable web elements and actions of each page in a different page.

Since Amazon menu elements and its interactions are shared between different pages, there is a class to centralize all menu interaction and others _extend_ this menu class.

## How to run
To run tests you must use 
```json
$ mvn clean verify
```
or you would define a browser, like
```json
$ mvn clean verify -Ddriver=firefox
```
but it's needed to adapt ``src/test/resources/serenity.conf`` to instead of defining Chrome capabilities, to define a new browser capabilities.


### Notes:
- Built with Cucumber integration
- All Enum were created to facilitate Cucumber features to use real words and phrases instead of UI keys directly.
- After running the tests a report is generated with screenshots when failing in the path 'target/site/serenity/index.html'. Path is included in the end of execution log.
- Amazon has AB testing on LIVE, making home page be different from expected and failing the tests randomly.