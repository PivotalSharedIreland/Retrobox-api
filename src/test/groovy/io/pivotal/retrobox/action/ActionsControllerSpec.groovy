package io.pivotal.retrobox.action

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class ActionsControllerSpec extends Specification {

    MockMvc mockMvc
    ActionsController actionsController

    void setup() {
        actionsController = new ActionsController(actionsService: Mock(ActionsService))
        mockMvc = MockMvcBuilders.standaloneSetup(actionsController).build()
    }

    def "create new action"() {
        given: 'an action'
        Action newAction = new Action(description: 'My description', owner: 'someGuy')

        when: 'action is posted to controller'
        def response = mockMvc.perform(post("/actions").contentType(APPLICATION_JSON).content(new ObjectMapper().writeValueAsBytes(newAction)))

        then: 'service is called to save the action'
        response.andExpect(status().isCreated())
    }

}
