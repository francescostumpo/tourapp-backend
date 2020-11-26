package org.nish.kairos.tourapp.model;


import com.fasterxml.jackson.databind.util.JSONPObject;

public class Test {

    private String _id;
    private String _rev;
    private Response test;

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

    public Response getTest() {
        return test;
    }

    public void setTest(Response test) {
        this.test = test;
    }

    @Override
    public String toString() {
        return "Test{" +
                "_id='" + _id + '\'' +
                ", _rev='" + _rev + '\'' +
                ", test='" + test + '\'' +
                '}';
    }
}
