package org.nish.kairos.tourapp.controllers;

import org.nish.kairos.tourapp.model.Response;
import org.nish.kairos.tourapp.services.ReportingService;
import org.nish.kairos.tourapp.utils.TokenValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.io.InputStream;
import java.util.Date;

@RestController
@RequestMapping("/api/re")
public class ReportingController {

    @Inject
    ReportingService reportingService;

    private static final Logger logger = LoggerFactory.getLogger(ReportingController.class);

    @GetMapping("/downloadReport")
    public ResponseEntity<Object> downloadReport(@RequestHeader("Authorization") String authorization, @RequestParam String type, @RequestParam String dateDa, @RequestParam String dateA){

        Response response = new Response();

        if(!TokenValidator.validate(authorization)) return new ResponseEntity<>(TokenValidator.generateUnauthResponse(response), HttpStatus.UNAUTHORIZED);


        try{
            InputStream is = reportingService.downloadReport(type,dateDa, dateA);
            return new ResponseEntity<>(is, HttpStatus.OK);

        }catch (Exception e){
            logger.error("Exception: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(500);
            response.setMessage("An internal error occurred: "+ e.getMessage() + ". Please verify logs for more details");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
