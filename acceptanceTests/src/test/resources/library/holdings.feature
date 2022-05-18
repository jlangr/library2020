Feature: holdings
  As a librarian,
  I want to track holdings
   
Background:
  Given a library system with a branch named "East"
  And a local classification service with:
    | source id | classification | format |
    | 123 | QA-111 | BOOK |
    | 999 | EF-333 | BOOK |

Scenario: Add holdings to the library system generates incremented barcodes
  * a librarian adds a book holding with source id 123 at branch "East"
  * a librarian adds a book holding with source id 123 at branch "East"
  * the "East" branch contains the following holdings:
    | barcode |
    | QA-111:1 |
    | QA-111:2 |
