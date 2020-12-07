package org.nish.kairos.tourapp.services;

import org.nish.kairos.tourapp.managers.DbManager;
import org.nish.kairos.tourapp.model.Site;
import org.nish.kairos.tourapp.model.TicketStandard;
import org.nish.kairos.tourapp.model.TicketVirtual;
import org.nish.kairos.tourapp.utils.GeneralHelper;
import org.nish.kairos.tourapp.utils.TicketGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TicketVirtualService {

    private static final String ENTITY_NAME = "TicketVirtual";
    private static final String DB_NAME = "ticketsvirtual";

    private static final Logger logger = LoggerFactory.getLogger(TicketVirtualService.class);

    public BufferedImage createOrUpdateTicketVirtual(TicketVirtual ticketVirtual, boolean validate) throws Exception {
        Date date = new Date();
        String uniqueTicketId = GeneralHelper.generateUniqueIdForTicket(date);
        String randomPassword = GeneralHelper.generateRandomPassword(5, 97, 122);

        logger.info("Creating/Updating TicketVirtual: " + uniqueTicketId + " and RandomPassword: " + randomPassword);
        BufferedImage ticketBufferedImg = TicketGenerator.generateTicketVirtual(uniqueTicketId, randomPassword, "Ticket VirtualTour", ticketVirtual.getTicketTipology().getNome());
        ticketVirtual.setTicketId(GeneralHelper.generateUniqueIdForTicket(date));
        ticketVirtual.setRandomPassword(randomPassword);
        ticketVirtual.setTotaleEuro(ticketVirtual.getTicketTipology().getPrezzo());
        if(!validate){
            ticketVirtual.setAccessoEffettuato(false);
        }else{
            ticketVirtual.setAttivato(true);
            ticketVirtual.setDataAttivazione(GeneralHelper.getDateFormatted(date));
        }
        if(DbManager.createOrUpdateCloudantDoc(DB_NAME, ticketVirtual) != null){
            return ticketBufferedImg;
        }else{
            logger.error("Unable to create doc on cloudant DB.");
            throw new RuntimeException();
        }
    }

    public List<TicketVirtual> getTicketVirtual(String ticketNo){
        logger.info("Verifying ticket with ticketID: " + ticketNo);
        Map<String, Object> queryParam = new HashMap<>();
        queryParam.put("ticketId", ticketNo);

        List<TicketVirtual> ticketVirtualList = (List<TicketVirtual>) DbManager.getCloudantDocFromQuery(DB_NAME, queryParam, ENTITY_NAME);
        return ticketVirtualList;
    }

    public void validateTicketVirtual(String ticketNo) throws Exception {
        try{
            List<TicketVirtual> ticketVirtualList = getTicketVirtual(ticketNo);
            TicketVirtual ticketVirtual = null;
            if(ticketVirtualList.size() > 0){
                ticketVirtual = ticketVirtualList.get(0);
            }else{
                return;
            }
            String revision = null;
            ticketVirtual.setDataAttivazione(GeneralHelper.getDateFormatted(new Date()));
            ticketVirtual.setAttivato(true);
            revision = DbManager.createOrUpdateCloudantDoc(DB_NAME, ticketVirtual);
            ticketVirtual.set_rev(revision);
            return;
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception();
        }
    }

    public String loginToVirtualTour(TicketVirtual ticketVirtual, String cookie){
        logger.info("Cookie: " + cookie);
        List<TicketVirtual> ticketVirtualList = getTicketVirtual(ticketVirtual.getTicketId());
        TicketVirtual ticketVirtualFound = null;
        if(ticketVirtualList.size() > 0){
            ticketVirtualFound = ticketVirtualList.get(0);
        }else{
            return "";
        }
        if(ticketVirtualFound.isAttivato()){
            if(ticketVirtualFound.getRandomPassword().equals(ticketVirtual.getRandomPassword())){
                if(!ticketVirtualFound.isAccessoEffettuato()){
                    ticketVirtualFound.setAccessoEffettuato(true);
                    ticketVirtualFound.setCookieValue(ticketVirtualFound.getTicketId() + ticketVirtualFound.getRandomPassword());
                    String revision = DbManager.createOrUpdateCloudantDoc(DB_NAME, ticketVirtualFound);
                    ticketVirtual.set_rev(revision);
                    return ticketVirtualFound.getTicketTipology().getNome();
                }else{
                    if(cookie == null || !cookie.equals(ticketVirtualFound.getTicketId() + ticketVirtualFound.getRandomPassword())){
                        return "La password inserita non è corretta";
                    }else{
                        if(cookie.equals(ticketVirtualFound.getCookieValue())){
                            return ticketVirtualFound.getTicketTipology().getNome();
                        }else{
                            return "La password inserita non è corretta";
                        }
                    }
                }
            }else{
                return "La password inserita non è corretta";
            }
        }
        return "Ticket non ancora attivato";
    }
}
