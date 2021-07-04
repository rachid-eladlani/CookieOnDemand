# Created by armand at 23/10/2020
Feature:  User save his CC

  Scenario Outline: A user would save his credit card to not have to do it the next time
    Given "<firstname>", a user logged into his account which will pay an order
    When he provides his credit card no <ccNo> expire on <ccExpMonth>/<ccExpYear> , the CVV <cvv> and the name on the credit card "<ccName>" to pay his order and would save the CC
    Then the payment is proceeded and the credit card is saved

    Examples:
      | firstname | ccNo             | ccExpMonth | ccExpYear | cvv | ccName          |
      | Pierre    | 5132478946280937 | 12         | 27        | 011 | Dupont Pierre   |
      | Marie     | 5132476942280132 | 9          | 22        | 421 | Luart Marie     |
      | Peter     | 5132578746210961 | 11         | 25        | 921 | Jackson Peter   |
      | Jeanne    | 5132148946480237 | 1          | 24        | 443 | Trassart Jeanne |
