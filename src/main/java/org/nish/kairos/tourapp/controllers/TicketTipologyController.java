package org.nish.kairos.tourapp.controllers;


import org.nish.kairos.tourapp.model.Response;
import org.nish.kairos.tourapp.model.TicketTipology;
import org.nish.kairos.tourapp.services.TicketTipologyService;
import org.nish.kairos.tourapp.utils.TokenValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/api/tt")
public class TicketTipologyController {

    private static final Logger logger = LoggerFactory.getLogger(TicketTipologyController.class);

    @Inject
    TicketTipologyService ticketTipologyService;

    @PostMapping("/createOrUpdateTicketTipology")
    public ResponseEntity<Response> createOrUpdateTicketTipology(@RequestHeader("Authorization") String authorization, @RequestBody TicketTipology ticketTipology){

        Response response = new Response();

        if(!TokenValidator.validate(authorization)) return new ResponseEntity<>(TokenValidator.generateUnauthResponse(response), HttpStatus.UNAUTHORIZED);

        try{
            if(ticketTipologyService.createOrUpdateTicketTipology(ticketTipology)){
                response.setStatus(200);
                if(ticketTipology.get_id() == null){
                    response.setMessage("TicketTipology correctly created on cloudant DB.");
                }else{
                    response.setMessage("TicketTipology correctly updated on cloudant DB.");
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
            response.setMessage("An internal error occurred: "+ e.getMessage() + ".\nPlease verify logs for more details");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getTicketTipology/{ticketTipologyId}")
    public ResponseEntity<Object> createTourOperator(@RequestHeader("Authorization") String authorization, @PathVariable String ticketTipologyId){

        Response response = new Response();

        if(!TokenValidator.validate(authorization)) return new ResponseEntity<>(TokenValidator.generateUnauthResponse(response), HttpStatus.UNAUTHORIZED);

        try{
            TicketTipology ticketTipology = ticketTipologyService.getTicketTipology(ticketTipologyId);
            if(ticketTipology != null){
                return new ResponseEntity<>(ticketTipology, HttpStatus.OK);
            }
            response.setStatus(503);
            response.setMessage("The service is unavailable. Please verify logs for more details");
            return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }catch (Exception e){
            logger.error("Exception: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(500);
            response.setMessage("An internal error occurred: "+ e.getMessage() + ".\nPlease verify logs for more details");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllTicketTipologies")
    public ResponseEntity<Object> getAllTicketTipologies(@RequestHeader("Authorization") String authorization){

        Response response = new Response();

        if(!TokenValidator.validate(authorization)) return new ResponseEntity<>(TokenValidator.generateUnauthResponse(response), HttpStatus.UNAUTHORIZED);

        try{
            List<TicketTipology> ticketTipologyList = ticketTipologyService.getAllTicketTipologies();
            return new ResponseEntity<>(ticketTipologyList, HttpStatus.OK);

        }catch (Exception e){
            logger.error("Exception: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(500);
            response.setMessage("An internal error occurred: "+ e.getMessage() + ".\nPlease verify logs for more details");
            return new ResponseEntity<>(response , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/deleteTicketTipology")
    public ResponseEntity<Object> deleteTicketTipology(@RequestHeader("Authorization") String authorization, @RequestBody TicketTipology ticketTipology){

        Response response = new Response();

        if(!TokenValidator.validate(authorization)) return new ResponseEntity<>(TokenValidator.generateUnauthResponse(response), HttpStatus.UNAUTHORIZED);

        try{
            if(ticketTipologyService.deleteTicketTipology(ticketTipology)){
                response.setStatus(200);
                response.setMessage("TicketTipology correctly deleted from cloudant DB.");
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
            return new ResponseEntity<>(response , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
