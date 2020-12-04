package org.nish.kairos.tourapp.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.nish.kairos.tourapp.model.Site;
import org.nish.kairos.tourapp.services.TicketStandardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.Inet4Address;
import java.util.List;

public class TicketGenerator {

    private static final Logger logger = LoggerFactory.getLogger(TicketGenerator.class);

    public static BufferedImage generateQRCodeImage(String barcodeText) throws Exception {
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix =
                barcodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, 190, 190);

        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    public static BufferedImage generateTicket(String ticketNo, int nIngressi, String dataEmissione, String ticketType, List<Site> siteList, String entityName) throws Exception {

        BufferedImage ticketBf = new BufferedImage(500, 200, BufferedImage.TYPE_INT_ARGB); // 350 x 250
        Graphics2D g2d = ticketBf.createGraphics();
        String linkToQr = null;

        if(entityName.equals("TicketStandard")) {
            linkToQr = System.getenv("BACKEND_SERVER_URL") + "/api/ts/ticket/" + ticketNo;
        }else{
            linkToQr = System.getenv("BACKEND_SERVER_URL") + "/#TO-DO";
        }
        BufferedImage qrBf = generateQRCodeImage(linkToQr);

        // Set Background
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, 500, 200);
        g2d.setColor(Color.orange);
        g2d.drawRect(1, 1, 497, 197);


        g2d.drawImage(qrBf, 10, 8, null);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Monospaced", Font.BOLD, 19));
        g2d.drawString("Ticket # " + ticketNo, 210, 35);

        g2d.setFont(new Font("Monospaced", Font.BOLD, 16));
        g2d.drawString(ticketType, 210, 65);
        g2d.setFont(new Font("Monospaced", Font.PLAIN, 16));
        g2d.drawString("Ingressi # " + nIngressi, 210, 85);
        g2d.drawString(dataEmissione, 210, 105);
        String sites = "";
        for(Site site: siteList){
            sites = sites + site.getNome().toUpperCase() + " ";
        }
        g2d.setFont(new Font("Monospaced", Font.PLAIN, 12));
        g2d.drawString(sites,  210, 125);
        g2d.setFont(new Font("Monospaced", Font.ITALIC, 10));
        g2d.drawString("Conservare per tutta la durata della visita",  210, 155);

        InputStream isComune = TicketGenerator.class.getClassLoader().getResourceAsStream("/img/logoComune.jpg");
        BufferedImage logoComune = ImageIO.read(isComune);
        InputStream is = TicketGenerator.class.getClassLoader().getResourceAsStream("/img/logo-kairos.png");
        BufferedImage logoImage = ImageIO.read(is);
        g2d.scale(0.50, 0.50);
        g2d.drawImage(logoComune, 635, 332, null);
        g2d.drawImage(logoImage, 690, 325, null);
        g2d.dispose();

        return ticketBf;
    }

    public static BufferedImage generateMultipleTickets(String ticketNo, int nIngressi, String dataEmissione, String ticketType, List<Site> siteList, String entityName) throws Exception {
        logger.info("Creating tickets - started.");
        BufferedImage bfMultipleTickets = new BufferedImage(500* nIngressi, 200, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bfMultipleTickets.createGraphics();
        int count = 0;
        for(int i=0; i< nIngressi; i++) {
            BufferedImage singleTicket = generateTicket(ticketNo, 1, dataEmissione, ticketType, siteList, entityName);
            g2d.drawImage(singleTicket, 500*count, 0, null);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            count++;
        }
        g2d.dispose();
        logger.info("Creating tickets - ended.");
        return bfMultipleTickets;

    }

}
