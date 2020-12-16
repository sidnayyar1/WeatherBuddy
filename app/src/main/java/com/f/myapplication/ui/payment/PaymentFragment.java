package com.f.myapplication.ui.payment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.f.myapplication.CardModel;
import com.f.myapplication.DataBaseHandler;
import com.f.myapplication.ProfileModel;
import com.f.myapplication.R;


public class PaymentFragment extends Fragment {

    private PaymentViewModel paymentViewModel;
    DataBaseHandler db;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        paymentViewModel =
                new ViewModelProvider(this).get(PaymentViewModel.class);
        View root = inflater.inflate(R.layout.payment_fragment, container, false);
        final TextView txtCardNumber = root.findViewById(R.id.txtCardNumber);
        final TextView txtExpirydate = root.findViewById(R.id.txtExpirydate);
        final ToggleButton btnSave = (ToggleButton)root.findViewById(R.id.btnSave);
        db = new DataBaseHandler(getActivity().getApplicationContext());
        CardModel objModel = db.getCard();
        txtCardNumber.setText(objModel.get_carno());
        txtExpirydate.setText(objModel.get_expirydate());
        btnSave.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                objModel.set_carno(txtCardNumber.getText().toString().trim());
                objModel.set_expirydate(txtExpirydate.getText().toString().trim());


                if (isChecked) {
                    txtCardNumber.setEnabled(true);
                    txtExpirydate.setEnabled(true);
                    btnSave.setChecked(true);
                } else {
                    if (isValid( txtCardNumber.getText().toString().trim()) && validateCardExpiryDate( txtExpirydate.getText().toString().trim()))
                    {
                        txtCardNumber.setEnabled(false);
                    txtExpirydate.setEnabled(false);
                    btnSave.setChecked(false);
                    if (objModel.getID() <= 0) {
                        objModel.setID(db.addCard(new CardModel(objModel.getID(), objModel.get_carno(), objModel.get_cardname(), objModel.get_cardtype(), objModel.get_expirydate())));
                        Toast.makeText(getActivity().getApplicationContext(), "successfully saved", Toast.LENGTH_SHORT).show();
                    } else {
                        db.updateCard(new CardModel(objModel.getID(), objModel.get_carno(), objModel.get_cardname(), objModel.get_cardtype(), objModel.get_expirydate()));
                        Toast.makeText(getActivity().getApplicationContext(), "successfully Updated", Toast.LENGTH_SHORT).show();
                    }
                }
                    else
                    {
                        Toast.makeText(getActivity().getApplicationContext(), "Invalid Card Number or Expiry date", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });
        return root;
    }
    boolean validateCardExpiryDate(String expiryDate) {
        return expiryDate.matches("(?:0[1-9]|1[0-2])/[0-9]{2}");
    }

    //link for code https://www.geeksforgeeks.org/program-credit-card-number-validation/
    // Return true if the card number is valid

    public static boolean isValid(String numbernumber)
    {
        Long  number=0L;
        try {
              number = Long.parseLong(numbernumber);
        }
        catch (Exception ex)
        {
            return false;
        }

        return (getSize(number) >= 13 &&
                getSize(number) <= 16) &&
                (prefixMatched(number, 4) ||
                        prefixMatched(number, 5) ||
                        prefixMatched(number, 37) ||
                        prefixMatched(number, 6)) &&
                ((sumOfDoubleEvenPlace(number) +
                        sumOfOddPlace(number)) % 10 == 0);
    }

    // Get the result from Step 2
    public static int sumOfDoubleEvenPlace(long number)
    {
        int sum = 0;
        String num = number + "";
        for (int i = getSize(number) - 2; i >= 0; i -= 2)
            sum += getDigit(Integer.parseInt(num.charAt(i) + "") * 2);

        return sum;
    }

    // Return this number if it is a single digit, otherwise,
    // return the sum of the two digits
    public static int getDigit(int number)
    {
        if (number < 9)
            return number;
        return number / 10 + number % 10;
    }

    // Return sum of odd-place digits in number
    public static int sumOfOddPlace(long number)
    {
        int sum = 0;
        String num = number + "";
        for (int i = getSize(number) - 1; i >= 0; i -= 2)
            sum += Integer.parseInt(num.charAt(i) + "");
        return sum;
    }

    // Return true if the digit d is a prefix for number
    public static boolean prefixMatched(long number, int d)
    {
        return getPrefix(number, getSize(d)) == d;
    }

    // Return the number of digits in d
    public static int getSize(long d)
    {
        String num = d + "";
        return num.length();
    }

    // Return the first k number of digits from
    // number. If the number of digits in number
    // is less than k, return number.
    public static long getPrefix(long number, int k)
    {
        if (getSize(number) > k) {
            String num = number + "";
            return Long.parseLong(num.substring(0, k));
        }
        return number;
    }
}