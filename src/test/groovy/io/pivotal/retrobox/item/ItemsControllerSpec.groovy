package io.pivotal.retrobox.item

import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.CoreMatchers
import org.hamcrest.Matchers
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static io.pivotal.retrobox.item.Board.BOARD_ID
import static io.pivotal.retrobox.item.ItemStatus.ACTIVE
import static io.pivotal.retrobox.item.ItemStatus.ARCHIVED
import static io.pivotal.retrobox.item.ItemType.*
import static java.time.ZoneOffset.UTC
import static java.time.ZonedDateTime.now
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class ItemsControllerSpec extends Specification {

    MockMvc mockMvc
    ItemsController itemsController

    void setup() {
        itemsController = new ItemsController(itemService: Mock(ItemService))
        mockMvc = MockMvcBuilders.standaloneSetup(itemsController).build()
    }

    def "return a list of items"() {
        when:
        def response = mockMvc.perform(get("/items?boardId=1").accept(APPLICATION_JSON))

        then:
        1 * itemsController.itemService.findItemsByBoardId(1) >> {
            [
                    new Item(id: 83838389, boardId: BOARD_ID, type: HAPPY, message: 'I\'m a message', status: ACTIVE, creationDate: now(UTC), lastModifiedDate: now(UTC), likes: 0),
                    new Item(id: 83838388, boardId: BOARD_ID, type: HAPPY, message: 'I\'m another message', status: ACTIVE, creationDate: now(UTC), lastModifiedDate: now(UTC), likes: 3),
                    new Item(id: 83838387, boardId: BOARD_ID, type: UNHAPPY, message: 'I\'m a different message', status: ACTIVE, creationDate: now(UTC), lastModifiedDate: now(UTC), likes: 1),
            ]
        }
        response.andExpect(status().isOk())
                .andExpect(jsonPath('$', Matchers.hasSize(3)))
                .andExpect(jsonPath('$.[0].board_id', CoreMatchers.is(1)))
                .andExpect(jsonPath('$.[0].message', CoreMatchers.is("I'm a message")))
                .andExpect(jsonPath('$.[0].status', CoreMatchers.is("ACTIVE")))
                .andExpect(jsonPath('$.[0].type', CoreMatchers.is("HAPPY")))
                .andExpect(jsonPath('$.[0].likes', CoreMatchers.is(0)))
                .andExpect(jsonPath('$.[0].creation_date', CoreMatchers.notNullValue()))
                .andExpect(jsonPath('$.[0].last_modified_date', CoreMatchers.notNullValue()))

    }

    def "save new item"() {
        given:
        def item = new Item(type: type, message: message)

        when:
        def response = mockMvc.perform(post("/items").contentType(APPLICATION_JSON).content(new ObjectMapper().writeValueAsBytes(item)))

        then:
        1 * itemsController.itemService.addItem(_ as Item) >> { Item i ->
            assert i == item

            return new Item()
        }
        response.andExpect(status().isCreated())

        where:
        type     | message
        HAPPY    | 'happy text'
        MEDIOCRE | 'mediocre text'
        UNHAPPY  | 'sad text'
    }

    def "return bad request if item to be saved is wrong"() {
        when:
        def response = mockMvc.perform(post("/items").contentType(APPLICATION_JSON).content(new ObjectMapper().writeValueAsBytes(item)))

        then:
        response.andExpect(status().isBadRequest())

        where:
        item                                         | _
        new Item(boardId: BOARD_ID, type: HAPPY)     | _
        new Item(boardId: BOARD_ID, message: 'text') | _
    }

    def "update an existing item"() {
        given:
        def item = new Item(id: 1, type: HAPPY, message: "Test", status: ARCHIVED)

        when:
        def response = mockMvc.perform(put("/items/${item.id}").contentType(APPLICATION_JSON).content(new ObjectMapper().writeValueAsBytes(item)))

        then:
        1 * itemsController.itemService.updateItem(_ as Item) >> { Item i ->
            assert i == item
        }
        response.andExpect(status().isOk())
    }

    def "return bad request if the id in the URL does not match the item id"() {
        given:
        def item = new Item(id: 1, type: HAPPY, message: "Test", status: ARCHIVED)

        when:
        def response = mockMvc.perform(put("/items/2").contentType(APPLICATION_JSON).content(new ObjectMapper().writeValueAsBytes(item)))

        then:
        0 * itemsController.itemService._
        response.andExpect(status().isBadRequest())
    }

    def "return not found if the item does not exist"() {
        given:
        def item = new Item(id: 1, type: HAPPY, message: "Test", status: ARCHIVED)

        when:
        def response = mockMvc.perform(put("/items/${item.id}").contentType(APPLICATION_JSON).content(new ObjectMapper().writeValueAsBytes(item)))

        then:
        1 * itemsController.itemService.updateItem(_ as Item) >> {
            throw new ItemNotFoundException('Item does not exist')
        }
        response.andExpect(status().isNotFound())
    }

    def "like an existing item"() {
        when:
        def response = mockMvc.perform(post("/items/1/like").contentType(APPLICATION_JSON))

        then:
        1 * itemsController.itemService.incrementLikes(1) >> {
            1
        }
        response.andExpect(status().isOk())
    }

    def "delete an existing item"() {
        when:
        def response = mockMvc.perform(delete("/items/1").contentType(APPLICATION_JSON))

        then:
        1 * itemsController.itemService.deleteItem(1)
        response.andExpect(status().isNoContent())
    }
}
