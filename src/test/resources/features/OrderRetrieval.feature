Feature: Set the order ready for the customer

  Scenario Outline: set the order ready, in order to notify the customer to retrieve his order
    Given a user of name "<name>", first name "<firstname>", his email is "<email>" and his billing address "<address>"
    And a shopping cart with <qty> cookies "<recipe>", he would pick his order in the store "<store>" on <pickDay>/<pickMonth>/<pickYear> <pickHour>:<pickMin>
    When the order is ready to be retrieved
    Then user retrieve it on the store


  Examples:
    | name | firstname | email | address | recipe | qty | store | pickYear | pickMonth | pickDay | pickHour | pickMin |
    | Dupont | Pierre | pierre.dupont@gmail.com | 863 Manukau Road, NYC | SooChocolalala | 3 | Cookie Factory, 1600 Broadway, NY | 2020 | 10 | 24 | 16 | 00 |
    | Luart | Marie | marie.11@gmail.com | 32 Marie Av, NYC | Dark Temptation | 1 | Cookie Factory, 12 Kennedy St, NY | 2020 | 10 | 24 | 11 | 00 |
    | Jackson | Peter | p.jacks@gmail.com | 92 Queen Street Av, NYC | WhiteChoco | 30 | Cookie Factory, 1600 Broadway, NY | 2020 | 10 | 26 | 9 | 00 |
    | Trassart | Jeanne | trassardJ@gmail.com | 103 Fifth Av, NYC   | SooChocolalala | 3 | Cookie Factory, 91 Manhattan, NY | 2020 | 10 | 29 | 13 | 00 |
