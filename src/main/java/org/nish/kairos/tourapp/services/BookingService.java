package org.nish.kairos.tourapp.services;

import org.nish.kairos.tourapp.managers.DbManager;
import org.nish.kairos.tourapp.model.Booking;
import org.nish.kairos.tourapp.model.Site;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.awt.print.Book;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class BookingService {

    private static final String ENTITY_NAME = "Booking";
    private static final String DB_NAME = "bookings";
    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);

    public boolean createOrUpdateBooking(Booking booking) {
        logger.info("Creating/Updating Booking: " + booking.getTitle());
        if(DbManager.createOrUpdateCloudantDoc(DB_NAME, booking) != null) return true;
        return false;
    }

    public Booking getBooking(String bookingId){
        logger.info("Retrieving Booking with ID: " + bookingId);
        return (Booking) DbManager.getCloudantDoc(DB_NAME, bookingId, ENTITY_NAME);
    }

    public boolean deleteBooking(Booking booking){
        String _id = booking.get_id();
        String _rev = booking.get_rev();
        logger.info("Deleting Booking with ID: " + _id + "and REV: " + _rev);
        return DbManager.deleteCloudantDoc(DB_NAME, _id, _rev);
    }

    public List<Booking> getAllBookings(){
        List<Booking> bookingsList = (List<Booking>) DbManager.getAllCloudantDocs(DB_NAME, ENTITY_NAME);
        Collections.sort(bookingsList);
        return bookingsList;
    }
}
