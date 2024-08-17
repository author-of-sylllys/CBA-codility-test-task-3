@regression @delete-pet-with-id-endpoint-tests
Feature: Delete pet with ID

  @happy-path
  Scenario: Verify pet details can be deleted using pet id
    Given a new pet is added to the store with id:700001
    Given I send a request to find a pet using id:700001
    Then verify response code is 200
    Then I send a request to delete a pet with id:700001
    Then verify response code is 200
    Given I send a request to find a pet using id:700001
    Then verify response code is 404

  @unhappy-path
  Scenario: Verify an error response is returned when a pet cannot be deleted using provided pet id
    Then I send a request to delete a pet with id:988
    Then verify response code is 404
    Then verify response body is JSON with tuple(s)
      | $.message | Pet not found |

  @unhappy-path @only
  Scenario: Verify an error response is returned when provided pet id is invalid (wrong datatype)
    Then I send a request to delete a pet with id:junk
    Then verify response code is 400
    Then verify response body is JSON with tuple(s)
      | $.message | Invalid ID supplied |