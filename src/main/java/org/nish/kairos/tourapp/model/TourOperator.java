package org.nish.kairos.tourapp.model;

public class TourOperator {

    private String _id;
    private String _rev;
    private String nome;
    private String citta;
    private String telefono;
    private String societa;
    private String provincia;
    private String indirizzo;
    private int cap;
    private String piva;

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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getSocieta() {
        return societa;
    }

    public void setSocieta(String societa) {
        this.societa = societa;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public int getCap() {
        return cap;
    }

    public void setCap(int cap) {
        this.cap = cap;
    }

    public String getPiva() {
        return piva;
    }

    public void setPiva(String piva) {
        this.piva = piva;
    }

    @Override
    public String toString() {
        return "TourOperator{" +
                "_id='" + _id + '\'' +
                ", _rev='" + _rev + '\'' +
                ", nome='" + nome + '\'' +
                ", citta='" + citta + '\'' +
                ", telefono='" + telefono + '\'' +
                ", societa='" + societa + '\'' +
                ", provincia='" + provincia + '\'' +
                ", indirizzo='" + indirizzo + '\'' +
                ", cap=" + cap +
                ", piva='" + piva + '\'' +
                '}';
    }

}
