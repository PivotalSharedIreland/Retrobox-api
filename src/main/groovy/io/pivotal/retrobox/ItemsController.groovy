package io.pivotal.retrobox

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

import javax.validation.Valid

import static io.pivotal.retrobox.Board.BOARD_ID
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.web.bind.annotation.RequestMethod.DELETE
import static org.springframework.web.bind.annotation.RequestMethod.GET
import static org.springframework.web.bind.annotation.RequestMethod.POST
import static org.springframework.web.bind.annotation.RequestMethod.PUT

@RestController
class ItemsController {

    @Autowired
    ItemService itemService

    @RequestMapping(value = "/board/{board_id}", method = GET)
    @ResponseBody
    Board findBoardItems(@PathVariable(value = "board_id") String boardId) {
        new Board(items: itemService.findItemsByBoardId(BOARD_ID))
    }

    @RequestMapping(value = "/items", method = POST)
    @ResponseStatus(CREATED)
    @ResponseBody
    Item addItem(@RequestBody @Valid Item item) {
        itemService.addItem(item)
    }

    @RequestMapping(value = "/items/{id}", method = PUT)
    @ResponseStatus(OK)
    @ResponseBody
    ResponseEntity updateItem(@PathVariable() String id, @RequestBody @Valid Item item) {
        if (id != item.id.toString()) {
            return ResponseEntity.badRequest().build();
        }

        itemService.updateItem(item)
        ResponseEntity.ok().build()
    }

    @RequestMapping(value = "/items/{id}/like", method = POST)
    @ResponseStatus(OK)
    void likeItem(@PathVariable() Long id) {
        itemService.incrementLikes(id)
    }

    @RequestMapping(value = "/items/{id}", method = DELETE)
    @ResponseStatus(NO_CONTENT)
    void deleteItem(@PathVariable() Long id) {
        itemService.deleteItem(id)
    }

    @ExceptionHandler(ItemNotFoundException)
    @ResponseStatus(NOT_FOUND)
    void handleItemNotFoundException(){}
}
