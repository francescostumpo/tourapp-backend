package org.nish.kairos.tourapp.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TicketGenerator {

    @GET
    @Path("/getQRCodeOLD")
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public static Response getQRCode() throws Exception{
        BufferedImage bf = generateTicket("ufoqoeufbeogwoneif");
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(bf, "png", os);                          // Passing: ​(RenderedImage im, String formatName, OutputStream output)
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        InputStreamResource re = new InputStreamResource(is);

        return Response.ok(is).build();
    }

    @GetMapping(value= "/generateTicket/getQRCode", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<InputStream> getSingleQRCode() throws Exception{

        BufferedImage bf = generateTicket("owvbnviuwbriv");
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(bf, "png", os);                          // Passing: ​(RenderedImage im, String formatName, OutputStream output)
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        return new ResponseEntity<>(is, HttpStatus.OK);
    }

    @GetMapping(value= "/generateMultipleTickets", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<InputStream> generateMultipleTicketsIs() throws Exception{

        BufferedImage bf = generateMultipleTickets();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(bf, "png", os);                          // Passing: ​(RenderedImage im, String formatName, OutputStream output)
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        InputStreamResource re = new InputStreamResource(is);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("image/png"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Tickets.png\"")
                .body(is);
    }

    public static BufferedImage generateQRCodeImage(String barcodeText) throws Exception {
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix =
                barcodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, 190, 190);

        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }


    public static BufferedImage generateTicket(String ticketNo) throws Exception {

        BufferedImage ticketBf = new BufferedImage(500, 200, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = ticketBf.createGraphics();
        String linkToQr = "http://localhost:8080/tickets/" + ticketNo;
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
        g2d.drawString("Ticket # " + ticketNo, 210, 45);

        g2d.setFont(new Font("Monospaced", Font.BOLD, 16));
        g2d.drawString("Intero", 210, 95);
        g2d.setFont(new Font("Monospaced", Font.PLAIN, 16));
        g2d.drawString("Castello Murat", 210, 115);
        g2d.drawString("14/11/2020 12:00", 210, 135);

        // Putting Logo

        InputStream is = TicketGenerator.class.getClassLoader().getResourceAsStream("/img/logo-kairos.png");
        BufferedImage logoImage = ImageIO.read(is);
        g2d.scale(0.50, 0.50);
        g2d.drawImage(logoImage, 690, 325, null);
        g2d.dispose();

        return ticketBf;
    }

    public BufferedImage generateMultipleTickets() throws Exception {

        List<String> ticketsList = new ArrayList<>();
        ticketsList.add("123456798");
        ticketsList.add("987654321");
        ticketsList.add("654321987");
        ticketsList.add("123456798");
        ticketsList.add("987654321");
        ticketsList.add("654321987");

        BufferedImage bfMultipleTickets = new BufferedImage(500* ticketsList.size(), 200, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bfMultipleTickets.createGraphics();
        int count = 0;
        for(String ticket: ticketsList) {
            System.out.println("Cycling, ticketNo: " + ticket);
            BufferedImage singleTicket = generateTicket(ticket);
            g2d.drawImage(singleTicket, 500*count, 0, null);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            count++;

        }

        g2d.dispose();
        return bfMultipleTickets;

    }

    /*@Bean
    public HttpMessageConverter<BufferedImage> createImageHttpMessageConverter() {
        return new BufferedImageHttpMessageConverter();
    }*/
}
