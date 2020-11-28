package org.nish.kairos.tourapp.model;

import java.util.List;

public class TicketStandard {

    private String _id;
    private String _rev;
    private String ticketId;
    private TicketTipology tipologiaTicket;
    private List<Site> siti;
    private TourOperator tourOperator;
    private int nIngressi;
    private String nazione;
    private String dataEmissione;
    private double totaleEuro;

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

    public TicketTipology getTipologiaTicket() {
        return tipologiaTicket;
    }

    public void setTipologiaTicket(TicketTipology tipologiaTicket) {
        this.tipologiaTicket = tipologiaTicket;
    }

    public List<Site> getSiti() {
        return siti;
    }

    public void setSiti(List<Site> siti) {
        this.siti = siti;
    }

    public TourOperator getTourOperator() {
        return tourOperator;
    }

    public void setTourOperator(TourOperator tourOperator) {
        this.tourOperator = tourOperator;
    }

    public int getnIngressi() {
        return nIngressi;
    }

    public void setnIngressi(int nIngressi) {
        this.nIngressi = nIngressi;
    }

    public String getNazione() {
        return nazione;
    }

    public void setNazione(String nazione) {
        this.nazione = nazione;
    }

    public String getDataEmissione() {
        return dataEmissione;
    }

    public void setDataEmissione(String dataEmissione) {
        this.dataEmissione = dataEmissione;
    }

    public double getTotaleEuro() {
        return totaleEuro;
    }

    public void setTotaleEuro(double totaleEuro) {
        this.totaleEuro = totaleEuro;
    }


}
