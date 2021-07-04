Feature: Order cookie

  Background:
    Given a customer of firstname "John" and of name "Doe" and address "3 Marie Ave, NYC"
    And a cookie named "Chocolala"

  Scenario: add cookies to the shopping cart
    When "John" adds 10 cookies "Chocolala" and 5 cookies "Sooo Chocolate" to the shopping cart
    Then There is 15 cookies in his shopping cart

	Scenario: remove cookies from the shopping cart
    When "John" adds 10 cookies "Chocolala" to the shopping cart
    And "John" removes 6 cookies "Chocolala" from the shopping cart
    Then There is 4 cookies in his shopping cart
    
  Scenario: add cookies to the shopping cart and then remove them
    When "John" adds 10 cookies "Chocolala" to the shopping cart
    And "John" removes 10 cookies "Chocolala" from the shopping cart
    Then There is no cookie "chocolala" in his shopping cart
    
  Scenario: add multiples times cookies to the shopping cart
    When "John" adds 10 cookies "Chocolala" to the shopping cart
    And "John" adds 5 cookies "Chocolala" to the shopping cart
    Then There is 15 cookies in his shopping cart
