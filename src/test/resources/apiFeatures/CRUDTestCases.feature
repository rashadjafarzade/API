Feature: CRUD operations with Dummy Rest API

@GetEmployee @ApiRegression
Scenario: Get Employees list from Dummy Rest API
  Given User gets Employee List from Dummy Rest API
  Then  Validate if status code is 200

  @GetEmployeeID   @ApiRegression
  Scenario: Get a single Employee data from Dummy Rest API
    Given User gets Employee List from Dummy Rest API
    Then User retrieve a single Employee Data from Dummy Rest API

    @CreateUser @ApiRegression
    Scenario Outline: End to End testing
      Given User creates request data with "<name>" , "<salary>" , "<age>"
      When User submits POST request to Dummy Rest API
      And User validates if statusCode is 201
      Then User retrieves userID from response
      And User deletes data with userID
      Examples:
        |name     |salary   		   |age   |
        |test     |123                 |23    |

