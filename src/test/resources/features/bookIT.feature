Feature: BookIT application UI and DB end to end testing
  @wip  @db
  Scenario: UI and DB user verification
    Given User logs into UI app with "email" and "password"
    When User navigates to mySelf page and gets user info
    And User sends a query to bookIT DB with "email"
    Then UI and DB information should match
