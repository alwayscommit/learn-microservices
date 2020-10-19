package com.learn.microservices.web.controller;

import com.learn.microservices.services.BeerService;
import com.learn.microservices.web.model.BeerDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {

    private final BeerService beerService;

    public BeerController(BeerService beerService) {
        this.beerService = beerService;
    }

    @GetMapping({"/{beerId}"})
    public ResponseEntity<BeerDto> getBeer(@NotNull @PathVariable("beerId") UUID beerId){
        return new ResponseEntity<>(beerService.getBeerById(beerId), HttpStatus.OK);
    }

    @PostMapping
    // POST - create new beer
    public ResponseEntity handlePost(@Valid @NotNull @RequestBody BeerDto beerDto){

        BeerDto savedDto = beerService.createBeer(beerDto);

        HttpHeaders headers = new HttpHeaders();
        //todo add hostname to url
        headers.add("Location", "/api/v1/beer/" + savedDto.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    //idempotent
    @PutMapping({"/{beerId}"})
    public ResponseEntity updateBeer(@Valid @PathVariable("beerId") UUID beerId, @RequestBody BeerDto beerDto){

        beerService.updateBeer(beerId, beerDto);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping({"/{beerId}"})
    @ResponseStatus(HttpStatus.NO_CONTENT) //204, accepted, processed, no content to return
    public void deleteBeer(@PathVariable("beerId") UUID beerId){
        beerService.deleteById(beerId);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List> handleError(ConstraintViolationException e){
        List<String> errorList = new ArrayList<String>(e.getConstraintViolations().size());

        e.getConstraintViolations().forEach(violation -> {
            errorList.add(violation.getPropertyPath() + " : " + violation.getMessage());
        });

        return new ResponseEntity<>(errorList, HttpStatus.BAD_REQUEST);
    }

}
