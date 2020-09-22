Feature:
  calculate

Scenario: add numbers
  Given entry of the number 8
  When add is pressed with a value 9
  Then the calculator has the value 16
