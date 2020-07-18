Feature: a point

  Scenario: generates a new point by moving in a direction and distance
    Given a point with x of 0 and y of 0
    When moving 10 with an angle of 90 degrees
    Then the new point has an x of 0 and a y of 10

