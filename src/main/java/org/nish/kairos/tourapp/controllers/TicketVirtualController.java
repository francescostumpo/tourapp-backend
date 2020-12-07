package org.nish.kairos.tourapp.controllers;

import org.nish.kairos.tourapp.model.Response;
import org.nish.kairos.tourapp.model.TicketVirtual;
import org.nish.kairos.tourapp.services.TicketVirtualService;
import org.nish.kairos.tourapp.utils.InvoiceGenerator;
import org.nish.kairos.tourapp.utils.TokenValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.ws.rs.CookieParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.NewCookie;
import java.awt.image.BufferedImage;
import java.io.*;

import java.util.List;

@RestController
@RequestMapping("/api/tv")
@Path("/api/tv")
public class TicketVirtualController {

    @Inject
    TicketVirtualService ticketVirtualService;


    private static final Logger logger = LoggerFactory.getLogger(TicketVirtualController.class);

    @PostMapping(value = "/createOrUpdateTicketVirtual", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<Object> createOrUpdateTicket(@RequestHeader("Authorization") String authorization, @RequestParam boolean validate, @RequestBody TicketVirtual ticketVirtual){

        Response response = new Response();

        if(!TokenValidator.validate(authorization)) return new ResponseEntity<>(TokenValidator.generateUnauthResponse(response), HttpStatus.UNAUTHORIZED);

        try{
            BufferedImage tickets = ticketVirtualService.createOrUpdateTicketVirtual(ticketVirtual, validate);
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
            List<TicketVirtual> ticketVirtualList = ticketVirtualService.getTicketVirtual(ticketNo);
            TicketVirtual ticketVirtual = null;
            if(ticketVirtualList.size() > 0){
                ticketVirtual = ticketVirtualList.get(0);
                return new ResponseEntity<>(ticketVirtual, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(ticketVirtualList, HttpStatus.OK);
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
            ticketVirtualService.validateTicketVirtual(ticketNo);
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

    @PostMapping("/generateFattura")
    public ResponseEntity<Object> generateFattura(@RequestBody TicketVirtual ticketVirtual){
        Response response = new Response();
        InputStream is = null;
        try{
            is = InvoiceGenerator.generateFatturaVirtual(ticketVirtual);
            return new ResponseEntity<>(is, HttpStatus.OK);
        }catch (Exception e){
            logger.error("Exception: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(500);
            response.setMessage("An internal error occurred: "+ e.getMessage() + ". Please verify logs for more details");
            return new ResponseEntity<>(response , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Path("/loginToVirtualTour")
    public javax.ws.rs.core.Response validateTicket(@CookieParam("authCookie") String authCookie, TicketVirtual ticketVirtual){
        Response response = new Response();

        try{
            String sitoVirtualTour = ticketVirtualService.loginToVirtualTour(ticketVirtual, authCookie);
            if(sitoVirtualTour.equals("") || sitoVirtualTour.equals("La password inserita non è corretta")){
                response.setStatus(401);
                response.setMessage("Inserire un ticketId e una password validi.");
                return javax.ws.rs.core.Response.serverError().entity(response).build();
            }else if(sitoVirtualTour.equals("Ticket non ancora attivato")){
                response.setStatus(403);
                response.setMessage("Il ticket non è stato ancora attivato. Si prega di attivarlo presso i siti Kairos.");
                return javax.ws.rs.core.Response.serverError().entity(response).build();
            }
            response.setStatus(200);
            response.setMessage(sitoVirtualTour);
            if(authCookie == null){
                NewCookie authCookie1 = new NewCookie("authCookie", ticketVirtual.getTicketId()+ticketVirtual.getRandomPassword(), "/", "", "Auth Cookie", 90000, false);
                return javax.ws.rs.core.Response.ok(response).cookie(authCookie1).build();
            }else {
                return javax.ws.rs.core.Response.ok(response).build();
            }
        }catch (Exception e){
            logger.error("Exception: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(500);
            response.setMessage("An internal error occurred: "+ e.getMessage() + ". Please verify logs for more details");
            return javax.ws.rs.core.Response.serverError().build();
        }
    }
}
