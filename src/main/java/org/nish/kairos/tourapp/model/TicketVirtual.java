package org.nish.kairos.tourapp.model;

import java.util.Date;
import java.util.List;

public class TicketVirtual {

    private String _id;
    private String _rev;
    private String ticketId;
    private String luogoEmissione;
    private String luogoAttivazione;
    private String dataAttivazione;
    private TicketTipology ticketTipology;
    private TourOperator tourOperator;
    private double totaleEuro;
    private String randomPassword;
    private boolean accessoEffettuato;
    private boolean attivato;
    private String cookieValue;
    private String nazione;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_rev() {
        return _rev;
    }

    public void set_rev(String _rev) {
        this._rev = _rev;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getLuogoEmissione() {
        return luogoEmissione;
    }

    public void setLuogoEmissione(String luogoEmissione) {
        this.luogoEmissione = luogoEmissione;
    }

    public String getLuogoAttivazione() {
        return luogoAttivazione;
    }

    public void setLuogoAttivazione(String luogoAttivazione) {
        this.luogoAttivazione = luogoAttivazione;
    }

    public String getDataAttivazione() {
        return dataAttivazione;
    }

    public void setDataAttivazione(String dataAttivazione) {
        this.dataAttivazione = dataAttivazione;
    }

    public TicketTipology getTicketTipology() {
        return ticketTipology;
    }

    public void setTicketTipology(TicketTipology ticketTipology) {
        this.ticketTipology = ticketTipology;
    }

    public TourOperator getTourOperator() {
        return tourOperator;
    }

    public void setTourOperator(TourOperator tourOperator) {
        this.tourOperator = tourOperator;
    }

    public double getTotaleEuro() {
        return totaleEuro;
    }

    public void setTotaleEuro(double totaleEuro) {
        this.totaleEuro = totaleEuro;
    }

    public String getRandomPassword() {
        return randomPassword;
    }

    public void setRandomPassword(String randomPassword) {
        this.randomPassword = randomPassword;
    }

    public boolean isAccessoEffettuato() {
        return accessoEffettuato;
    }

    public void setAccessoEffettuato(boolean accessoEffettuato) {
        this.accessoEffettuato = accessoEffettuato;
    }

    public boolean isAttivato() {
        return attivato;
    }

    public void setAttivato(boolean attivato) {
        this.attivato = attivato;
    }

    public String getCookieValue() {
        return cookieValue;
    }

    public void setCookieValue(String cookieValue) {
        this.cookieValue = cookieValue;
    }

    public String getNazione() {
        return nazione;
    }

    public void setNazione(String nazione) {
        this.nazione = nazione;
    }

    @Override
    public String toString() {
        return "TicketVirtual{" +
                "_id='" + _id + '\'' +
                ", _rev='" + _rev + '\'' +
                ", ticketId='" + ticketId + '\'' +
                ", luogoEmissione='" + luogoEmissione + '\'' +
                ", luogoAttivazione='" + luogoAttivazione + '\'' +
                ", dataAttivazione='" + dataAttivazione + '\'' +
                ", ticketTipology=" + ticketTipology +
                ", tourOperator=" + tourOperator +
                ", totaleEuro=" + totaleEuro +
                ", randomPassword='" + randomPassword + '\'' +
                ", accessoEffettuato=" + accessoEffettuato +
                ", attivato=" + attivato +
                ", cookieValue='" + cookieValue + '\'' +
                ", nazione='" + nazione + '\'' +
                '}';
    }
}
