package org.nish.kairos.tourapp.utils;

import com.google.common.io.ByteStreams;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfDiv;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.nish.kairos.tourapp.model.TicketStandard;
import org.nish.kairos.tourapp.model.TicketVirtual;
import org.nish.kairos.tourapp.model.TourOperator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

public class InvoiceGenerator {

    public static InputStream generateFattura(TicketStandard ticketStandard) throws DocumentException, IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, out);

        TourOperator tourOperator = ticketStandard.getTourOperator();
        Font intestazioneFont = new Font(Font.FontFamily.COURIER, 22, Font.BOLD);
        Font subIntestazioneFont = new Font(Font.FontFamily.COURIER, 8);
        Font headerTableFont = new Font(Font.FontFamily.COURIER, 14);
        Font corpoFont = new Font(Font.FontFamily.COURIER, 12);
        document.open();
        InputStream logoKairos = TicketGenerator.class.getClassLoader().getResourceAsStream("/img/logo-kairos.png");

        Image image = Image.getInstance(ByteStreams.toByteArray(logoKairos));
        image.scalePercent(50);
        Paragraph intestazione = new Paragraph();
        intestazione.add(new Paragraph("Via Nazionale snc C.da Mazzotta", subIntestazioneFont));
        intestazione.add(new Paragraph("Pizzo (VV) - 89812", subIntestazioneFont));
        intestazione.add(new Paragraph("Tel: +39 3920580111", subIntestazioneFont));
        intestazione.add(new Paragraph("Mail: kairospizzo@gmail.com", subIntestazioneFont));
        intestazione.add(new Paragraph("C.Fiscale e P.IVA: 03307480792", subIntestazioneFont));


        PdfDiv div = new PdfDiv();
        if(tourOperator != null){
            Paragraph p0 = new Paragraph("Spett.le " + tourOperator.getNome() + "", corpoFont);
            p0.setAlignment(Element.ALIGN_RIGHT);
            Paragraph p1 = new Paragraph(tourOperator.getSocieta(), corpoFont);
            p1.setAlignment(Element.ALIGN_RIGHT);
            Paragraph p2 = new Paragraph(tourOperator.getIndirizzo() + ", " + tourOperator.getCitta() + "(" + tourOperator.getCap() + ")", corpoFont);
            p2.setAlignment(Element.ALIGN_RIGHT);
            Paragraph p3 = new Paragraph(tourOperator.getProvincia() + " " + tourOperator.getTelefono(), corpoFont);
            p3.setAlignment(Element.ALIGN_RIGHT);
            Paragraph p4 = new Paragraph("P. IVA " + tourOperator.getPiva(), corpoFont);
            p4.setAlignment(Element.ALIGN_RIGHT);
            div.addElement(p0);
            div.addElement(p1);
            div.addElement(p2);
            div.addElement(p3);
            div.addElement(p4);
        }


        Paragraph corpo = new Paragraph();
        corpo.add(new Paragraph("FATTURA/INVOICE N.    DEL " + GeneralHelper.getDateFormatted(new Date()), corpoFont));

        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        PdfPCell cell0 = new PdfPCell();
        cell0.addElement(new Paragraph("DESCRIZIONE", headerTableFont));
        PdfPCell cell1 = new PdfPCell();
        cell1.addElement(new Paragraph("COSTO €", headerTableFont));

        PdfPCell cell2 = new PdfPCell();
        cell2.addElement(new Paragraph("% IVA/VAT", headerTableFont));

        PdfPCell cell3 = new PdfPCell();
        cell3.addElement(new Paragraph(" ", corpoFont));

        PdfPCell cell4 = new PdfPCell();
        cell4.addElement(new Paragraph(" ", corpoFont));

        PdfPCell cell5 = new PdfPCell();
        cell5.addElement(new Paragraph(" ", corpoFont));

        PdfPCell cell6 = new PdfPCell();
        cell6.addElement(new Paragraph(" ", corpoFont));

        PdfPCell cell7 = new PdfPCell();
        cell7.addElement(new Paragraph(" ", corpoFont));

        PdfPCell cell8 = new PdfPCell();
        cell8.addElement(new Paragraph(" ", corpoFont));

        PdfPCell cellX = new PdfPCell();
        cellX.addElement(new Paragraph(" ", corpoFont));

        PdfPCell cellY = new PdfPCell();
        cellY.addElement(new Paragraph(" ", corpoFont));

        PdfPCell cellZ = new PdfPCell();
        cellZ.addElement(new Paragraph(" ", corpoFont));

        PdfPCell cell9 = new PdfPCell();
        cell9.addElement(new Paragraph(" ", corpoFont));

        PdfPCell cell10 = new PdfPCell();
        cell10.addElement(new Paragraph("Totale Imponibile/Total", corpoFont));

        PdfPCell cell11 = new PdfPCell();
        cell11.addElement(new Paragraph(" ", corpoFont));

        PdfPCell cell12 = new PdfPCell();
        cell12.addElement(new Paragraph(" ", corpoFont));

        PdfPCell cell13 = new PdfPCell();
        cell13.addElement(new Paragraph("IVA/VAT 22%", corpoFont));

        PdfPCell cell14 = new PdfPCell();
        cell14.addElement(new Paragraph(" ", corpoFont));

        PdfPCell cell15 = new PdfPCell();
        cell15.addElement(new Paragraph(" ", corpoFont));

        PdfPCell cell16 = new PdfPCell();
        cell16.addElement(new Paragraph("TOTALE/TOTAL TO PAY", corpoFont));

        PdfPCell cell17 = new PdfPCell();
        cell17.addElement(new Paragraph(" ", corpoFont));

        PdfPCell cell18 = new PdfPCell();
        cell18.addElement(new Paragraph(" ", corpoFont));

        table.addCell(cell0);
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
        table.addCell(cell7);
        table.addCell(cell8);
        table.addCell(cellX);
        table.addCell(cellY);
        table.addCell(cellZ);
        table.addCell(cell9);
        table.addCell(cell10);
        table.addCell(cell11);
        table.addCell(cell12);
        table.addCell(cell13);
        table.addCell(cell14);
        table.addCell(cell15);
        table.addCell(cell16);
        table.addCell(cell17);
        table.addCell(cell18);

        Paragraph banca = new Paragraph("Modalità di pagamento: bonifico bancario Credem Spa Filiale di Pizzo (VV) IBAN IT12F0303242690010000002176", subIntestazioneFont);

        document.add(image);
        document.add(intestazione);
        document.add(new Paragraph(" "));

        document.add(div);
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));

        document.add(corpo);
        document.add(new Paragraph(" "));

        document.add(table);

        document.add(new Paragraph(" "));
        document.add(banca);
        document.close();


        InputStream is = new ByteArrayInputStream(out.toByteArray());
        return is;
    }

    public static InputStream generateFatturaVirtual(TicketVirtual ticketVirtual) throws DocumentException, IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, out);

        TourOperator tourOperator = ticketVirtual.getTourOperator();
        Font intestazioneFont = new Font(Font.FontFamily.COURIER, 22, Font.BOLD);
        Font subIntestazioneFont = new Font(Font.FontFamily.COURIER, 8);
        Font headerTableFont = new Font(Font.FontFamily.COURIER, 14);
        Font corpoFont = new Font(Font.FontFamily.COURIER, 12);
        document.open();
        InputStream logoKairos = TicketGenerator.class.getClassLoader().getResourceAsStream("/img/logo-kairos.png");

        Image image = Image.getInstance(ByteStreams.toByteArray(logoKairos));
        image.scalePercent(50);
        Paragraph intestazione = new Paragraph();
        intestazione.add(new Paragraph("Via Nazionale snc C.da Mazzotta", subIntestazioneFont));
        intestazione.add(new Paragraph("Pizzo (VV) - 89812", subIntestazioneFont));
        intestazione.add(new Paragraph("Tel: +39 3920580111", subIntestazioneFont));
        intestazione.add(new Paragraph("Mail: kairospizzo@gmail.com", subIntestazioneFont));
        intestazione.add(new Paragraph("C.Fiscale e P.IVA: 03307480792", subIntestazioneFont));


        PdfDiv div = new PdfDiv();
        if(tourOperator != null){
            Paragraph p0 = new Paragraph("Spett.le " + tourOperator.getNome() + "", corpoFont);
            p0.setAlignment(Element.ALIGN_RIGHT);
            Paragraph p1 = new Paragraph(tourOperator.getSocieta(), corpoFont);
            p1.setAlignment(Element.ALIGN_RIGHT);
            Paragraph p2 = new Paragraph(tourOperator.getIndirizzo() + ", " + tourOperator.getCitta() + "(" + tourOperator.getCap() + ")", corpoFont);
            p2.setAlignment(Element.ALIGN_RIGHT);
            Paragraph p3 = new Paragraph(tourOperator.getProvincia() + " " + tourOperator.getTelefono(), corpoFont);
            p3.setAlignment(Element.ALIGN_RIGHT);
            Paragraph p4 = new Paragraph("P. IVA " + tourOperator.getPiva(), corpoFont);
            p4.setAlignment(Element.ALIGN_RIGHT);
            div.addElement(p0);
            div.addElement(p1);
            div.addElement(p2);
            div.addElement(p3);
            div.addElement(p4);
        }


        Paragraph corpo = new Paragraph();
        corpo.add(new Paragraph("FATTURA/INVOICE N.    DEL " + GeneralHelper.getDateFormatted(new Date()), corpoFont));

        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        PdfPCell cell0 = new PdfPCell();
        cell0.addElement(new Paragraph("DESCRIZIONE", headerTableFont));
        PdfPCell cell1 = new PdfPCell();
        cell1.addElement(new Paragraph("COSTO €", headerTableFont));

        PdfPCell cell2 = new PdfPCell();
        cell2.addElement(new Paragraph("% IVA/VAT", headerTableFont));

        PdfPCell cell3 = new PdfPCell();
        cell3.addElement(new Paragraph(" ", corpoFont));

        PdfPCell cell4 = new PdfPCell();
        cell4.addElement(new Paragraph(" ", corpoFont));

        PdfPCell cell5 = new PdfPCell();
        cell5.addElement(new Paragraph(" ", corpoFont));

        PdfPCell cell6 = new PdfPCell();
        cell6.addElement(new Paragraph(" ", corpoFont));

        PdfPCell cell7 = new PdfPCell();
        cell7.addElement(new Paragraph(" ", corpoFont));

        PdfPCell cell8 = new PdfPCell();
        cell8.addElement(new Paragraph(" ", corpoFont));

        PdfPCell cellX = new PdfPCell();
        cellX.addElement(new Paragraph(" ", corpoFont));

        PdfPCell cellY = new PdfPCell();
        cellY.addElement(new Paragraph(" ", corpoFont));

        PdfPCell cellZ = new PdfPCell();
        cellZ.addElement(new Paragraph(" ", corpoFont));

        PdfPCell cell9 = new PdfPCell();
        cell9.addElement(new Paragraph(" ", corpoFont));

        PdfPCell cell10 = new PdfPCell();
        cell10.addElement(new Paragraph("Totale Imponibile/Total", corpoFont));

        PdfPCell cell11 = new PdfPCell();
        cell11.addElement(new Paragraph(" ", corpoFont));

        PdfPCell cell12 = new PdfPCell();
        cell12.addElement(new Paragraph(" ", corpoFont));

        PdfPCell cell13 = new PdfPCell();
        cell13.addElement(new Paragraph("IVA/VAT 22%", corpoFont));

        PdfPCell cell14 = new PdfPCell();
        cell14.addElement(new Paragraph(" ", corpoFont));

        PdfPCell cell15 = new PdfPCell();
        cell15.addElement(new Paragraph(" ", corpoFont));

        PdfPCell cell16 = new PdfPCell();
        cell16.addElement(new Paragraph("TOTALE/TOTAL TO PAY", corpoFont));

        PdfPCell cell17 = new PdfPCell();
        cell17.addElement(new Paragraph(" ", corpoFont));

        PdfPCell cell18 = new PdfPCell();
        cell18.addElement(new Paragraph(" ", corpoFont));

        table.addCell(cell0);
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
        table.addCell(cell7);
        table.addCell(cell8);
        table.addCell(cellX);
        table.addCell(cellY);
        table.addCell(cellZ);
        table.addCell(cell9);
        table.addCell(cell10);
        table.addCell(cell11);
        table.addCell(cell12);
        table.addCell(cell13);
        table.addCell(cell14);
        table.addCell(cell15);
        table.addCell(cell16);
        table.addCell(cell17);
        table.addCell(cell18);

        Paragraph banca = new Paragraph("Modalità di pagamento: bonifico bancario Credem Spa Filiale di Pizzo (VV) IBAN IT12F0303242690010000002176", subIntestazioneFont);

        document.add(image);
        document.add(intestazione);
        document.add(new Paragraph(" "));

        document.add(div);
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));

        document.add(corpo);
        document.add(new Paragraph(" "));

        document.add(table);

        document.add(new Paragraph(" "));
        document.add(banca);
        document.close();


        InputStream is = new ByteArrayInputStream(out.toByteArray());
        return is;
    }
}
