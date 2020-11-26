package org.nish.kairos.tourapp.probes;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.nish.kairos.tourapp.managers.DbManager;
import org.nish.kairos.tourapp.model.Response;
import org.nish.kairos.tourapp.utils.TokenValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthProbe {

    private static final Logger logger = LoggerFactory.getLogger(HealthProbe.class);

    @GetMapping("/liveness")
    public ResponseEntity<Response> liveness(){

        Response response = new Response();
        response.setStatus(200);
        response.setMessage("TourApp is up & running.");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/readiness")
    public ResponseEntity<Response> readiness() {

        Response response = new Response();
        response.setMessage("Cloudant Version: " + DbManager.getCloudantVersion() + ". Application is ready.");
        response.setStatus(200);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getTestDoc")
    public ResponseEntity<Object> getTestDoc(@RequestHeader("Authorization") String authorization) throws JsonProcessingException {

        return new ResponseEntity<>(DbManager.getCloudantDoc("test", "561d99d38f6477c3b7d04560c7b48127", "Test"), HttpStatus.OK);

    }
}
