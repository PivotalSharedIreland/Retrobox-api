package io.pivotal.retrobox.acceptance.steps.actions

import cucumber.api.DataTable
import cucumber.api.groovy.EN
import cucumber.api.groovy.Hooks
import io.pivotal.retrobox.acceptance.steps.common.StoryContext
import io.pivotal.retrobox.action.Action

import static com.jayway.restassured.http.ContentType.JSON
import static io.pivotal.retrobox.acceptance.common.BaseApiClient.givenApiClient
import static io.pivotal.retrobox.acceptance.steps.common.StoryContext.putInContext

this.metaClass.mixin(Hooks)
this.metaClass.mixin(EN)

When(~'^User creates a new action with$') { DataTable actions ->

    actions.asMaps(String, String).each { row ->
        def description = row.description ?: null
        def owner = row.owner ?: null

        def action = new Action(description: description, owner: owner) //TODO don't use application model objects
        def response = givenApiClient().contentType(JSON).body(action).post("/actions")
        putInContext(StoryContext.Fields.RESPONSE,response) //Overrides RESPONSE on iteration (not suitable for several actions created)
    }

}

