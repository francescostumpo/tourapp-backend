package org.nish.kairos.tourapp.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.nish.kairos.tourapp.model.*;

import java.util.ArrayList;
import java.util.List;

public class CloudantConverter {

    public static Object convertToEntity(String entityName, String document){

        switch (entityName){
            case EntityConstants.TEST_ENTITY:
                Test test = new Gson().fromJson(document, Test.class);
                return test;
            case EntityConstants.BOOKING_ENTITY:
                Booking booking = new Gson().fromJson(document, Booking.class);
                return booking;
            case EntityConstants.SITE_ENTITY:
                Site site = new Gson().fromJson(document, Site.class);
                return site;
            case EntityConstants.TICKET_STANDARD_ENTITY:
                TicketStandard ticketStandard = new Gson().fromJson(document, TicketStandard.class);
                return ticketStandard;
            case EntityConstants.TICKET_TIPOLOGY_ENTITY:
                TicketTipology ticketTipology = new Gson().fromJson(document, TicketTipology.class);
                return ticketTipology;
            case EntityConstants.TICKET_VIRTUAL_ENTITY:
                TicketVirtual ticketVirtual = new Gson().fromJson(document, TicketVirtual.class);
                return ticketVirtual;
            case EntityConstants.TOUR_OPERATOR_ENTITY:
                TourOperator tourOperator = new Gson().fromJson(document, TourOperator.class);
                return tourOperator;
            default:
                return null;
        }
    }


    public static JsonObject convertToJson(Object object) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonObject entityJson = null;
        try {
            String entityString = objectMapper.writeValueAsString(object);
            entityJson = new Gson().fromJson(entityString, JsonObject.class);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw e;
        }
        return entityJson;
    }


    public static List<JsonObject> convertDocumentStringListToObjectList(String entitiesListString, boolean manualQuery){
        JsonArray entitiesListJO = new Gson().fromJson(entitiesListString, JsonArray.class);
        List<JsonObject> objectList = new ArrayList<>();
        for(int i=0; i< entitiesListJO.size(); i++){
            JsonObject jo = null;
            if(!manualQuery){
                jo = (JsonObject) entitiesListJO.get(i).getAsJsonObject().get("doc");
            }else{
                jo = entitiesListJO.get(i).getAsJsonObject();
            }
            objectList.add(jo);
        }
        return objectList;
    }
}
