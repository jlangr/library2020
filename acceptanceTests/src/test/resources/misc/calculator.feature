Feature:
  calculate stuffs!

Scenario: add numbers
  Given entry of the number 4
  When add is pressed with a value 3
  Then the calculator has the value 7

@ignore
Scenario: square numbers
  Given entry of the number 2
  When square is pressed
  Then the calculator has the value 4

@ignore
Scenario: square numbers again
  Given entry of the number 3
  When square is pressed
  Then the calculator has the value 9
