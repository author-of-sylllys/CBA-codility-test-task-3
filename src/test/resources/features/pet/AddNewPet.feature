@regression @add-new-pet-endpoint-tests
Feature: Add a new pet to the store

  @happy-path
  Scenario: Verify a new pet can be added using valid data
    Given I send a request to add a new pet
    Then verify response code is 200
    Then verify response body is JSON with tuple(s)
      | $.id            | regex \\d+ |
      | $.category.id   | 0          |
      | $.category.name | herding    |
      | $.name          | lucky      |
      | $.photoUrls     | []         |
      | $.tags[0].id    | 0          |
      | $.tags[0].name  | pink       |
      | $.status        | available  |

  @unhappy-path @only
  Scenario: Verify a new pet can be added using only mandatory data
    Given I send a request to add a new pet, with following details
      | $.id:null       |
      | $.category:null |
      | $.name:guru     |
      | $.photoUrls:[]  |
      | $.tags:null     |
      | $.status:null   |
    Then verify response code is 200
    Then verify response body is JSON with tuple(s)
      | $.id            | regex \\d+      |
      | $.category.id   | does not exists |
      | $.category.name | does not exists |
      | $.name          | guru            |
      | $.photoUrls     | []              |
      | $.tags[0].id    | does not exists |
      | $.tags[0].name  | does not exists |
      | $.status        | does not exists |