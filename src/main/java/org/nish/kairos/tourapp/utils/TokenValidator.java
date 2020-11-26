package org.nish.kairos.tourapp.utils;

import org.nish.kairos.tourapp.model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TokenValidator {

    private static final Logger logger = LoggerFactory.getLogger(TokenValidator.class);

    public static boolean validate(String authorization){
        if(authorization == null){
            return false;
        }else{
            logger.info("Authorization: " + authorization);
            String[] splitAuth = authorization.split(" ");
            logger.info(splitAuth[1]);
            //TO-DO Implement logic to validate token
            return true;
        }
    }

    public static Response generateUnauthResponse(Response response){
        response.setStatus(401);
        response.setMessage("Access denied. Please verify your token.");

        return response;
    }
}
