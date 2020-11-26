package org.nish.kairos.tourapp.model;

public class Site {

    private String _id;
    private String _rev;
    private String nome;
    private String descrizioneBreve;

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

    public String getDescrizioneBreve() {
        return descrizioneBreve;
    }

    public void setDescrizioneBreve(String descrizioneBreve) {
        this.descrizioneBreve = descrizioneBreve;
    }

    @Override
    public String toString() {
        return "Site{" +
                "_id='" + _id + '\'' +
                ", _rev='" + _rev + '\'' +
                ", nome='" + nome + '\'' +
                ", descrizioneBreve='" + descrizioneBreve + '\'' +
                '}';
    }
}
