Feature: Cookie On Demande

  Background:
    Given a cookie with the name of "test"
    And a topping named "chochoc" with a quantity 3

  Scenario: Add topping
    When a customer want to add the toppingAdd to the cookie
    Then the topping is added

  Scenario: Add more than 3 toppings
    When a customer want to add 2 toppings in a cookie with already 2 topping
    Then only one topping is added

  Scenario: Delete topping
    When delete an existing topping from a cookie
    Then the topping is deleted

  Scenario: Delete non existing topping
    When delete non existing topping
    Then cookie is not created

  Scenario: Change quantity of topping
    When a customer want to change quantity of an Ingredient
    Then the quantity of the topping is created

  Scenario: Change quantity of non existing topping
    When a customer want to change quantity of an non existing Ingredient
    Then the cookie is not created