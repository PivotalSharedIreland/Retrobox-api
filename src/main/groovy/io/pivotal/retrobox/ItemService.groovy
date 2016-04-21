package io.pivotal.retrobox

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.transaction.Transactional
import java.time.ZonedDateTime

import static io.pivotal.retrobox.ItemStatus.ACTIVE
import static java.time.ZoneOffset.UTC

@Component
class ItemService {

    @Autowired
    ItemRepository itemRepository

    Item[] findItemsByBoardId(Long boardId) {
        itemRepository.findByBoardId(boardId)
    }

    Item addItem(Item item) {
        def now = ZonedDateTime.now(UTC)
        itemRepository.save(new Item(boardId: item.boardId, message: item.message, type: item.type, status: ACTIVE, creationDate: now, lastModifiedDate: now))
    }

    Item updateItem(Item item) {
        def existingItem = itemRepository.findOne(item.id)

        if (existingItem == null) {
            throw new ItemNotFoundException("Item not found for id ${item.id}")
        }

        item.creationDate = existingItem.creationDate
        item.lastModifiedDate = ZonedDateTime.now(UTC)

        itemRepository.save(item)
    }

    @Transactional
    int incrementLikes(Long itemId) {
        itemRepository.incrementLikes(itemId)
    }
}
