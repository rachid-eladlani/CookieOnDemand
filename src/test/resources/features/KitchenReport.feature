Feature: Kitchen report

  Scenario Outline: an employee report a dysfunction of the kitchen
    Given a store located at <address>, named <name>, has <tax>
    And this store has 5 orders to prepare
    When the kitchen is out of service
    Then the store cannot get any new order and the orders to prepare are redirect to other nearest stores

    Examples:
      | address | name | tax |
      | "124 avenue BDA" | "Pierre On Cookie" | 0.1 |
      | "123 avenue USA" | "Marie On Demand" | 0.2  |
      | "123 avenue CBA" | "Peter's Cookie" | 0.3 |
      | "123 avenue CBD" | "Jeanne'ey des cookies" | 0.1 |
