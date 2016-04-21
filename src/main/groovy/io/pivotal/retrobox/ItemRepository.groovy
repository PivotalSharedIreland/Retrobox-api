package io.pivotal.retrobox

import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface ItemRepository extends CrudRepository<Item, Long> {

    Item[] findByBoardId(Long boardId)

    @Modifying
    @Query("update Item i set i.likes = i.likes + 1 where i.id = ?1")
    int incrementLikes(Long itemId);

}
