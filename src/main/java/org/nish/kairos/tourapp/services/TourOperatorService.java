package org.nish.kairos.tourapp.services;

import org.nish.kairos.tourapp.controllers.TourOperatorController;
import org.nish.kairos.tourapp.managers.DbManager;
import org.nish.kairos.tourapp.model.TourOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TourOperatorService {

    private static final String ENTITY_NAME = "TourOperator";
    private static final String DB_NAME = "touroperators";
    private static final Logger logger = LoggerFactory.getLogger(TourOperatorService.class);

    public boolean createOrUpdateTourOperator(TourOperator tourOperator){
        logger.info("Creating/Updating TourOperator: " + tourOperator.getSocieta());
        return DbManager.createOrUpdateCloudantDoc(DB_NAME, tourOperator);
    }

    public TourOperator getTourOperator(String tourOperatorId){
        logger.info("Retrieving TourOperator with ID: " + tourOperatorId);
        return (TourOperator) DbManager.getCloudantDoc(DB_NAME, tourOperatorId, ENTITY_NAME);
    }

    public boolean deleteTourOperator(TourOperator tourOperator){
        String _id = tourOperator.get_id();
        String _rev = tourOperator.get_rev();
        logger.info("Deleting TourOperator with ID: " + _id + "and REV: " + _rev);
        return DbManager.deleteCloudantDoc(DB_NAME, _id, _rev);
    }

    public List<TourOperator> getAllTourOperators(){
        List<TourOperator> tourOperatorList = (List<TourOperator>) (Object) DbManager.getAllCloudantDocs(DB_NAME);
        return tourOperatorList;
    }



}
