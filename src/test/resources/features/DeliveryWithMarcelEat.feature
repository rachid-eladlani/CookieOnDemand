Feature:  Delivery with Marcel Eat

  Background:
    Given a customer of firstname "Chris", of name "Mac" and address "3 Marie Ave, NYC"
    And a store named "CookiePlace" at "630 Old Country Rd., NYC" and with 11.3 percent taxes

  Scenario: Customer get delivered By MarcelEat with simple delivery option
    When "Chris" a customer add 9 cookie "Chocolala" and 5 cookie "Sooo Chocolate" on the shopping cart and chose simple delivery with MarcelEat
    Then MarcelEat confirms his order and send him a confirmation email

  Scenario: Customer get delivered By MarcelEat with last delivery option
    When "Chris" a customer add 9 cookie "Chocolala" and 5 cookie "Sooo Chocolate" on the shopping cart and chose last minute delivery with MarcelEat
    Then MarcelEat confirms his order and send him a confirmation email and the extra costs represent 50% of the total price order