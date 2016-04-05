package io.pivotal.retrobox

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ItemsController {

    @Autowired
    ItemService itemService

    @RequestMapping(value = "/board/{board_id}/items")
    @ResponseBody
    Item[] findBoardItems(@PathVariable(value = "board_id") String boardId) {
        itemService.findItems(1)
    }

}
