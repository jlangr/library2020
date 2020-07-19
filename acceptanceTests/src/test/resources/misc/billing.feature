Feature: billing

  Scenario: average monthly billing given full year history
    Given the customer has the following monthly charges:
      | 100.00 |
      | 120.00 |
      | 100.00 |
      |  80.00 |
      |  80.00 |
      |  90.00 |
      |  95.00 |
      | 105.00 |
      |  90.00 |
      |  90.00 |
      | 100.00 |
      | 111.00 |
    Then the average monthly billing will be 96.75
