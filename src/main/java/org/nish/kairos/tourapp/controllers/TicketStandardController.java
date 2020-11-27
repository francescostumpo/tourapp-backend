package org.nish.kairos.tourapp.controllers;

import org.nish.kairos.tourapp.model.Response;
import org.nish.kairos.tourapp.model.TicketStandard;
import org.nish.kairos.tourapp.model.TicketTipology;
import org.nish.kairos.tourapp.services.TicketStandardService;
import org.nish.kairos.tourapp.utils.TokenValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RestController
@RequestMapping("/api/ts")
public class TicketStandardController {

    private static final Logger logger = LoggerFactory.getLogger(TicketStandardController.class);

    @Inject
    TicketStandardService ticketStandardService;

    @PostMapping("/createOrUpdateTicketStandard")
    public ResponseEntity<Response> createOrUpdateTicketTipology(@RequestHeader("Authorization") String authorization, @RequestBody TicketStandard ticketStandard){

        Response response = new Response();

        if(!TokenValidator.validate(authorization)) return new ResponseEntity<>(TokenValidator.generateUnauthResponse(response), HttpStatus.UNAUTHORIZED);

        try{
            if(ticketStandardService.createOrUpdateTicketStandard(ticketStandard)){
                response.setStatus(200);
                if(ticketStandard.get_id() == null){
                    response.setMessage("TicketStandard correctly created on cloudant DB.");
                }else{
                    response.setMessage("TicketStandard correctly updated on cloudant DB.");
                }
                return new ResponseEntity<>(response, HttpStatus.OK);
            }else{
                response.setStatus(503);
                response.setMessage("The service is unavailable. Please verify logs for more details");
                return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
            }
        }catch (Exception e){
            logger.error("Exception: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(500);
            response.setMessage("An internal error occurred: "+ e.getMessage() + ". Please verify logs for more details");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
