package org.nish.kairos.tourapp.model;

import java.util.Date;
import java.util.List;

public class TicketVirtual {

    private String _id;
    private String _rev;
    private String ticketId;
    private TicketTipology tipologiaTicket;
    private List<Site> siti;
    private TourOperator tourOperator;
    private int nIngressi;
    private String luogoEmissione;
    private Date dataEmissione;
    private double totaleEuro;
    private String randomPassword;
    private String clientIp;

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

    public String getLuogoEmissione() {
        return luogoEmissione;
    }

    public void setLuogoEmissione(String luogoEmissione) {
        this.luogoEmissione = luogoEmissione;
    }

    public int getnIngressi() {
        return nIngressi;
    }

    public void setnIngressi(int nIngressi) {
        this.nIngressi = nIngressi;
    }

    public Date getDataEmissione() {
        return dataEmissione;
    }

    public void setDataEmissione(Date dataEmissione) {
        this.dataEmissione = dataEmissione;
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

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    @Override
    public String toString() {
        return "TicketVirtual{" +
                "_id='" + _id + '\'' +
                ", _rev='" + _rev + '\'' +
                ", ticketId='" + ticketId + '\'' +
                ", tipologiaTicket=" + tipologiaTicket +
                ", siti=" + siti +
                ", tourOperator=" + tourOperator +
                ", nIngressi=" + nIngressi +
                ", luogoEmissione='" + luogoEmissione + '\'' +
                ", dataEmissione=" + dataEmissione +
                ", totaleEuro=" + totaleEuro +
                ", randomPassword='" + randomPassword + '\'' +
                ", clientIp='" + clientIp + '\'' +
                '}';
    }
}
