package com.f.myapplication.ui.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.f.myapplication.BackgroundForecast;
import com.f.myapplication.BackgroundJSONCall;
import com.f.myapplication.GPSTracker;
import com.f.myapplication.R;
import com.f.myapplication.WeatherAdapter;
import com.f.myapplication.WeatherInfo;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    public static WeatherAdapter wAdapter;
    public static List<WeatherInfo> wList = new ArrayList<>();

    private HomeViewModel homeViewModel;
    Double currentLongitude=0.0,currentLatitude=0.0;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        GPSTracker gps = new GPSTracker(root.getContext());
        if(gps.getLastLocation()==null)
        {
            Toast.makeText(getActivity().getApplicationContext(),"Location unavailable",Toast.LENGTH_SHORT).show();

        }
        else {
            currentLongitude = gps.getLastLocation().getLongitude();
            currentLatitude = gps.getLastLocation().getLatitude();
            if (currentLatitude != 0 && currentLongitude != 0) {
                BackgroundJSONCall b = new BackgroundJSONCall(root, getActivity());
                b.execute(currentLatitude, currentLongitude);

            }
            Toast.makeText(getActivity().getApplicationContext(), currentLongitude + " , " + currentLatitude, Toast.LENGTH_SHORT).show();
        }

        try {

//            if (ActivityCompat.checkSelfPermission(this.getContext(),
//                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {


                recyclerView = root.findViewById(R.id.recycler_view);
                wAdapter = new WeatherAdapter(wList, root.getContext());
                LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext(),LinearLayoutManager.VERTICAL,false);
                if(recyclerView==null)
                    System.out.println("recyclerview is null");
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(wAdapter);
                BackgroundForecast bf = new BackgroundForecast(this.getActivity(),null);
                bf.execute(currentLatitude, currentLongitude);
                System.out.println("wlist="+wList.toString());

          //  }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return root;
    }
}