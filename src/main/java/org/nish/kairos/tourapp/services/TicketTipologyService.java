package org.nish.kairos.tourapp.services;

import com.google.gson.Gson;
import org.nish.kairos.tourapp.managers.DbManager;
import org.nish.kairos.tourapp.model.TicketTipology;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TicketTipologyService {

    private static final String ENTITY_NAME = "TicketTipology";
    private static final String DB_NAME = "tickettipology";
    private static final Logger logger = LoggerFactory.getLogger(TicketTipologyService.class);

    public boolean createOrUpdateTicketTipology(TicketTipology ticketTipology){
        logger.info("Creating/Updating TicketTipology: " + ticketTipology.getNome());
        if(DbManager.createOrUpdateCloudantDoc(DB_NAME, ticketTipology) != null) return true;
        return false;
    }

    public TicketTipology getTicketTipology(String ticketTipologyId){
        logger.info("Retrieving TicketTipology with ID: " + ticketTipologyId);
        return (TicketTipology) DbManager.getCloudantDoc(DB_NAME, ticketTipologyId, ENTITY_NAME);
    }

    public boolean deleteTicketTipology(TicketTipology ticketTipology){
        String _id = ticketTipology.get_id();
        String _rev = ticketTipology.get_rev();
        logger.info("Deleting TourOperator with ID: " + _id + "and REV: " + _rev);
        return DbManager.deleteCloudantDoc(DB_NAME, _id, _rev);
    }

    public List<TicketTipology> getAllTicketTipologies(){
        List<TicketTipology> ticketTipologyList = (List<TicketTipology>) DbManager.getAllCloudantDocs(DB_NAME, ENTITY_NAME);
        return ticketTipologyList;
    }
}
