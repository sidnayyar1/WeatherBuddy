package com.f.myapplication;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

public class BackgroundJSONCall extends AsyncTask<Double, Void, Void> {

    private Double currentLatitude, currentLongitude,arg3;
    private View v;
    private Activity act;
    private String city;
    private String city2;
    private boolean isCitySearch=false;
    BackgroundJSONCall(){}
    public BackgroundJSONCall(View v, Activity act) {
        this.v = v;
        this.act=act;
        isCitySearch=false;
    }
    public void AssignCity(String city){
        isCitySearch=true;
        this.city=city;
    }
    public void AssignCity(String city,String city2){
        isCitySearch=true;
        this.city=city;
        this.city2=city2;

    }
    @Override
    protected Void doInBackground(Double... arg0) {
            currentLatitude = arg0[0];
            currentLongitude = arg0[1];

            if(arg0.length==3)

            {
                isCitySearch=true;
                jsonfn();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                jsonfn2();
            }
            else
            {
                jsonfn();

            }
            return null;
    }
    WeatherInfo objModel=null;
    WeatherInfo objModel2=null;
    private void jsonfn2() {
        objModel2=new WeatherInfo();
        HttpHandler sh2 = new HttpHandler();
        String url2=  "https://api.openweathermap.org/data/2.5/weather?q="+city2+"&appid=db79f5c5b1bf48211f2f082ff1c24fac&units=metric";

        System.out.println(url2);
        String jsonStr2 = sh2.makeServiceCall(url2);

        if (jsonStr2 != null) {
            try {



                JSONObject fJSONObject2 = new JSONObject(jsonStr2);
                JSONArray  weather = (JSONArray ) fJSONObject2.get("weather");
                JSONObject wind = (JSONObject) fJSONObject2.get("wind");
                JSONObject coord = (JSONObject) fJSONObject2.get("coord");
                JSONObject main = (JSONObject) fJSONObject2.get("main");
                JSONObject sys= (JSONObject) fJSONObject2.get("sys");
                objModel2.setLatitude(coord.get("lat").toString());
                objModel2.setLongitude(coord.get("lon").toString());

                objModel2.setCityname(fJSONObject2.get("name").toString());
                objModel2.setCountryname(sys.get("country").toString());
                objModel2.setSkystatus(((JSONObject)weather.get(0)).get("main").toString());
                objModel2.setDescription(((JSONObject)weather.get(0)).get("description").toString());
                objModel2.setImgURL("http://openweathermap.org/img/wn/"+ ((JSONObject)weather.get(0)).get("icon").toString()+"@2x.png");
                objModel2.setTemp(main.get("temp").toString());
                objModel2.setPressor(main.get("pressure").toString());
                objModel2.setHumidity(main.get("humidity").toString());
                objModel2.setWind(wind.get("speed").toString());
                objModel2.setWinddeg(wind.get("deg").toString());

                final TextView tempView2 = (TextView) v.findViewById(R.id.temp_text_view2);
                final TextView humView2 = (TextView) v.findViewById(R.id.humidity_text_view2);
                final TextView txtskystatus2=(TextView)v.findViewById(R.id.txtskystatus2);
                final TextView descView2 = (TextView) v.findViewById(R.id.descView2);
                final TextView latView2 = (TextView) v.findViewById(R.id.latView2);
                final TextView longView2 = (TextView) v.findViewById(R.id.longView2);
                final TextView txtpressure2=(TextView)v.findViewById(R.id.txtpressure2);
                final TextView txtCityName2 = (TextView) v.findViewById(R.id.txtCityName2);
                final TextView txtwindspeed2=(TextView)v.findViewById(R.id.windspeed2);
                final ImageView imageViewCloud2 = (ImageView)v.findViewById(R.id.imageViewCloud2);

                r2 = new Runnable() {
                    @Override
                    public void run() {
                        tempView2.setText(objModel2.getTemp()+"\u2103");  //F="\u2109" ℃="\u2103"
                        txtCityName2.setText(objModel2.getCityname()+", "+objModel2.getCountryname());
                        txtskystatus2.setText(objModel2.getSkystatus());
                        humView2.setText("Humidity: "+objModel2.getHumidity() +" %");
                        txtpressure2.setText("Pressor: "+objModel2.getPressor());
                        txtwindspeed2.setText("Wind: "+objModel2.getWind() +" Km/h");
                        descView2.setText(objModel2.getDescription());
                        latView2.setText(objModel2.getLatitude()+" (lat)");
                        longView2.setText(objModel2.getLongitude()+" (lon)");
                        Picasso.get().load(objModel2.getImgURL()).into(imageViewCloud2);

                    }
                };



            } catch (Exception e) {

                if(act!=null)
                    act.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(act.getApplicationContext(),"No weather data available",Toast.LENGTH_LONG).show();
                        }
                    });
                e.printStackTrace();
            }
        }
        else
        {
            System.out.println("Couldnt get json response");
        }
    }
