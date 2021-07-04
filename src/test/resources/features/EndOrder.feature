
Feature: End Order

  Background:
    Given the command is ready to be picked up in a Store

  Scenario: order already prepared and waiting for customer
    When the customer came to collect his order with the id "12500"
    Then the order can't be reused and archived



