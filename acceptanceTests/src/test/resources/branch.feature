Feature: Branch

Background:
   Given a clean library system

Scenario: Add a branch
   Given librarian adds a branch named "East"
   And they add a branch named "West"
   When user requests a list of all branches
   Then the system returns the following branches:
      | East |
      | West |
      
 # TODO
 Scenario: Attempt to add branch with duplicate name