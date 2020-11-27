package org.nish.kairos.tourapp.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.nish.kairos.tourapp.managers.DbManager;
import org.nish.kairos.tourapp.model.Response;
import org.nish.kairos.tourapp.model.Test;
import org.nish.kairos.tourapp.services.TestService;
import org.nish.kairos.tourapp.utils.TokenValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TestController {

    @Inject
    TestService testService;

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/getTestDoc")
    public ResponseEntity<Object> getTestDoc(@RequestHeader("Authorization") String authorization) throws JsonProcessingException {

        return new ResponseEntity<>(testService.getTestDoc(), HttpStatus.OK);
    }

    @PostMapping("/insertTestDoc")
    public ResponseEntity<Response> insertTestDoc(@RequestHeader("Authorization") String authorization, @RequestBody Test test){

        Response response = new Response();

        if(!TokenValidator.validate(authorization)) return new ResponseEntity<>(TokenValidator.generateUnauthResponse(response), HttpStatus.UNAUTHORIZED);

        try{
            DbManager.createOrUpdateCloudantDoc("test", test);
            response.setStatus(200);
            response.setMessage("Document correctly inserted on cloudant DB.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            response.setStatus(500);
            response.setMessage("An internal error occurred: "+ e.getMessage() + ".\nPlease verify logs for more details");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



    @GetMapping("/getAllTestDocs")
    public ResponseEntity<List<Test>> getAllTourOperators() {

        List<Test> testList = testService.getAllTestDocuments();
        return new ResponseEntity<>(testList, HttpStatus.OK);

    }
}
