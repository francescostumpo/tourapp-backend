package org.nish.kairos.tourapp.services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.nish.kairos.tourapp.managers.DbManager;
import org.nish.kairos.tourapp.model.Site;
import org.nish.kairos.tourapp.model.TicketStandard;
import org.nish.kairos.tourapp.utils.Converter;
import org.nish.kairos.tourapp.utils.GeneralHelper;
import org.nish.kairos.tourapp.utils.TicketGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.util.*;

@Component
public class TicketStandardService {

    private static final String ENTITY_NAME = "TicketStandard";
    private static final String DB_NAME = "ticketstandard";
    private static final String SINGLE_MODE = "single";
    private static final String MULTI_MODE = "multiple";

    private static final Logger logger = LoggerFactory.getLogger(TicketStandardService.class);

    public BufferedImage createOrUpdateTicketStandard(String mode, TicketStandard ticketStandard) throws Exception {
        logger.info("Creating/Updating TicketStandard: " + ticketStandard.getTipologiaTicket());
        BufferedImage ticketBufferedImg = null;

        Date date = new Date();
        String uniqueTicketId = GeneralHelper.generateUniqueIdForTicket(date);
        if(mode.equals(SINGLE_MODE)){
            ticketBufferedImg = TicketGenerator.generateMultipleTicketsStandard(uniqueTicketId, ticketStandard.getnIngressi(), GeneralHelper.getDateFormatted(date), ticketStandard.getTipologiaTicket().getNome(), ticketStandard.getSiti(), ENTITY_NAME);
        }else{
            ticketBufferedImg = TicketGenerator.generateTicketStandard(uniqueTicketId, ticketStandard.getnIngressi(), GeneralHelper.getDateFormatted(date), ticketStandard.getTipologiaTicket().getNome(), ticketStandard.getSiti(), ENTITY_NAME);
        }
        ticketStandard.setTicketId(GeneralHelper.generateUniqueIdForTicket(date));
        ticketStandard.setDataEmissione(GeneralHelper.getDateFormatted(date));

        ticketStandard.setTotaleEuro(ticketStandard.getnIngressi()*ticketStandard.getTipologiaTicket().getPrezzo());

        if(DbManager.createOrUpdateCloudantDoc(DB_NAME, ticketStandard) != null){
            return ticketBufferedImg;
        }else{
            logger.error("Unable to create doc on cloudant DB.");
            throw new RuntimeException();
        }
    }

    public List<TicketStandard> getTicketStandard(String ticketNo){
        logger.info("Verifying ticket with ticketID: " + ticketNo);
        Map<String, Object> queryParam = new HashMap<>();
        queryParam.put("ticketId", ticketNo);

        List<TicketStandard> ticketStandardList = (List<TicketStandard>) DbManager.getCloudantDocFromQuery(DB_NAME, queryParam, ENTITY_NAME);
        return ticketStandardList;
    }

    public List<TicketStandard> getAllTicketsStandard(){
        logger.info("Retrieving all ticketsStandard");
        List<TicketStandard> ticketStandardList = (List<TicketStandard>) DbManager.getAllCloudantDocs(DB_NAME, ENTITY_NAME);
        return ticketStandardList;
    }

    public void validateTicketStandard(String ticketNo) throws Exception {
        try{
            List<TicketStandard> ticketStandardList = getTicketStandard(ticketNo);
            TicketStandard ticketStandard = null;
            if(ticketStandardList.size() > 0){
                ticketStandard = ticketStandardList.get(0);
            }else{
                return;
            }
            String revision = null;
            for(Site site: ticketStandard.getSiti()){
                if(site.isValid()){
                    site.setValid(false);
                    revision = DbManager.createOrUpdateCloudantDoc(DB_NAME, ticketStandard);
                    ticketStandard.set_rev(revision);
                }
            }
            return;
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception();
        }
    }
}
