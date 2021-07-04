Feature: Stock Supply

  Background:
    Given a store located at "6 bd NYC."
    And an employee who has access to the stock of the store which is empty

  Scenario: an employee receives a new delivery of ingredient
    When the employee add 100 "vanilla" flavour and 50 "MILKCHOCOLATE" topping that he received
    Then the stock balance is now 100 of "vanilla" flavour and 50 of "MILKCHOCOLATE" topping

  Scenario: an employee receives a new delivery with missing ingredient
    When the employee wants to add 100 "vanilla" flavour, he realises that 20 percent of those ingredient are missing
    Then the employee declare that they are missing 20 of "vanilla" flavour so they are lost

