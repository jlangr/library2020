Feature:
  calculate

Scenario: add numbers
  Given entry of the number 7
  When add is pressed with a value 12
  Then the calculator has the value 19

Scenario: square numbers
  Given entry of the number 7
  When square is pressed
  Then the calculator has the value 49

