Feature: Remove a recipe for all implanted store

  Background:

  Scenario Outline: a manager who has access to the COD remove a cookie recipe into the COD
    Given a manager from the cookie factory, who has access to the system COD
    And a specific cookie recipe named "<name>", which is define by its type of dough "<dough>", an optional flavour "<flavour>", up to three toppings <topping>, a type of mixture "<mix>", and finally a type of cooking "<cooking>" with a given price <price>
    When the manager remove a specific cookie recipe named "<name>", which is define by its type of dough "<dough>", an optional flavour "<flavour>", up to three toppings <topping>, a type of mixture "<mix>", and finally a type of cooking "<cooking>" with a given price <price>
    Then the cookie recipe has been removed into the COD, in the system so that it's removed also in each store so that it cannot be offered to customers

    Examples:
      | name | dough | flavour | topping | mix | cooking | price |
      | chocolala | cholate | cherry | oreo | mixed | crunchy | 4.99 |
      | dark temptation | vanilla | cinamon | kitkat, ginger | mixed | chewy | 6.99 |
      | crunchy | peanut butter | chili | reeses buttercup, white chocolate | topped | crunchy | 7.99 |