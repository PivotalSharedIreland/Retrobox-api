package io.pivotal.retrobox.item

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import javax.validation.Valid

import static Board.BOARD_ID
import static org.springframework.http.HttpStatus.*
import static org.springframework.web.bind.annotation.RequestMethod.*

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
