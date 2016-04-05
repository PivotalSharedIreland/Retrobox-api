package io.pivotal.retrobox.acceptance.steps.items

import io.pivotal.retrobox.acceptance.steps.common.StoryContext
import cucumber.api.DataTable
import cucumber.api.groovy.EN
import cucumber.api.groovy.Hooks
import org.hamcrest.CoreMatchers

import static io.pivotal.retrobox.acceptance.common.BaseApiClient.givenApiClient
import static io.pivotal.retrobox.acceptance.steps.common.StoryContext.Fields.RESPONSE
import static com.jayway.restassured.http.ContentType.JSON
import static org.junit.Assert.assertThat

this.metaClass.mixin(Hooks)
this.metaClass.mixin(EN)

When(~'^item for the default board are requested$') { ->
    def response = givenApiClient().contentType(JSON).get('/board/1/items')
    StoryContext.putInContext(RESPONSE, response)
}

Then(~'^items are$') { DataTable items ->
    def response = StoryContext.getFromContext(RESPONSE).as(List)

    items.asMaps(String,String).each { row ->
        def result = Eval.me('items', response, "${row.property}").toString()
        def fieldValue = row.value ?: null

        assertThat("${row.field}", result, CoreMatchers.is(fieldValue))
    }
}

