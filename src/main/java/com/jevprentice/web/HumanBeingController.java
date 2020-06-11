package com.jevprentice.web;

import com.jevprentice.model.HumanBeing;
import com.jevprentice.service.HumanBeingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Rest controller to crud a human being.
 */
@RestController
@RequestMapping("/api/human")
@Slf4j
@Validated
public class HumanBeingController {

    @Autowired
    private HumanBeingService humanBeingService;

    /**
     * Find a human being by ID.
     *
     * @param id The human being ID.
     * @return The response entity.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findHumanById(@PathVariable final long id) {
        log.info("api/human/{}", id);
        final HumanBeing t = humanBeingService.findById(id);
        if (t == null)
            return new ResponseEntity<>(new Error(String.format("Human with id %s does not exist.", id)), HttpStatus.OK);
        return new ResponseEntity<>(t, HttpStatus.OK);
    }

    /**
     * Save a human being.
     *
     * @param t The human being.
     * @return The response entity.
     */
    @PostMapping("human/save")
    public ResponseEntity<?> mergeHuman(@RequestBody final HumanBeing t) {
        log.info("api/human/save: {}", t == null ? "null" : t.toString());
        final HumanBeing result = t.getId() == 0
                ? humanBeingService.insert(t.getName(), t.getBirthday())
                : humanBeingService.upsert(t.getId(), t.getName(), t.getBirthday());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
