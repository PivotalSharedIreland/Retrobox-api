package io.pivotal.retrobox.acceptance

import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

@RunWith(Cucumber)
@CucumberOptions(
  format = ["pretty", "html:build/reports/cucumber"],
  strict = true,
  features = ["src/test/resources/features/local"],
  glue = ["src/test/groovy/com/mttnow/cloud/services/application/acceptance/steps"],
  tags = ["~@ignore"]
)
class RunCukes {}
