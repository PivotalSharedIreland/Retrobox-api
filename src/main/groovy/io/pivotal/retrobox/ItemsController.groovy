package io.pivotal.retrobox

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

import javax.validation.Valid

import static io.pivotal.retrobox.Board.BOARD_ID
import static org.springframework.web.bind.annotation.RequestMethod.GET
import static org.springframework.web.bind.annotation.RequestMethod.POST

@RestController
class ItemsController {

    @Autowired
    ItemService itemService

    @RequestMapping(value = "/board/{board_id}", method = GET)
    @ResponseBody
    Board findBoardItems(@PathVariable(value = "board_id") String boardId) {
        new Board(items: itemService.findItems(BOARD_ID))
    }

    @RequestMapping(value = "/items", method = POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    Item addItem(@RequestBody @Valid Item item) {
        itemService.addItem(item)
    }

}
