Feature:
  format names

@ignore
Scenario: Format a duonym
  When the name "Luna Buna" is formatted
  Then the system returns "Buna, Luna"