//https://home.openweathermap.org/api_keys  = db79f5c5b1bf48211f2f082ff1c24fac


    Runnable r,r2;
    private void jsonfn() {
        objModel=new WeatherInfo();
        HttpHandler sh = new HttpHandler();
        String url="";
        if(!isCitySearch) {
            System.out.println("\n" + currentLatitude + "\n" + currentLongitude);

            url = "https://api.openweathermap.org/data/2.5/weather?lat=" + currentLatitude + "&lon=" + currentLongitude + "&appid=db79f5c5b1bf48211f2f082ff1c24fac&units=metric";
        }
        else
        {
            url = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=db79f5c5b1bf48211f2f082ff1c24fac&units=metric";
        }
        System.out.println(url);
        String jsonStr = sh.makeServiceCall(url);

        if (jsonStr != null) {
            try {

               JSONObject fJSONObject = new JSONObject(jsonStr);
                JSONArray  weather = (JSONArray ) fJSONObject.get("weather");
                JSONObject wind = (JSONObject) fJSONObject.get("wind");
                JSONObject coord = (JSONObject) fJSONObject.get("coord");
                JSONObject main = (JSONObject) fJSONObject.get("main");
                JSONObject sys= (JSONObject) fJSONObject.get("sys");
                objModel.setLatitude(coord.get("lat").toString());
                objModel.setLongitude(coord.get("lon").toString());

                 objModel.setCityname(fJSONObject.get("name").toString());
                objModel.setCountryname(sys.get("country").toString());
                objModel.setSkystatus(((JSONObject)weather.get(0)).get("main").toString());
                objModel.setDescription(((JSONObject)weather.get(0)).get("description").toString());
                objModel.setImgURL("http://openweathermap.org/img/wn/"+ ((JSONObject)weather.get(0)).get("icon").toString()+"@2x.png");
                objModel.setTemp(main.get("temp").toString());
                objModel.setPressor(main.get("pressure").toString());
                 objModel.setHumidity(main.get("humidity").toString());
                objModel.setWind(wind.get("speed").toString());
                objModel.setWinddeg(wind.get("deg").toString());

                final TextView tempView = (TextView) v.findViewById(R.id.temp_text_view);
               final TextView humView = (TextView) v.findViewById(R.id.humidity_text_view);
               final TextView txtskystatus=(TextView)v.findViewById(R.id.txtskystatus);
               final TextView descView = (TextView) v.findViewById(R.id.descView);
                final TextView latView = (TextView) v.findViewById(R.id.latView);
               final TextView longView = (TextView) v.findViewById(R.id.longView);
               final TextView txtpressure=(TextView)v.findViewById(R.id.txtpressure);
               final TextView txtCityName = (TextView) v.findViewById(R.id.txtCityName);
                final TextView txtwindspeed=(TextView)v.findViewById(R.id.windspeed);
                final ImageView imageViewCloud = (ImageView)v.findViewById(R.id.imageViewCloud);

                r = new Runnable() {
                    @Override
                    public void run() {
                        tempView.setText(objModel.getTemp()+"\u2103");  //F="\u2109" ℃="\u2103"
                        txtCityName.setText(objModel.getCityname()+", "+objModel.getCountryname());
                        txtskystatus.setText(objModel.getSkystatus());
                        humView.setText("Humidity: "+objModel.getHumidity() +" %");
                        txtpressure.setText("Pressor: "+objModel.getPressor());
                        txtwindspeed.setText("Wind: "+objModel.getWind() +" Km/h");
                        descView.setText(objModel.getDescription());
                        latView.setText(objModel.getLatitude()+" (lat)");
                        longView.setText(objModel.getLongitude()+" (lon)");
                        Picasso.get().load(objModel.getImgURL()).into(imageViewCloud);

                    }
                };



            } catch (Exception e) {

                if(act!=null)
                    act.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(act.getApplicationContext(),"No weather data available",Toast.LENGTH_LONG).show();
                        }
                    });
                e.printStackTrace();
            }
        }
        else
        {
            System.out.println("Couldnt get json response");
        }

    }
    @Override
    protected void onPostExecute(Void aVoid) {

        try {
            if(r!=null)
                act.runOnUiThread(r);
            if(r2!=null)
                act.runOnUiThread(r2);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        super.onPostExecute(aVoid);

    }

}


