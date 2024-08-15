# CBA-codility-test-task-3


## Local executions

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

## CICD executions

Github Actions gets triggered when ever a new code is pushed to branches: main, feature/SandeepKurella-CBATest or pull-request is raised/changed/re-opened on main branch

Also you can start the test execution manually by following below steps,
* Go to the "Actions" tab of the GitHub repository.
* Select the Workflow: "Regression tests"
* Run Workflow: Click the "Run workflow" button.
* For "Use workflow from" field, select branch: feature/SandeepKurella-CBATest
* For "Cucumber tags to execute *" field, provide the tag of scenario(s) you want to execute
* Click "Run workflow"
* You will see a new item: "Regression tests" appear

For each execution, there will be an item listed with either commit message or as "Regression tests"
* Click on the item you wish to view the execution report
* Scroll all the way down
* You will see a link: "html-report"
* Clicking on that link downloads a zip file
* Unzip the file to view the cucumber html report of that specific execution item 

<span style="color:red">
Note: External contributors generally cannot trigger workflows unless they are collaborators
</span>