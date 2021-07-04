# Created by armand at 22/10/2020
Feature: Pay order

  Scenario Outline: a User pay an order
    Given a user of name "<name>", first name "<firstname>", his email is "<email>" and his billing address "<address>",
    And a shopping cart with <qty> cookies "<recipe>", he would pick his order in the store "<store>" on <pickDay>/<pickMonth>/<pickYear> at <pickHour>:<pickMin>
    When the user give the name: "<ccName>" of the credit card, the credit card number: <ccNo> , the date of expiration : <ccExpMonth>/<ccExpYear> and the CVV: <cvv>
    Then if the payment is accepted, the customer is order is confirmed

    Examples:
    | name | firstname | email | address | recipe | qty | store | pickYear | pickMonth | pickDay | pickHour | pickMin | ccNo | ccExpMonth | ccExpYear | cvv | ccName |
    | Dupont | Pierre | pierre.dupont@gmail.com | 863 Manukau Road, NYC | SooChocolalala | 3 | Cookie Factory, 1600 Broadway, NY | 2020 | 10 | 24 | 16 | 00 | 5132478946280937 | 12 | 27 | 011 |  Dupont Pierre |
    | Luart | Marie | marie.11@gmail.com | 32 Marie Av, NYC | Dark Temptation | 1 | Cookie Factory, 12 Kennedy St, NY | 2020 | 10 | 24 | 11 | 00 | 5132476942280132 | 9 | 22 | 421 | Luart Marie |
    | Jackson | Peter | p.jacks@gmail.com | 92 Queen Street Av, NYC | WhiteChoco | 30 | Cookie Factory, 1600 Broadway, NY | 2020 | 10 | 26 | 9 | 00 | 5132578746210961 | 11 | 25 | 921 | Jackson Peter |
    | Trassart | Jeanne | trassardJ@gmail.com | 103 Fifth Av, NYC   | SooChocolalala | 3 | Cookie Factory, 91 Manhattan, NY | 2020 | 10 | 29 | 13 | 00 | 5132148946480237 | 1 | 24 | 443 | Trassart Jeanne |
