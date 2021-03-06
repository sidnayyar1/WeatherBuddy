package com.f.myapplication;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

//import static com.f.myapplication.MainActivity.PlaceholderFragment.wList;
//import static com.f.myapplication.MainActivity.PlaceholderFragment.wAdapter;


public class BackgroundForecast extends AsyncTask<Double, Void, Void> {

    Double currentLatitude, currentLongitude;
    Activity act;
    String city;
    public BackgroundForecast(Activity act, String city)
    {
        this.act=act;this.city=city;
    }
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {

               // wAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    protected Void doInBackground(Double... arg0) {

        currentLatitude = arg0[0];
        currentLongitude = arg0[1];
        try {
            fn();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void fn() {
        HttpHandler sh = new HttpHandler();
        System.out.println("\n" + currentLatitude + "\n" + currentLongitude);
        String url="";
        if(city==null)
            url = "http://api.wunderground.com/api/8a655346344b1471/forecast/q/z" + currentLatitude + "," + currentLongitude + ".json";
        else
            url = "http://api.wunderground.com/api/8a655346344b1471/forecast/q/"+city+".json";
        System.out.println(url);
        String jsonStr = sh.makeServiceCall(url);

        if (jsonStr != null && ((currentLatitude!=0&&currentLongitude!=0)||city!=null)) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                JSONObject forecast = (JSONObject) jsonObj.get("forecast");
                JSONObject TxtForecast = (JSONObject) forecast.get("txt_forecast");
                JSONArray ForecastDay = (JSONArray) TxtForecast.get("forecastday");
                System.out.println("txtforecast" + TxtForecast.toString());
                System.out.println("Forecastday" + ForecastDay.toString());
                String title, description, imgURL;
                for (int i = 0; i < ForecastDay.length(); i++) {
                    System.out.println("i=" + i);
                    JSONObject j = (JSONObject) ForecastDay.get(i);
                    title = j.get("title").toString();
                    description = j.get("fcttext").toString();
                    imgURL = j.get("icon_url").toString();
                    WeatherInfo w = new WeatherInfo();
                    w.setTitle(title);
                    w.setDescription(description);
                    w.setImgURL(imgURL);
                  //  wList.add(w);
                }

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
    }
}