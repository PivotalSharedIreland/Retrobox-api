package io.pivotal.retrobox.action

import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.CoreMatchers
import org.hamcrest.Matchers
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class ActionsControllerSpec extends Specification {

    MockMvc mockMvc
    ActionsController actionsController

    void setup() {
        actionsController = new ActionsController(actionsService: Mock(ActionsService))
        mockMvc = MockMvcBuilders.standaloneSetup(actionsController).build()
    }

    def "return a list of actions of a given board"() {
        when:
        def response = mockMvc.perform(get("/actions?boardId=1").accept(APPLICATION_JSON))

        then:
        1 * actionsController.actionsService.findActionsByBoardId(1) >> {
            [
                    new Action(id: 1, boardId: 1L, description: "desc 1", owner: "guy1"),
                    new Action(id: 2, boardId: 1L, description: "desc 2", owner: "guy2"),
                    new Action(id: 3, boardId: 1L, description: "desc 3", owner: "guy3")
            ]
        }

        response.andExpect(status().isOk())
                .andExpect(jsonPath('$', Matchers.hasSize(3)))
                .andExpect(jsonPath('$.[0].boardId', CoreMatchers.is(1)))
                .andExpect(jsonPath('$.[0].description', CoreMatchers.is("desc 1")))
                .andExpect(jsonPath('$.[0].owner', CoreMatchers.is("guy1")))
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
