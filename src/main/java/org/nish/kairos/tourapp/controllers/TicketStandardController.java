package org.nish.kairos.tourapp.controllers;

import com.google.gson.JsonArray;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import org.nish.kairos.tourapp.model.Response;
import org.nish.kairos.tourapp.model.Site;
import org.nish.kairos.tourapp.model.TicketStandard;
import org.nish.kairos.tourapp.model.TicketTipology;
import org.nish.kairos.tourapp.services.TicketStandardService;
import org.nish.kairos.tourapp.utils.InvoiceGenerator;
import org.nish.kairos.tourapp.utils.TicketGenerator;
import org.nish.kairos.tourapp.utils.TokenValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/ts")
public class TicketStandardController {
    @Inject
    Template validityTemplate;

    private static final Logger logger = LoggerFactory.getLogger(TicketStandardController.class);

    @Inject
    TicketStandardService ticketStandardService;

    @PostMapping(value = "/createOrUpdateTicketStandard", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<Object> createOrUpdateTicket(@RequestHeader("Authorization") String authorization, @RequestParam String mode, @RequestBody TicketStandard ticketStandard){

        Response response = new Response();

        if(!TokenValidator.validate(authorization)) return new ResponseEntity<>(TokenValidator.generateUnauthResponse(response), HttpStatus.UNAUTHORIZED);

        try{
            BufferedImage tickets = ticketStandardService.createOrUpdateTicketStandard(mode, ticketStandard);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(tickets, "png", os);                          // Passing: ​(RenderedImage im, String formatName, OutputStream output)
            InputStream is = new ByteArrayInputStream(os.toByteArray());
            return new ResponseEntity<>(is, HttpStatus.OK);

        }catch (Exception e){
            logger.error("Exception: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(500);
            response.setMessage("An internal error occurred: "+ e.getMessage() + ". Please verify logs for more details");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/verifyTicket/{ticketNo}")
    public ResponseEntity<Object> verifyTicket(@RequestHeader("Authorization") String authorization, @PathVariable String ticketNo){
        Response response = new Response();

        if(!TokenValidator.validate(authorization)) return new ResponseEntity<>(TokenValidator.generateUnauthResponse(response), HttpStatus.UNAUTHORIZED);

        try{
            List<TicketStandard> ticketStandardList = ticketStandardService.getTicketStandard(ticketNo);
            TicketStandard ticketStandard = null;
            if(ticketStandardList.size() > 0){
                ticketStandard = ticketStandardList.get(0);
                return new ResponseEntity<>(ticketStandard, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(ticketStandardList, HttpStatus.OK);
            }
        }catch (Exception e){
            logger.error("Exception: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(500);
            response.setMessage("An internal error occurred: "+ e.getMessage() + ". Please verify logs for more details");
            return new ResponseEntity<>(response , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/validateTicket/{ticketNo}")
    public ResponseEntity<Object> validateTicket(@RequestHeader("Authorization") String authorization, @PathVariable String ticketNo){
        Response response = new Response();

        if(!TokenValidator.validate(authorization) && !authorization.equals(System.getenv("VERIFICATION_CODE"))) return new ResponseEntity<>(TokenValidator.generateUnauthResponse(response), HttpStatus.UNAUTHORIZED);

        try{
            ticketStandardService.validateTicketStandard(ticketNo);
            response.setStatus(200);
            response.setMessage("Il ticket " + ticketNo + " è stato convalidato correttamente.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            logger.error("Exception: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(500);
            response.setMessage("An internal error occurred: "+ e.getMessage() + ". Please verify logs for more details");
            return new ResponseEntity<>(response , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllTicketsStandard")
    public ResponseEntity<Object> getAllTicketsStandard(@RequestHeader("Authorization") String authorization){
        Response response = new Response();

        if(!TokenValidator.validate(authorization)) return new ResponseEntity<>(TokenValidator.generateUnauthResponse(response), HttpStatus.UNAUTHORIZED);
        try{
            List<TicketStandard> ticketStandardList = ticketStandardService.getAllTicketsStandard();
            if(ticketStandardList != null){
                return new ResponseEntity<>(ticketStandardList, HttpStatus.OK);
            }
            return new ResponseEntity<>(new JsonArray(), HttpStatus.OK);
        }catch (Exception e){
            logger.error("Exception: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(500);
            response.setMessage("An internal error occurred: "+ e.getMessage() + ". Please verify logs for more details");
            return new ResponseEntity<>(response , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/generateFattura")
    public ResponseEntity<Object> generateFattura(@RequestBody TicketStandard ticketStandard){
        Response response = new Response();
        InputStream is = null;
        try{
            is = InvoiceGenerator.generateFattura(ticketStandard);
            return new ResponseEntity<>(is, HttpStatus.OK);
        }catch (Exception e){
            logger.error("Exception: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(500);
            response.setMessage("An internal error occurred: "+ e.getMessage() + ". Please verify logs for more details");
            return new ResponseEntity<>(response , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/generateTicketMatrix", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<Object> generateTicketMatrix(@RequestHeader("Authorization") String authorization, @RequestBody TicketStandard ticketStandard){
        Response response = new Response();

        if(!TokenValidator.validate(authorization)) return new ResponseEntity<>(TokenValidator.generateUnauthResponse(response), HttpStatus.UNAUTHORIZED);

        try{
            BufferedImage tickets = ticketStandardService.generateTicketMatrix(ticketStandard);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(tickets, "png", os);                          // Passing: ​(RenderedImage im, String formatName, OutputStream output)
            InputStream is = new ByteArrayInputStream(os.toByteArray());
            return new ResponseEntity<>(is, HttpStatus.OK);

        }catch (Exception e){
            logger.error("Exception: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(500);
            response.setMessage("An internal error occurred: "+ e.getMessage() + ". Please verify logs for more details");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/ticket/{ticketNo}", produces = "text/html")
    public TemplateInstance validityTemplate(@PathVariable String ticketNo){

        List<TicketStandard> ticketStandardList = ticketStandardService.getTicketStandard(ticketNo);
        TicketStandard ticketStandard = null;
        if(ticketStandardList.size()>0){
            ticketStandard = ticketStandardList.get(0);
        }
        boolean armed = false;
        for(Site site: ticketStandard.getSiti()){
            if(site.isValid()){
                armed = true;
            }
        }

        return validityTemplate
                .data("ticketStandard", ticketStandard)
                .data("armed", armed);


    }
}
