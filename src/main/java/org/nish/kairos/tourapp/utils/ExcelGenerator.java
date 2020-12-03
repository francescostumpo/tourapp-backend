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
        cell2.setCellValue("SITI");
        Cell cell3 = headerRow.createCell(CellReference.convertColStringToIndex("D"));
        cell3.setCellValue("TOUR OPERATOR");
        Cell cell4 = headerRow.createCell(CellReference.convertColStringToIndex("E"));
        cell4.setCellValue("N. INGRESSI");
        Cell cell5 = headerRow.createCell(CellReference.convertColStringToIndex("F"));
        cell5.setCellValue("NAZIONE");
        Cell cell6 = headerRow.createCell(CellReference.convertColStringToIndex("G"));
        cell6.setCellValue("DATA EMISSIONE");
        Cell cell7 = headerRow.createCell(CellReference.convertColStringToIndex("H"));
        cell7.setCellValue("TOTALE (€)");
    }

    public static void createTsRow(XSSFSheet ws, int rowNum, String ticketId, String tipologiaTicket, String siti, String tourOperator, int nIngressi, String nazione, String dataEmissione, Double totaleEuro){
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
        cell7.setCellValue(totaleEuro);
    }
}
