Feature: points

  Scenario: k points closest to origin
    Given the following points:
      | x | y |
      | 3 | 3 |
      | 5 | -1 |
      | -2 | 4 |
      | 40 | 5 |
    Then the closest 2 points are:
      | x | y |
      | 3 | 3 |
      | -2 | 4 |

#  Scenario: k points closest to origin 2
#    Given the following points B:
#      | (3, 3) |
#      | (5, -1) |
#      | -(2, 4) |
#      | (40, 5) |
#    Then the closest 2 points are B:
#      | (3, 3) |
#      | (-2, 4) |