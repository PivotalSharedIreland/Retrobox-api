package io.pivotal.retrobox

import spock.lang.Specification

import static io.pivotal.retrobox.Board.BOARD_ID
import static io.pivotal.retrobox.ItemStatus.ACTIVE
import static io.pivotal.retrobox.ItemType.HAPPY
import static io.pivotal.retrobox.ItemType.SAD
import static java.time.ZonedDateTime.now
import static java.time.ZoneOffset.UTC

class ItemServiceSpec extends Specification {

    ItemService itemService

    void setup() {
        itemService = new ItemService(itemRepository: Mock(ItemRepository))
    }

    def "return a list of items"() {
        when:
        def items = itemService.findItems(1)

        then:
        1 * itemService.itemRepository.findByBoardId(1) >> {
            [
                    new Item(id: 83838389, boardId: BOARD_ID, type: HAPPY, message: 'I\'m a message', status: ACTIVE, creationDate: now(UTC), lastModifiedDate: now(UTC), likes: 0),
                    new Item(id: 83838388, boardId: 1, type: HAPPY, message: 'I\'m another message', status: ACTIVE, creationDate: now(UTC), lastModifiedDate: now(UTC), likes: 3),
                    new Item(id: 83838387, boardId: 1, type: SAD, message: 'I\'m a different message', status: ACTIVE, creationDate: now(UTC), lastModifiedDate: now(UTC), likes: 1),
            ]
        }

        items.size() == 3
        items[0].boardId == BOARD_ID
        items[0].message == "I'm a message"
        items[0].status == ACTIVE
        items[0].type == HAPPY
        items[0].likes == 0
    }

    def "save new item"() {
        given:
        def item = new Item(message: 'text', type: HAPPY, boardId: BOARD_ID)
        when:
        def newItem = itemService.addItem(item)

        then:
        1 * itemService.itemRepository.save(_ as Item) >> { Item i ->
            assert i.boardId == BOARD_ID
            assert i.message == item.message
            assert i.type == HAPPY
            assert i.status == ACTIVE
            assert i.likes == 0
            assert i.creationDate != null
            assert i.lastModifiedDate == i.creationDate

            new Item(boardId: i.boardId, message: i.message, type: HAPPY, status: ACTIVE)
        }
        newItem != null
    }
}
