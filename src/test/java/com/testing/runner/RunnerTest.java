package com.testing.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = { 
        "src/test/resources/features/student/LoginStudent.feature",
        "src/test/resources/features/student/MyGrades.feature",
        "src/test/resources/features/student/Performance.feature", 
        "src/test/resources/features/admin/LoginAdmin.feature",
        "src/test/resources/features/admin/ManageUserForm.feature",
        "src/test/resources/features/admin/ManageUserEdit.feature",
        "src/test/resources/features/admin/ManageUserEditPW.feature"
    }, 
    glue = {
        "com.testing.definition",
        "com.testing.hook"
    },
    plugin = { 
        "pretty", 
        "html:target/cucumber-reports.html", 
        "json:target/cucumber.json",
        // "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" 
    }
)
public class RunnerTest extends AbstractTestNGCucumberTests {
}
