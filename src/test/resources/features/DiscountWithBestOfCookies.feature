Feature: Reduce price when a customer buy a best of cookie local or national
  Scenario Outline: a customer will order some cookies of the Best cookie of the month of his favorite store and get a discount
    Given a customer with firstname "<firstname>" and a name "<name>" and address "<address>", his mail is "<email>"
    And a shopping cart filled with <qtyBestOfStore> cookies "<recipeBestOfStore>"
    And the favorite store of the customer which has a BestCookieOfTheMonth: "<recipeBestOfStore>"
    When he validate his order to pay it
    Then he got a reduction because he has ordered cookies of the month

    Examples:
      | name | firstname | email | address | recipeBestOfStore | qtyBestOfStore |
      | Dupont | Pierre | pierre.dupont@gmail.com | 863 Manukau Road, NYC | BlackChocolate | 5 |


  Scenario Outline: a customer will order some cookies of the National Best cookie of the month
    Given a customer with firstname "<firstname>" and a name "<name>" and address "<address>", his mail is "<email>"
    And a shopping cart filled with <qtyBestofNational> cookies "<recipeBestOfNational>"
    And the current national cookie of the month is: "<recipeBestOfNational>"
    When he validate his order to pay it
    Then he got a reduction because he has ordered cookies of the month


    Examples:
      | name | firstname | email | address | recipeBestOfNational | qtyBestofNational |
      | Dupont | Pierre | pierre.dupont@gmail.com | 863 Manukau Road, NYC | BlackChocolate | 5 |
