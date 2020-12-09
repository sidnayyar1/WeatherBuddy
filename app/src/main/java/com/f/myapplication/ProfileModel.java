package com.f.myapplication;

public class ProfileModel
{

    // private variables
    int _id;
    String _name;
    String _lname;
    byte[] _image;

    // Empty constructor
    public ProfileModel() {

    }

    // constructor
    public ProfileModel(int keyId, String name,String lname, byte[] image) {
        this._id = keyId;
        this._name = name;
        this._lname = lname;
        this._image = image;

    }
    public ProfileModel(String name,String lname, byte[] image) {
        this._name = name;
        this._lname = lname;
        this._image = image;

    }

    public ProfileModel(int keyId) {
        this._id = keyId;

    }

    // getting ID
    public int getID() {
        return this._id;
    }

    // setting id
    public void setID(int keyId) {
        this._id = keyId;
    }

    // getting name
    public String getName() {
        return this._name;
    }

    // setting name
    public void setName(String name) {
        this._name = name;
    }
    public String getLname() {
        return _lname;
    }

    public void setLname(String _lname) {
        this._lname = _lname;
    }

    // getting image
    public byte[] getImage() {
        return this._image;
    }

    // setting image
    public void setImage(byte[] image) {
        this._image = image;
    }
}