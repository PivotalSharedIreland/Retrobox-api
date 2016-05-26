Feature: Retro Box API manage actions

  Scenario: Create a new action
    Given app has started
    When User creates a new action with
      | description | owner    |
      | New Action  | my-owner |
    Then status code 201 returned

  Scenario: Create an invalid action
    Given app has started
    When User creates a new action with
      | description | owner |
      | New Action  |       |
    Then status code 400 returned
