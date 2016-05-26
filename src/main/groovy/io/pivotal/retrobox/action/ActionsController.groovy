package io.pivotal.retrobox.action

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

import javax.validation.Valid

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.web.bind.annotation.RequestMethod.POST

@RestController
@RequestMapping(value = '/actions')
class ActionsController {

    @Autowired
    ActionsService actionsService

    @RequestMapping(method = POST)
    @ResponseStatus(CREATED)
    public Action saveAction(@RequestBody @Valid Action action) {
       actionsService.save(action)
    }
}
