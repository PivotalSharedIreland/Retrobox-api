package io.pivotal.retrobox.action

import org.springframework.data.repository.CrudRepository

interface ActionsRepository extends CrudRepository<Action, Long>{

    Action[] findByBoardId(Long boardId)
}
