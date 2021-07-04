Feature: Check statistics on a store

  Background:
    Given a manager of name "Bob" and with store address "12 Palace Street, Manhattan"
    And the store openingTimes:
      | 10:00 |
      | 10:00 |
      | 10:00 |
      | 10:00 |
      | 10:00 |
    And the store closingTimes:
      | 18:00 |
      | 18:00 |
      | 18:00 |
      | 18:00 |
      | 18:00 |
    And statistics with a range of 2 hours per period in a day
    And has 1 orders on: "2020-10-20 10:45"
    And has 2 orders on:
      | 2020-10-21 13:45 |
      | 2020-10-21 17:20 |


  Scenario: check the statistics for a period of a day
    When "Bob" wants to check the statistics for the period 2020/10/21 13:00
    Then Statistics for the period "2020-10-21 13:00" : "from=12:00, to=14:00, orderQty=1"

  Scenario: check the statistics for a day
    When "Bob" wants to check the statistics for the day 2020/10/20 10:00
    Then Statistics for "2020-10-2O" : "from=10:00, to=12:00, orderQty=1 | from=12:00, to=14:00, orderQty=0 | from=14:00, to=16:00, orderQty=0 | from=16:00, to=18:00, orderQty=0"

  Scenario: check the statistics for a day (borderline case)
    When "Bob" wants to check the statistics for the day 2020/10/20 10:00 (borderline case)
    Then Statistics for orders ordered in 2020-10-20 between 19:00 to 20:00 is 0

