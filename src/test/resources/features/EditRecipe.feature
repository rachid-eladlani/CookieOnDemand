Feature: Edit a recipe for all implanted store

  Background:

  Scenario Outline: a manager who has access to the COD change a cookie recipe into the COD
    Given a manager from the cookie factory, who has access to the system CookieOnDemand
    When the manager edit a specific cookie recipe named "<name>", which is defined by its type of dough "<dough>", an optional flavour "<flavour>", up to three toppings <topping>, a type of mixture "<mix>", and finally a type of cooking "<cooking>" with a given price <price>
    Then the cookie recipe with name "<name>" has been edited to "Cookiesss", the type of dough will be "papate", the flavour will be "choco", up to three toppings "topoo", the type of mixture is change by "xim", and finally the type of cooking is changed by "feu doux" with a given price 3

    Examples:
      | name | dough | flavour | topping | mix | cooking | price |
      | chocolala | chocolate | cherry | oreo | mixed | crunchy | 4.99 |
      | dark temptation | vanilla | cinamon | kitkat, ginger | mixed | chewy | 6.99 |
      | crunchy | peanut butter | chili | reeses buttercup, white chocolate | topped | crunchy | 7.99 |