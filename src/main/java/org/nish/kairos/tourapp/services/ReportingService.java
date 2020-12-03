package org.nish.kairos.tourapp.services;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.nish.kairos.tourapp.controllers.ReportingController;
import org.nish.kairos.tourapp.managers.DbManager;
import org.nish.kairos.tourapp.model.Site;
import org.nish.kairos.tourapp.model.TicketStandard;
import org.nish.kairos.tourapp.utils.ExcelGenerator;
import org.nish.kairos.tourapp.utils.GeneralHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class ReportingService {

    @Inject
    TicketStandardService ticketStandardService;

    private static final String ENTITY_NAME_TS = "TicketStandard";
    private static final String DB_NAME_TS = "ticketstandard";
    private static final Logger logger = LoggerFactory.getLogger(ReportingController.class);

    public InputStream downloadReport(String type, String dateDa, String dateA) {
        Date dateDaF = null;
        Date dateAF = null;

        try {
            String dateDSF = GeneralHelper.convertStringDateBrowserToStringDateServer(dateDa);
            String dateASF = GeneralHelper.convertStringDateBrowserToStringDateServer(dateA);

            dateDaF = GeneralHelper.formatStringDateToDate(dateDSF);
            dateAF = GeneralHelper.formatStringDateToDate(dateASF);
            XSSFWorkbook workbook = ExcelGenerator.writeReport();
            if(type.equals("ts")){
                XSSFSheet sheet = ExcelGenerator.createSheet(workbook, type);
                int rowNum = 0;
                List<TicketStandard> ticketsStandardEmitted = ticketStandardService.getAllTicketsStandard();
                Collections.sort(ticketsStandardEmitted);
                for(TicketStandard ticketStandard: ticketsStandardEmitted){
                    Date dateEmissione = GeneralHelper.formatStringDateToDate(ticketStandard.getDataEmissione());
                    if(dateEmissione.after(dateDaF) && dateEmissione.before(dateAF)){
                        rowNum = rowNum + 1;
                        String siti = "";
                        for(Site site: ticketStandard.getSiti()){
                            siti = siti + site.getNome() + " ";
                        }
                        String tourOperatorSocieta = ticketStandard.getTourOperator() != null ? ticketStandard.getTourOperator().getSocieta() : "";
                        String nazione = ticketStandard.getNazione() != null ? ticketStandard.getNazione() : "";
                        ExcelGenerator.createTsRow(sheet, rowNum, ticketStandard.getTicketId(), ticketStandard.getTipologiaTicket().getNome(), siti, tourOperatorSocieta, ticketStandard.getnIngressi(), nazione, ticketStandard.getDataEmissione(), ticketStandard.getTotaleEuro());
                    }
                }
            }else{
                // TO-DO da fare per i ticketvirtual
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            workbook.write(baos);
            workbook.close();
            InputStream is = new ByteArrayInputStream(baos.toByteArray());
            return is;
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
