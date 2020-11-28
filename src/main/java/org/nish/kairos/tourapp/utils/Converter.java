package org.nish.kairos.tourapp.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.nish.kairos.tourapp.model.*;

import java.util.ArrayList;
import java.util.List;

public class Converter {

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

    public static Object convertJsonObjectListToEntityList(List<JsonObject> jsonObjectList, String entityName){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            switch (entityName) {
                case EntityConstants.TEST_ENTITY:
                    List<Test> test = objectMapper.readValue(jsonObjectList.toString(), new TypeReference<>() {});
                    return test;
                case EntityConstants.BOOKING_ENTITY:
                    List<Booking> booking = objectMapper.readValue(jsonObjectList.toString(), new TypeReference<>() {});
                    return booking;
                case EntityConstants.SITE_ENTITY:
                    List<Site> site = objectMapper.readValue(jsonObjectList.toString(), new TypeReference<>() {});
                    return site;
                case EntityConstants.TICKET_STANDARD_ENTITY:
                    List<TicketStandard> ticketStandard = objectMapper.readValue(jsonObjectList.toString(), new TypeReference<>() {});
                    return ticketStandard;
                case EntityConstants.TICKET_TIPOLOGY_ENTITY:
                    List<TicketTipology> ticketTipology = objectMapper.readValue(jsonObjectList.toString(), new TypeReference<>() {});
                    return ticketTipology;
                case EntityConstants.TICKET_VIRTUAL_ENTITY:
                    List<TicketVirtual> ticketVirtual = objectMapper.readValue(jsonObjectList.toString(), new TypeReference<>() {});
                    return ticketVirtual;
                case EntityConstants.TOUR_OPERATOR_ENTITY:
                    List<TourOperator> tourOperator = objectMapper.readValue(jsonObjectList.toString(), new TypeReference<>() {});
                    return tourOperator;
                default:
                    return null;
            }
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object convertDocumentStringListToEntityList(String entitiesListString, boolean manualQuery, String entityName){
        List<JsonObject> jsonObjectList = convertDocumentStringListToJsonObjectList(entitiesListString, manualQuery);
        return convertJsonObjectListToEntityList(jsonObjectList, entityName);

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


    public static List<JsonObject> convertDocumentStringListToJsonObjectList(String entitiesListString, boolean manualQuery){
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
