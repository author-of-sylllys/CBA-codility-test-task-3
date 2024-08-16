@regression @find-pet-by-id-endpoint-tests
Feature: Find pet by ID

  @happy-path
  Scenario: Verify pet details can be retrieved using pet id
    Given a new pet is added to the store with id:612893
    Then I send a request to fina a pet using id:612893
    Then verify response code is 200
    Then verify response body is JSON with tuple(s)
      | $.id            | 612893                                    |
      | $.category.id   | 0                                         |
      | $.category.name | herding                                   |
      | $.name          | lucky                                     |
      | $.photoUrls[0]  | https://petphotos.com.au/my-pet-pic1.jpeg |
      | $.tags[0].id    | 0                                         |
      | $.tags[0].name  | pink                                      |
      | $.status        | available                                 |

#  @unhappy-path
#  Scenario: Verify an error response is returned when a pet cannot be retrieved
#    Given I send a request to add a new pet, with following details
#      | $.id:null                                                |
#    Then verify response body is JSON with tuple(s)
#      | $.code    | 1             |
#      | $.type    | error         |
#      | $.message | Pet not found |