Feature: scan station

  Background:

    @ignore
  Scenario: Scanner identifies itself
    Given librarian adds a branch named "East"
    When the branch barcode for "East" is scanned
    Then the scanner identifies itself as belonging to the branch "East"