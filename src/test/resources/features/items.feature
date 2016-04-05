Feature: Retro Box API manage boar items

  Scenario: Create a new item
    Given app has started
    When user sends a new "HAPPY" post with content "message"
    Then status code 201 returned

  Scenario: Find board items
    Given app has started
    When item for the default board are requested
    Then status code 200 returned
    And items are
      | property                  | value                   |
      | items[0].message          | I'm a message           |
      | items[0].status           | ACTIVE                  |
      | items[0].type             | HAPPY                   |
      | items[0].creationDate     | 2016-01-01T20:30:00Z    |
      | items[0].lastModifiedDate | 2016-01-01T20:30:00Z    |
      | items[0].likes            | 0                       |
      | items[0].board_id         | 1                       |
      | items[2].message          | I'm a different message |
      | items[2].status           | ACTIVE                  |
      | items[2].type             | SAD                     |
      | items[2].creationDate     | 2016-01-01T21:32:00Z    |
      | items[2].lastModifiedDate | 2016-01-01T21:32:00Z    |
      | items[2].likes            | 1                       |
      | items[2].board_id         | 1                       |
      | items[3].message          | I'm a different message |
      | items[3].status           | ARCHIVED                |
      | items[3].type             | MEDIOCRE                |
      | items[3].creationDate     | 2016-01-01T21:32:00Z    |
      | items[3].lastModifiedDate | 2016-01-01T21:32:00Z    |
      | items[3].likes            | 1                       |
      | items[3].board_id         | 1                       |
