@regression @upload-pet-image-by-id-endpoint-tests
Feature: Upload pet image by ID

  @happy-path
  Scenario: Verify pet image can be uploaded using pet id
    Given a new pet is added to the store with id:3799
    Then I send a request to upload a pet image using id:3799
    Then verify response code is 200
    Then verify response body is JSON with tuple(s)
      | $.code    | regex \\d+                                                                |
      | $.type    | regex .+                                                                  |
      | $.message | additionalMetadata: sample\nFile uploaded to ./pet-image.jpg, 24590 bytes |

  @happy-path
  Scenario: Verify pet image can be uploaded using pet id using only mandatory data
    Given a new pet is added to the store with id:4799
    Then I send a request with mandatory data to upload a pet image using id:4799
    Then verify response code is 200
    Then verify response body is JSON with tuple(s)
      | $.code    | regex \\d+                                                              |
      | $.type    | regex .+                                                                |
      | $.message | additionalMetadata: null\nFile uploaded to ./pet-image.jpg, 24590 bytes |

  @unhappy-path
  Scenario: Verify pet image cannot be uploaded when file path is empty
    Given a new pet is added to the store with id:37988
    Then I send an empty request to upload a pet image using id:37988
    Then verify response code is 400

  @unhappy-path
  Scenario: Verify pet image cannot be uploaded when pet id does not exists
    Then I send a request to upload a pet image using id:77778
    Then verify response code is 404

  @unhappy-path
  Scenario: Verify pet image cannot be uploaded when pet id is invalid datatype
    Then I send a request to upload a pet image using id:junk
    Then verify response code is 404