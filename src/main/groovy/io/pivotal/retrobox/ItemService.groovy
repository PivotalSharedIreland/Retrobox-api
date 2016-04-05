package io.pivotal.retrobox

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ItemService {

    @Autowired
    ItemRepository itemRepository

    Item[] findItems(Long boardId){
        itemRepository.findByBoardId(boardId)
    }

}
