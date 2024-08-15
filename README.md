# CBA-codility-test-task-3

To run all the tests from your local machine, run the following command from the project's home directory
```
./mvnw clean test
```
use below command to run any specific tests using their tags,
```
./mvnw clean test -Dcucumber.filter.tags="@rest_assured-setup-test"
```

After each execution, to view the test execution report, open the file at below location
```
target/cucumber-reports/Cucumber.html
```