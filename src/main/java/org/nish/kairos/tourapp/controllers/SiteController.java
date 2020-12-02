package org.nish.kairos.tourapp.controllers;

import org.nish.kairos.tourapp.model.Response;
import org.nish.kairos.tourapp.model.Site;
import org.nish.kairos.tourapp.model.TourOperator;
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
@RequestMapping("/api/si")
public class SiteController {

    @Inject
    SiteService siteService;

    private static final Logger logger = LoggerFactory.getLogger(SiteController.class);

    @PostMapping("/createOrUpdateSite")
    public ResponseEntity<Response> createOrUpdateTourOperator(@RequestHeader("Authorization") String authorization, @RequestBody Site site){

        Response response = new Response();

        if(!TokenValidator.validate(authorization)) return new ResponseEntity<>(TokenValidator.generateUnauthResponse(response), HttpStatus.UNAUTHORIZED);

        try{
            if(siteService.createOrUpdateSite(site)){
                response.setStatus(200);
                if(site.get_id() == null){
                    response.setMessage("Site correctly created on cloudant DB.");
                }else{
                    response.setMessage("Site correctly updated on cloudant DB.");
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

    @GetMapping("/getSite/{siteId}")
    public ResponseEntity<Object> createTourOperator(@RequestHeader("Authorization") String authorization, @PathVariable String siteId){

        Response response = new Response();

        if(!TokenValidator.validate(authorization)) return new ResponseEntity<>(TokenValidator.generateUnauthResponse(response), HttpStatus.UNAUTHORIZED);

        try{
            Site site = siteService.getSite(siteId);
            if(site != null){
                return new ResponseEntity<>(site, HttpStatus.OK);
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

    @GetMapping("/getAllSites")
    public ResponseEntity<Object> getAllSites(@RequestHeader("Authorization") String authorization){

        Response response = new Response();

        if(!TokenValidator.validate(authorization)) return new ResponseEntity<>(TokenValidator.generateUnauthResponse(response), HttpStatus.UNAUTHORIZED);

        try{
            List<Site> siteList = siteService.getAllSites();
            return new ResponseEntity<>(siteList, HttpStatus.OK);

        }catch (Exception e){
            logger.error("Exception: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(500);
            response.setMessage("An internal error occurred: "+ e.getMessage() + ". Please verify logs for more details");
            return new ResponseEntity<>(response , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/deleteSite")
    public ResponseEntity<Object> deleteSite(@RequestHeader("Authorization") String authorization, @RequestBody Site site){

        Response response = new Response();

        if(!TokenValidator.validate(authorization)) return new ResponseEntity<>(TokenValidator.generateUnauthResponse(response), HttpStatus.UNAUTHORIZED);

        try{
            if(siteService.deleteSite(site)){
                response.setStatus(200);
                response.setMessage("Site correctly deleted from cloudant DB.");
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
