Feature: get spartan DB info

  @wip @db
  Scenario: get all spartan
    When user executes query to get all spartan
    Then user should see 266 results