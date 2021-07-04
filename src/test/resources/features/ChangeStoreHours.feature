Feature: Change store hours

  Background:
    Given a manager of name "Harry Pak"
    And a store named "CookiePlace" at "630 Old Country Rd., NYC" with 12.3 percent taxes
    And a customer of first name "Patrick" and name "Dozo" and mail "patrick.d@gmail.com" and address "12 Palm Street, NYC"

  Scenario: adding opening hours of a store
    When "Harry" wants to define the opening hours at 8 hours and 0 minutes on "Monday" of his store named "CookiePlace" located at "630 Old Country Rd., NYC" with 12.3 percent taxes
    Then The opening hours of the store named "CookiePlace" located "630 Old Country Rd., NYC" with 12.3 percent taxes is define to 8 hours and 0 minutes on "Monday"

  Scenario: adding closing hours of a store
    When "Harry" wants to define the closing hours at 18 hours and 35 minutes on "Monday" of his store named "CookiePlace" located at "630 Old Country Rd., NYC" with 12.3 percent taxes
    Then The closing hours of the store named "CookiePlace" located "630 Old Country Rd., NYC" with 12.3 percent taxes is define to 18 hours and 35 minutes on "Monday"

  Scenario: changing opening hours of a store
    When "Harry" wants to change opening hours which was 8 hours and 0 minutes on "Monday" and put 10 hours and 0 minutes on a same day, of his store named "CookiePlace" located at "630 Old Country Rd., NYC" with 12.3 percent taxes
    Then The opening hours are now at 10 hours and 0 minutes on "Monday" of his store named "CookiePlace" located at "630 Old Country Rd., NYC" with 12.3 percent taxes

  Scenario: changing closing hours of a store
    When "Harry" wants to change closing hours which was 18 hours and 35 minutes on "Monday" and put 15 hours and 45 minutes on a same day, of his store named "CookiePlace" located at "630 Old Country Rd., NYC" with 12.3 percent taxes
    Then The closing hours are now at 15 hours and 45 minutes on "Monday" of his store named "CookiePlace" located at "630 Old Country Rd., NYC" with 12.3 percent taxes
