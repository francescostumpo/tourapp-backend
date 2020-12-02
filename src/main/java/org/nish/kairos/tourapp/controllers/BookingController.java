package org.nish.kairos.tourapp.controllers;

import org.nish.kairos.tourapp.model.Booking;
import org.nish.kairos.tourapp.model.Response;
import org.nish.kairos.tourapp.model.Site;
import org.nish.kairos.tourapp.services.BookingService;
import org.nish.kairos.tourapp.services.SiteService;
import org.nish.kairos.tourapp.utils.TokenValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/api/bo")
public class BookingController {

    @Inject
    BookingService bookingService;

    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);

    @PostMapping("/createOrUpdateBooking")
    public ResponseEntity<Response> createOrUpdateBooking(@RequestHeader("Authorization") String authorization, @RequestBody Booking booking){

        Response response = new Response();

        if(!TokenValidator.validate(authorization)) return new ResponseEntity<>(TokenValidator.generateUnauthResponse(response), HttpStatus.UNAUTHORIZED);

        try{
            if(bookingService.createOrUpdateBooking(booking)){
                response.setStatus(200);
                if(booking.get_id() == null){
                    response.setMessage("Booking correctly created on cloudant DB.");
                }else{
                    response.setMessage("Booking correctly updated on cloudant DB.");
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

    @GetMapping("/getBooking/{bookingId}")
    public ResponseEntity<Object> createTourOperator(@RequestHeader("Authorization") String authorization, @PathVariable String bookingId){

        Response response = new Response();

        if(!TokenValidator.validate(authorization)) return new ResponseEntity<>(TokenValidator.generateUnauthResponse(response), HttpStatus.UNAUTHORIZED);

        try{
            Booking booking = bookingService.getBooking(bookingId);
            if(booking != null){
                return new ResponseEntity<>(booking, HttpStatus.OK);
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

    @GetMapping("/getAllBookings")
    public ResponseEntity<Object> getAllBookings(@RequestHeader("Authorization") String authorization){

        Response response = new Response();

        if(!TokenValidator.validate(authorization)) return new ResponseEntity<>(TokenValidator.generateUnauthResponse(response), HttpStatus.UNAUTHORIZED);

        try{
            List<Booking> bookingList = bookingService.getAllBookings();
            return new ResponseEntity<>(bookingList, HttpStatus.OK);

        }catch (Exception e){
            logger.error("Exception: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(500);
            response.setMessage("An internal error occurred: "+ e.getMessage() + ". Please verify logs for more details");
            return new ResponseEntity<>(response , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/deleteBooking")
    public ResponseEntity<Object> deleteBooking(@RequestHeader("Authorization") String authorization, @RequestBody Booking booking){

        Response response = new Response();

        if(!TokenValidator.validate(authorization)) return new ResponseEntity<>(TokenValidator.generateUnauthResponse(response), HttpStatus.UNAUTHORIZED);

        try{
            if(bookingService.deleteBooking(booking)){
                response.setStatus(200);
                response.setMessage("Booking correctly deleted from cloudant DB.");
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
