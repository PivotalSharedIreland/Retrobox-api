package io.pivotal.retrobox

import spock.lang.Specification

import static io.pivotal.retrobox.ItemStatus.ACTIVE
import static io.pivotal.retrobox.ItemType.HAPPY
import static io.pivotal.retrobox.ItemType.SAD
import static java.time.ZonedDateTime.now
import static java.time.ZoneOffset.UTC

class ItemServiceSpec extends Specification {

    def "return a list of items"() {
        given:
        ItemService itemService = new ItemService(itemRepository: Mock(ItemRepository))

        when:
        def items = itemService.findItems(1)

        then:
        1 * itemService.itemRepository.findByBoardId(1) >> {
            [
                    new Item(id: 83838389, boardId: 1, type: HAPPY, message: 'I\'m a message', status: ACTIVE, creationDate: now(UTC), lastModifiedDate: now(UTC), likes: 0),
                    new Item(id: 83838388, boardId: 1, type: HAPPY, message: 'I\'m another message', status: ACTIVE, creationDate: now(UTC), lastModifiedDate: now(UTC), likes: 3),
                    new Item(id: 83838387, boardId: 1, type: SAD, message: 'I\'m a different message', status: ACTIVE, creationDate: now(UTC), lastModifiedDate: now(UTC), likes: 1),
            ]
        }

        items.size() == 3
        items[0].boardId == 1
        items[0].message == "I'm a message"
        items[0].status == ACTIVE
        items[0].type == HAPPY
        items[0].likes == 0
    }
}
