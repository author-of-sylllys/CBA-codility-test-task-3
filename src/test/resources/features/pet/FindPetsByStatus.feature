@regression @find-pets-by-status-endpoint-tests
Feature: Find pets by status

  @happy-path
  Scenario Outline: Verify pet details can be retrieved by their status
    Given I send a request to add a new pet, with following details
      | $.id:<petId>              |
      | $.photoUrls[0]:photo-link |
      | $.status:<petStatus>      |
    Then I send a request to find pets by status:<petStatus>
    Then verify response code is 200
    Then verify response body is JSON with tuple(s)
      | $.[?(@.['id']=='<petId>')].status        | has item <petStatus> |
      | $.[?(@.['id']=='<petId>')].category.id   | has item 0           |
      | $.[?(@.['id']=='<petId>')].category.name | has item herding     |
      | $.[?(@.['id']=='<petId>')].name          | has item lucky       |
      | $.[?(@.['id']=='<petId>')].photoUrls[0]  | has item photo-link  |
      | $.[?(@.['id']=='<petId>')].tags[0].id    | has item 0           |
      | $.[?(@.['id']=='<petId>')].tags[0].name  | has item pink        |

    Examples:
      | petId  | petStatus |
      | 405050 | available |
      | 405051 | pending   |
      | 405052 | sold      |

  @unhappy-path
  Scenario: Verify pet details cannot be retrieved without status parameter
    Then I send a request to find pets without status parameter
    Then verify response code is 400

  @unhappy-path
  Scenario: Verify pet details cannot be retrieved using an invalid status
    Then I send a request to find pets by status:junk
    Then verify response code is 400
    Then verify response body is JSON with tuple(s)
      | $.message | Invalid status value |