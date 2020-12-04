package org.nish.kairos.tourapp.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelGenerator {

    public static XSSFWorkbook writeReport(){
        XSSFWorkbook wb = new XSSFWorkbook();
        return wb;
    }

    public static XSSFSheet createSheet(XSSFWorkbook wb, String type){
        XSSFSheet ws = null;
        if(type.equals("ts")){
            ws = wb.createSheet("Report Tickets Standard");
            createTsHeaderRow(ws);
        }else if(type.equals("ts_summary")){
            ws = wb.createSheet("Resoconto Tickets Standard");
            createSummaryHeader(ws);
        }
        return ws;
    }

    private static void createTsHeaderRow(XSSFSheet ws){
        Row headerRow = ws.createRow(0);
        Cell cell = headerRow.createCell(CellReference.convertColStringToIndex("A"));
        cell.setCellValue("TICKET ID");
        Cell cell1 = headerRow.createCell(CellReference.convertColStringToIndex("B"));
        cell1.setCellValue("TIPOLOGIA TICKET");
        Cell cell2 = headerRow.createCell(CellReference.convertColStringToIndex("C"));
        cell2.setCellValue("SITI VALIDI");
        Cell cell3 = headerRow.createCell(CellReference.convertColStringToIndex("D"));
        cell3.setCellValue("TOUR OPERATOR");
        Cell cell4 = headerRow.createCell(CellReference.convertColStringToIndex("E"));
        cell4.setCellValue("N. INGRESSI");
        Cell cell5 = headerRow.createCell(CellReference.convertColStringToIndex("F"));
        cell5.setCellValue("NAZIONE");
        Cell cell6 = headerRow.createCell(CellReference.convertColStringToIndex("G"));
        cell6.setCellValue("DATA EMISSIONE");
        Cell cell7 = headerRow.createCell(CellReference.convertColStringToIndex("H"));
        cell7.setCellValue("LUOGO EMISSIONE");
        Cell cell8 = headerRow.createCell(CellReference.convertColStringToIndex("I"));
        cell8.setCellValue("TOTALE (€)");
    }

    public static void createTsRow(XSSFSheet ws, int rowNum, String ticketId, String tipologiaTicket, String siti, String tourOperator, int nIngressi, String nazione, String dataEmissione, String luogoEmissione, Double totaleEuro){
        Row headerRow = ws.createRow(rowNum);
        Cell cell = headerRow.createCell(CellReference.convertColStringToIndex("A"));
        cell.setCellValue(ticketId);
        Cell cell1 = headerRow.createCell(CellReference.convertColStringToIndex("B"));
        cell1.setCellValue(tipologiaTicket);
        Cell cell2 = headerRow.createCell(CellReference.convertColStringToIndex("C"));
        cell2.setCellValue(siti);
        Cell cell3 = headerRow.createCell(CellReference.convertColStringToIndex("D"));
        cell3.setCellValue(tourOperator);
        Cell cell4 = headerRow.createCell(CellReference.convertColStringToIndex("E"));
        cell4.setCellValue(nIngressi);
        Cell cell5 = headerRow.createCell(CellReference.convertColStringToIndex("F"));
        cell5.setCellValue(nazione);
        Cell cell6 = headerRow.createCell(CellReference.convertColStringToIndex("G"));
        cell6.setCellValue(dataEmissione);
        Cell cell7 = headerRow.createCell(CellReference.convertColStringToIndex("H"));
        cell7.setCellValue(luogoEmissione);
        Cell cell8 = headerRow.createCell(CellReference.convertColStringToIndex("I"));
        cell8.setCellValue(totaleEuro);
    }

    public static void createSummaryHeader(XSSFSheet ws) {
        Row headerRow = ws.createRow(0);
        Cell cell = headerRow.createCell(CellReference.convertColStringToIndex("A"));
        cell.setCellValue("TIPOLOGIA TICKET");
        Cell cell1 = headerRow.createCell(CellReference.convertColStringToIndex("B"));
        cell1.setCellValue("N. INGRESSI");
        Cell cell2 = headerRow.createCell(CellReference.convertColStringToIndex("C"));
        cell2.setCellValue("TOTALE (€)");
    }

    public static void createSummary(XSSFSheet ws, int rowNum, String tipologiaTicket, int nIngressi, double totaleEuro){
        Row headerRow = ws.createRow(rowNum);
        Cell cell = headerRow.createCell(CellReference.convertColStringToIndex("A"));
        cell.setCellValue(tipologiaTicket);
        Cell cell1 = headerRow.createCell(CellReference.convertColStringToIndex("B"));
        cell1.setCellValue(nIngressi);
        Cell cell2 = headerRow.createCell(CellReference.convertColStringToIndex("C"));
        cell2.setCellValue(totaleEuro);
    }


}
