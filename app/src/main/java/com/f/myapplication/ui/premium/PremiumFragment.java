package com.f.myapplication.ui.premium;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.f.myapplication.BackgroundJSONCall;
import com.f.myapplication.CardModel;
import com.f.myapplication.DataBaseHandler;
import com.f.myapplication.MainActivity;
import com.f.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PremiumFragment extends Fragment {

    private PremiumViewModel premiumViewModel;
    DataBaseHandler db;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_premium, container, false);
        final Button btnserach = root.findViewById(R.id.btnSearch);
        EditText city1 = (EditText) root.findViewById(R.id.txtCityName1);
        EditText city2 = (EditText) root.findViewById(R.id.txtCity2);

        btnserach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new DataBaseHandler(getActivity().getApplicationContext());
                CardModel objModel = db.getCard();
                if(objModel.getID()>0) {
                    if ("".equalsIgnoreCase(city1.getText().toString().trim()) || "".equalsIgnoreCase(city2.getText().toString().trim())) {
                        Toast.makeText(root.getContext(), "Both Two City Name is Required", Toast.LENGTH_SHORT).show();

                    } else {
                        BackgroundJSONCall b = new BackgroundJSONCall(root, getActivity());
                        b.AssignCity(city1.getText().toString().trim(), city2.getText().toString().trim());
                        b.execute(0.0, 0.0, 1.1);

                    }
                }
                else
                {
                    Toast.makeText(root.getContext(), "Please registered your Card ", Toast.LENGTH_SHORT).show();
                }

            }
        });


        return root;
    }
}