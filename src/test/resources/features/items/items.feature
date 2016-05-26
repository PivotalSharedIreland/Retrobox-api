Feature: Retro Box API manage board items

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
      | items[0].id               | 1                       |
      | items[0].message          | I'm a message           |
      | items[0].status           | ACTIVE                  |
      | items[0].type             | HAPPY                   |
      | items[0].creationDate     | 2016-01-01T20:30Z[GMT]  |
      | items[0].lastModifiedDate | 2016-01-01T20:30Z[GMT]  |
      | items[0].likes            | 0                       |
      | items[0].boardId          | 1                       |
      | items[2].id               | 3                       |
      | items[2].message          | I'm a different message |
      | items[2].status           | ACTIVE                  |
      | items[2].type             | UNHAPPY                 |
      | items[2].creationDate     | 2016-01-01T21:32Z[GMT]  |
      | items[2].lastModifiedDate | 2016-01-01T21:32Z[GMT]  |
      | items[2].likes            | 1                       |
      | items[2].boardId          | 1                       |
      | items[3].id               | 4                       |
      | items[3].message          | I'm a different message |
      | items[3].status           | ARCHIVED                |
      | items[3].type             | MEDIOCRE                |
      | items[3].creationDate     | 2016-01-01T21:32Z[GMT]  |
      | items[3].lastModifiedDate | 2016-01-01T21:32Z[GMT]  |
      | items[3].likes            | 1                       |
      | items[3].boardId          | 1                       |

  Scenario: Update an existing board item
    Given app has started
    When user updates item with id "1" and new status "ARCHIVED"
    Then status code 200 returned
    When item for the default board are requested
    Then status code 200 returned
    And items are
      | property                  | value                  |
      | items[0].id               | 1                      |
      | items[0].message          | I'm a message          |
      | items[0].status           | ARCHIVED               |
      | items[0].type             | HAPPY                  |
      | items[0].creationDate     | 2016-01-01T20:30Z[GMT] |
      | items[0].likes            | 0                      |
      | items[0].boardId          | 1                      |

  Scenario: Like an existing board item
    Given app has started
    When a user likes the item with id "3"
    Then status code 200 returned
    When item for the default board are requested
    Then status code 200 returned
    And items are
      | property                  | value                  |
      | items[2].id               | 3                      |
      | items[2].likes            | 2                      |

  Scenario: Delete an existing board item
    Given app has started
    When a user deletes the item with id "2"
    Then status code 204 returned
    When item for the default board are requested
    Then status code 200 returned
    And items are
      | property                  | value                   |
      | items[0].id               | 1                       |
      | items[1].id               | 3                       |
      | items[2].id               | 4                       |
