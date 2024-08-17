@regression @update-pet-details-endpoint-tests
Feature: Update an existing pet details in the store

  @happy-path
  Scenario: Verify an existing pet can be updated using valid data
    Given a new pet is added to the store with id:66610
    Then I send a request to update an existing pet, with following details
      | $.id:66610 |
    Then verify response code is 200
    Then verify response body is JSON with tuple(s)
      | $.id            | 66610                                     |
      | $.category.id   | 0                                         |
      | $.category.name | toy                                       |
      | $.name          | luckee                                    |
      | $.photoUrls[0]  | https://petphotos.com.au/my-pet-pic2.jpeg |
      | $.tags[0].id    | 0                                         |
      | $.tags[0].name  | purple                                    |
      | $.status        | sold                                      |

  @happy-path
  Scenario: Verify an existing pet can be updated using only mandatory data
    Given I send a request to update an existing pet, with following details
      | $.id:null                                                |
      | $.category:null                                          |
      | $.name:guru                                              |
      | $.photoUrls[0]:https://petphotos.com.au/my-pet-pic2.jpeg |
      | $.tags:null                                              |
      | $.status:null                                            |
    Then verify response code is 200
    Then verify response body is JSON with tuple(s)
      | $.id            | regex \\d+                                |
      | $.category.id   | does not exists                           |
      | $.category.name | does not exists                           |
      | $.name          | guru                                      |
      | $.photoUrls[0]  | https://petphotos.com.au/my-pet-pic2.jpeg |
      | $.tags[0].id    | does not exists                           |
      | $.tags[0].name  | does not exists                           |
      | $.status        | does not exists                           |

  @happy-path
  Scenario Outline: Verify an existing pet can be updated if field:status is one of the allowed values:available, pending, sold
    Given a new pet is added to the store with id:<petId>
    Then I send a request to update an existing pet, with following details
      | $.id:<petId>      |
      | $.status:<status> |
    Then verify response code is 200
    Then verify response body is JSON with tuple(s)
      | $.id     | <petId>  |
      | $.status | <status> |

    Examples:
      | petId | status    |
      | 66618 | available |
      | 66619 | pending   |
      | 66620 | sold      |

  @unhappy-path
  Scenario: Verify an existing pet can be updated if mandatory field:name is missing
    Given a new pet is added to the store with id:66611
    Then I send a request to update an existing pet, with following details
      | $.id:66611  |
      | $.name:null |
    Then verify response code is 400

  @unhappy-path
  Scenario: Verify an existing pet can be updated if mandatory field:photoUrls is missing
    Given a new pet is added to the store with id:66612
    Then I send a request to update an existing pet, with following details
      | $.id:66612       |
      | $.photoUrls:null |
    Then verify response code is 400

  @unhappy-path
  Scenario: Verify an existing pet can be updated if mandatory field:photoUrls is empty
    Given a new pet is added to the store with id:66613
    Then I send a request to update an existing pet, with following details
      | $.id:66613     |
      | $.photoUrls:[] |
    Then verify response code is 400

  @unhappy-path
  Scenario: Verify an existing pet can be updated if field:photoUrls is not a valid URL
    Given a new pet is added to the store with id:66614
    Then I send a request to update an existing pet, with following details
      | $.id:66614          |
      | $.photoUrls[0]:junk |
    Then verify response code is 400

  @unhappy-path
  Scenario: Verify an existing pet can be updated if field:id is not an integer
    Then I send a request to update an existing pet, with following details
      | $.id:abc |
    Then verify response code is 400

  @unhappy-path
  Scenario: Verify an existing pet can be updated if field:id is greater allowed value of int64
    Then I send a request to update an existing pet, with following details
      | $.id:9223372036854775808 |
    Then verify response code is 400

  @unhappy-path
  Scenario: Verify an existing pet can be updated if field:category.id is not an integer
    Given a new pet is added to the store with id:66615
    Then I send a request to update an existing pet, with following details
      | $.id:66615        |
      | $.category.id:abc |
    Then verify response code is 400

  @unhappy-path
  Scenario: Verify an existing pet can be updated if field:tags[*].id is not an integer
    Given a new pet is added to the store with id:66616
    Then I send a request to update an existing pet, with following details
      | $.id:66616       |
      | $.tags[0].id:abc |
    Then verify response code is 400

  @unhappy-path
  Scenario: Verify an existing pet can be updated if field:status is not one of the allowed values:available, pending, sold
    Given a new pet is added to the store with id:66617
    Then I send a request to update an existing pet, with following details
      | $.id:66617    |
      | $.status:junk |
    Then verify response code is 400
