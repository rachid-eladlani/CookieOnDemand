# Created by armand at 23/10/2020
Feature: loyalty program
  a User which subscribed to the loyaltyProgram get 10% off on is next order when he order at least 30 cookies

  Scenario Outline: a User doesn't have the discount but order more than 30 cookies
    Given a user of name "<name>", first name "<firstname>", his email is "<email>" and his billing address "<address>", which subscribed to the loyaltyProgram
    When the user makes an order with <qty> cookies, which is more or equal to 30, and pay <price> for it
    Then the user got 10% off on his next order and paid <price>

    Examples:
      | name     | firstname | email                   | address                 | qty | price |
      | Dupont   | Pierre    | pierre.dupont@gmail.com | 863 Manukau Road, NYC   | 30   | 60.00   |
      | Luart    | Marie     | marie.11@gmail.com      | 32 Marie Av, NYC        | 35  | 57.50 |
      | Jackson  | Peter     | p.jacks@gmail.com       | 92 Queen Street Av, NYC | 50  | 80.00    |
      | Trassart | Jeanne    | trassardJ@gmail.com     | 103 Fifth Av, NYC       | 42  | 70.00    |


  Scenario Outline: a User make and pay an order and has 10% off thanks to his loyalty program
    Given a user of name "<name>", first name "<firstname>", his email is "<email>" and his billing address "<address>", which subscribed to the loyaltyProgram and has 10% off on his next order
    When the user makes an order for an amount of <price> without discount
    Then the user get 10% off on his order and paid <priceDiscount> instead of <price>

    Examples:
      | name     | firstname | email                   | address                 | qty | price | priceDiscount |
      | Dupont   | Pierre    | pierre.dupont@gmail.com | 863 Manukau Road, NYC   | 30   | 60.00   | 54.00            |
      | Luart    | Marie     | marie.11@gmail.com      | 32 Marie Av, NYC        | 35  | 57.50 | 51.75         |
      | Jackson  | Peter     | p.jacks@gmail.com       | 92 Queen Street Av, NYC | 50  | 80.00    | 72.00            |
      | Trassart | Jeanne    | trassardJ@gmail.com     | 103 Fifth Av, NYC       | 42  | 70.00    | 63.00            |
