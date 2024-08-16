@regression @add-new-pet-endpoint-tests
Feature: Add a new pet to the store

  @happy-path
  Scenario: Verify a new pet can be added using valid data
    Given I send a request to add a new pet
    Then verify response code is 200
    Then verify response body is JSON with tuple(s)
      | $.id            | regex \\d+                                |
      | $.category.id   | 0                                         |
      | $.category.name | herding                                   |
      | $.name          | lucky                                     |
      | $.photoUrls[0]  | https://petphotos.com.au/my-pet-pic1.jpeg |
      | $.tags[0].id    | 0                                         |
      | $.tags[0].name  | pink                                      |
      | $.status        | available                                 |

  @happy-path
  Scenario: Verify a new pet can be added using only mandatory data
    Given I send a request to add a new pet, with following details
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

  @unhappy-path
  Scenario: Verify a new pet cannot be added if mandatory field:name is missing
    Given I send a request to add a new pet, with following details
      | $.name:null |
    Then verify response code is 400

  @unhappy-path
  Scenario: Verify a new pet cannot be added if mandatory field:photoUrls is missing
    Given I send a request to add a new pet, with following details
      | $.photoUrls:null |
    Then verify response code is 400

  @unhappy-path
  Scenario: Verify a new pet cannot be added if mandatory field:photoUrls is empty
    Given I send a request to add a new pet, with following details
      | $.photoUrls:[] |
    Then verify response code is 400

  @unhappy-path
  Scenario: Verify a new pet cannot be added if field:photoUrls is not a valid URL
    Given I send a request to add a new pet, with following details
      | $.photoUrls[0]:junk |
    Then verify response code is 400

  @unhappy-path
  Scenario: Verify a new pet cannot be added if field:id is not an integer
    Given I send a request to add a new pet, with following details
      | $.id:abc |
    Then verify response code is 400

  @unhappy-path
  Scenario: Verify a new pet cannot be added if field:id is greater allowed value of int64
    Given I send a request to add a new pet, with following details
      | $.id:9223372036854775808 |
    Then verify response code is 400

  @unhappy-path
  Scenario: Verify a new pet cannot be added if field:category.id is not an integer
    Given I send a request to add a new pet, with following details
      | $.category.id:abc |
    Then verify response code is 400

  @unhappy-path
  Scenario: Verify a new pet cannot be added if field:tags[*].id is not an integer
    Given I send a request to add a new pet, with following details
      | $.tags[0].id:abc |
    Then verify response code is 400

  @unhappy-path
  Scenario: Verify a new pet cannot be added if field:status is not one of the allowed values:available, pending, sold
    Given I send a request to add a new pet, with following details
      | $.status:junk |
    Then verify response code is 400
