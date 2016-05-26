package io.pivotal.retrobox.acceptance.steps.items

import cucumber.api.DataTable
import cucumber.api.groovy.EN
import cucumber.api.groovy.Hooks
import io.pivotal.retrobox.item.Board
import org.hamcrest.CoreMatchers

import static com.jayway.restassured.http.ContentType.JSON
import static io.pivotal.retrobox.acceptance.common.BaseApiClient.givenApiClient
import static io.pivotal.retrobox.acceptance.steps.common.StoryContext.Fields.RESPONSE
import static io.pivotal.retrobox.acceptance.steps.common.StoryContext.getFromContext
import static io.pivotal.retrobox.acceptance.steps.common.StoryContext.putInContext
import static org.junit.Assert.assertThat

this.metaClass.mixin(Hooks)
this.metaClass.mixin(EN)

When(~'^item for the default board are requested$') { ->
    loadBoard()
}

private void loadBoard() {
    def response = givenApiClient().contentType(JSON).get('/board/1')
    putInContext(RESPONSE, response)
}

When(~'^user sends a new "([^"]*)" post with content "([^"]*)"$') { String type, String message ->
    def response = givenApiClient().contentType(JSON).body(['type': type, 'message': message]).post('/items')
    putInContext(RESPONSE, response)
}

Then(~'^items are$') { DataTable items ->
    def response = getFromContext(RESPONSE).as(Board)
    def responseItems = response.items

    items.asMaps(String, String).each { row ->
        def result = Eval.me('items', responseItems, "${row.property}").toString()
        def fieldValue = row.value ?: null

        assertThat("${row.field}", result, CoreMatchers.is(fieldValue))
    }
}

When(~/^user updates item with id "([^"]*)" and new status "([^"]*)"$/) { String itemId, String status ->
    loadBoard()
    def board = getFromContext(RESPONSE).as(Map)
    def item = board.items.find{ it.id.toString() == itemId}
    item.status = status

    def response = givenApiClient().contentType(JSON).body(item).put("/items/${itemId}" )
    putInContext(RESPONSE, response)
}

When(~/^a user likes the item with id "([^"]*)"$/) { String itemId ->
    def response = givenApiClient().contentType(JSON).post("/items/${itemId}/like" )
    putInContext(RESPONSE, response)
}

When(~/^a user deletes the item with id "([^"]*)"$/) { String itemId ->
    def response = givenApiClient().contentType(JSON).delete("/items/${itemId}" )
    putInContext(RESPONSE, response)
}
