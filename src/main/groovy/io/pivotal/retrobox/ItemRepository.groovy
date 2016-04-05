package io.pivotal.retrobox

import org.springframework.data.repository.CrudRepository

interface ItemRepository extends CrudRepository<Item, Long> {

    Item[] findByBoardId(Long boardId)

}
