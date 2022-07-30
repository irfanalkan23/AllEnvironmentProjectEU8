Feature: BookIT application UI and DB end to end testing
  Scenario: UI and DB user verification
    Given User logs into UI app with "sbirdbj@fc2.com" and "asenorval"
    When User navigates to mySelf page and gets user info
    And User sends a query to bookIT DB with "sbirdbj@fc2.com"
    Then UI and DB information should match

  # HW : Finish the rest of the steps and do scenario outline
    # ucharlot7y@nbcnews.com  username
    # archibaldmelloy  password

  @wip  @db
    Scenario: DB vs API verification
    Given User sends a query to bookIT DB with "sbirdbj@fc2.com"
    And User logs into BookIT API using "sbirdbj@fc2.com" and "asenorval"
    And User gets related API information
    Then API and DB information should match
