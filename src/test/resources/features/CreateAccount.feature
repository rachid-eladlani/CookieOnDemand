Feature: Account creation

  Background:
    Given a customer of first name "John", name "Doe", mail "johnDoe@gmail.com" and address "3 Marie Ave, NYC"

  Scenario: Account creation
    When "John" create an account
    Then Account informations : first name : "John", name : "Doe", mail : "johnDoe@gmail.com"
    And "John" can join Loyalty Program

  Scenario: Join Loyalty Program
    When "John" have an account
    Then "John" join Loyalty Program
