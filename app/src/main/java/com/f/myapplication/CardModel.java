package com.f.myapplication;

public class CardModel
{
    // private variables
    int _id;
    String _cardno;
    String _cardname;
    String _cardtype;
    String _expirydate;

    // Empty constructor
    public CardModel() { }
    // constructor
    public CardModel(int keyId, String cardno, String cardname, String cardtype,String expirydate) {
        this._id = keyId;
        this._cardno = cardno;
        this._cardname = cardname;
        this._cardtype = cardtype;
        this._expirydate = expirydate;
    }
    public CardModel(String cardno, String cardname, String cardtype,String expirydate) {
        this._cardno = cardno;
        this._cardname = cardname;
        this._cardtype = cardtype;
        this._expirydate = expirydate;
    }

    public CardModel(int keyId) {
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
    public String get_carno() {
        return _cardno;
    }

    public void set_carno(String _carno) {
        this._cardno = _carno;
    }

    public String get_cardname() {
        return _cardname;
    }

    public void set_cardname(String _cardname) {
        this._cardname = _cardname;
    }

    public String get_cardtype() {
        return _cardtype;
    }

    public void set_cardtype(String _cardtype) {
        this._cardtype = _cardtype;
    }

    public String get_expirydate() {
        return _expirydate;
    }

    public void set_expirydate(String _expirydate) {
        this._expirydate = _expirydate;
    }
}