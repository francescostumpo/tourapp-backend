package org.nish.kairos.tourapp.controllers;

import org.nish.kairos.tourapp.managers.DbManager;
import org.nish.kairos.tourapp.model.Response;
import org.nish.kairos.tourapp.model.TourOperator;
import org.nish.kairos.tourapp.services.TourOperatorService;
import org.nish.kairos.tourapp.utils.TokenValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;


@RestController
@RequestMapping("/api/to")
public class TourOperatorController {

    @Inject
    TourOperatorService tourOperatorService;

    private static final Logger logger = LoggerFactory.getLogger(TourOperatorController.class);

    @PostMapping("/createOrUpdateTourOperator")
    public ResponseEntity<Response> createOrUpdateTourOperator(@RequestHeader("Authorization") String authorization, @RequestBody TourOperator tourOperator){

        Response response = new Response();

        if(!TokenValidator.validate(authorization)) return new ResponseEntity<>(TokenValidator.generateUnauthResponse(response), HttpStatus.UNAUTHORIZED);

        try{
            if(tourOperatorService.createOrUpdateTourOperator(tourOperator)){
                response.setStatus(200);
                if(tourOperator.get_id() == null){
                    response.setMessage("TourOperator correctly created on cloudant DB.");
                }else{
                    response.setMessage("TourOperator correctly updated on cloudant DB.");
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

    @GetMapping("/getTourOperator/{tourOperatorId}")
    public ResponseEntity<Object> createTourOperator(@RequestHeader("Authorization") String authorization, @PathVariable String tourOperatorId){

        Response response = new Response();

        if(!TokenValidator.validate(authorization)) return new ResponseEntity<>(TokenValidator.generateUnauthResponse(response), HttpStatus.UNAUTHORIZED);

        try{
            TourOperator tourOperator = tourOperatorService.getTourOperator(tourOperatorId);
            if(tourOperator != null){
                return new ResponseEntity<>(tourOperator, HttpStatus.OK);
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

    @GetMapping("/getAllTourOperators")
    public ResponseEntity<Object> getAllTourOperators(@RequestHeader("Authorization") String authorization){

        Response response = new Response();

        if(!TokenValidator.validate(authorization)) return new ResponseEntity<>(TokenValidator.generateUnauthResponse(response), HttpStatus.UNAUTHORIZED);

        try{
            List<TourOperator> tourOperatorList = tourOperatorService.getAllTourOperators();
            return new ResponseEntity<>(tourOperatorList, HttpStatus.OK);

        }catch (Exception e){
            logger.error("Exception: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(500);
            response.setMessage("An internal error occurred: "+ e.getMessage() + ". Please verify logs for more details");
            return new ResponseEntity<>(response , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/deleteTourOperator")
    public ResponseEntity<Object> deleteTourOperator(@RequestHeader("Authorization") String authorization, @RequestBody TourOperator tourOperator){

        Response response = new Response();

        if(!TokenValidator.validate(authorization)) return new ResponseEntity<>(TokenValidator.generateUnauthResponse(response), HttpStatus.UNAUTHORIZED);

        try{
            if(tourOperatorService.deleteTourOperator(tourOperator)){
                response.setStatus(200);
                response.setMessage("TourOperator correctly deleted from cloudant DB.");
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
