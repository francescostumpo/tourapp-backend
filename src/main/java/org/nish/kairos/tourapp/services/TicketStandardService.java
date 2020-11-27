package org.nish.kairos.tourapp.services;

import org.nish.kairos.tourapp.managers.DbManager;
import org.nish.kairos.tourapp.model.TicketStandard;
import org.nish.kairos.tourapp.utils.GeneralHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TicketStandardService {

    private static final String ENTITY_NAME = "TicketStandard";
    private static final String DB_NAME = "ticketstandard";
    private static final Logger logger = LoggerFactory.getLogger(TicketStandardService.class);

    public boolean createOrUpdateTicketStandard(TicketStandard ticketStandard){
        logger.info("Creating/Updating TicketTipology: " + ticketStandard.getTipologiaTicket());

        if(ticketStandard.getTicketId() == null){
            Date date = new Date();
            ticketStandard.setTicketId(GeneralHelper.generateUniqueIdForTicket(date));
            ticketStandard.setDataEmissione(GeneralHelper.getDateFormatted(date));
        }

        return DbManager.createOrUpdateCloudantDoc(DB_NAME, ticketStandard);
    }
}
