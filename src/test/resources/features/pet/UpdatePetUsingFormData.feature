@regression @update-pet-using-form-data-endpoint-tests
Feature: Update pet using form data

  @happy-path
  Scenario: Verify pet data can be updated using form data
    Given a new pet is added to the store with id:2344001
    Then I send a form data request to update a pet with id:2344001
    Then verify response code is 200
    Then I send a request to find a pet using id:2344001
    Then verify response code is 200
    Then verify response body is JSON with tuple(s)
      | $.id            | 2344001                                   |
      | $.category.id   | 0                                         |
      | $.category.name | herding                                   |
      | $.name          | tommy                                     |
      | $.photoUrls[0]  | https://petphotos.com.au/my-pet-pic1.jpeg |
      | $.tags[0].id    | 0                                         |
      | $.tags[0].name  | pink                                      |
      | $.status        | pending                                   |

  @happy-path
  Scenario: Verify pet data can be updated using form data using only mandatory data
    Given a new pet is added to the store with id:2344002
    Then I send a request with mandatory form data request to update a pet with id:2344002
    Then verify response code is 200
    Then I send a request to find a pet using id:2344002
    Then verify response code is 200
    Then verify response body is JSON with tuple(s)
      | $.id            | 2344002                                   |
      | $.category.id   | 0                                         |
      | $.category.name | herding                                   |
      | $.name          | lucky                                     |
      | $.photoUrls[0]  | https://petphotos.com.au/my-pet-pic1.jpeg |
      | $.tags[0].id    | 0                                         |
      | $.tags[0].name  | pink                                      |
      | $.status        | available                                 |

  @unhappy-path
  Scenario: Verify pet data cannot be updated using form data when pet id does not exists
    Given I send a form data request to update a pet with id:77778
    Then verify response code is 404

  @unhappy-path
  Scenario: Verify pet data cannot be updated using form data when pet id is of invalid data type
    Given I send a form data request to update a pet with id:junk
    Then verify response code is 404

#Design flaw: based on add pet endpoint documentation, allowed values for a pet "status" are available,pending,sold
# however in this endpoint pet "status" field is mentioned as a string i.e., it can take any value