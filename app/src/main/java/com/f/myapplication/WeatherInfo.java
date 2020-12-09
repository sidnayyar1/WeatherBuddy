package com.f.myapplication;


import java.time.LocalDateTime;

public class WeatherInfo {
    private String title;
    private String description;
    private String Humidity;
    private String Pressor;
    private String Wind;
    private String latitude;
    private String longitude;
    private String cityname;
    private String countryname;
    private String winddeg;
    private String temp;
    private String skystatus;
    LocalDateTime localDateTime;

    public String getWinddeg() {
        return winddeg;
    }

    public void setWinddeg(String winddeg) {
        this.winddeg = winddeg;
    }

    public String getSkystatus() {
        return skystatus;
    }
    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }
    public void setSkystatus(String skystatus) {
        this.skystatus = skystatus;
    }
    public String getHumidity() {
        return Humidity;
    }

    public void setHumidity(String humidity) {
        Humidity = humidity;
    }

    public String getPressor() {
        return Pressor;
    }

    public void setPressor(String pressor) {
        Pressor = pressor;
    }

    public String getWind() {
        return Wind;
    }

    public void setWind(String wind) {
        Wind = wind;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getCountryname() {
        return countryname;
    }

    public void setCountryname(String countryname) {
        this.countryname = countryname;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    private String imgURL;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}
