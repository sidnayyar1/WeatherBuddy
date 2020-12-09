package com.f.myapplication.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.f.myapplication.DataBaseHandler;
import com.f.myapplication.ProfileModel;
import com.f.myapplication.R;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static android.hardware.SensorManager.getOrientation;

public class ProfileFragment extends Fragment {

    private static Bitmap Image = null;
    private static Bitmap rotateImage = null;
    private ImageView imageView = null;
    DataBaseHandler db;
    private static final int GALLERY = 1;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);
          TextView txtFirstName = root.findViewById(R.id.txtFirstName);
          TextView txtLastName = root.findViewById(R.id.txtLastName);
         imageView= root.findViewById(R.id.profileImg);
        imageView.setClickable(false);
        db = new DataBaseHandler(getActivity().getApplicationContext());
        /**
         * Reading and getting all records from database
         */

         ProfileModel  objModel = db.getProfile();
        txtFirstName.setText(objModel.getName());
        txtLastName.setText(objModel.getLname());
        if (objModel.getImage() != null )
        {
            if(objModel.getImage().length> 0)
            {
                ByteArrayInputStream imageStream = new ByteArrayInputStream(objModel.getImage());
                Bitmap theImage = BitmapFactory.decodeStream(imageStream);
                imageView.setImageBitmap(theImage);
            }
        }


        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                imageView.setImageBitmap(null);
                if (Image != null)
                    Image.recycle();
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY);

                return false;
            }
        });

        final ToggleButton btnSave = (ToggleButton)root.findViewById(R.id.btnSave);
        btnSave.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    txtFirstName.setEnabled(true);
                    txtLastName.setEnabled(true);
                    btnSave.setChecked(true);
                    imageView.setClickable(true);
                } else {
                    if(firstName(txtFirstName.getText().toString().trim()) && lastName(txtLastName.getText().toString().trim())) {
                        txtFirstName.setEnabled(false);
                        txtLastName.setEnabled(false);
                        imageView.setClickable(false);
                        btnSave.setChecked(false);
                        objModel.setName(txtFirstName.getText().toString().trim());
                        objModel.setLname(txtLastName.getText().toString().trim());

                        if (objModel.getID() <= 0) {

                            objModel.setID(db.addProfile(new ProfileModel(objModel.getID(), objModel.getName(), objModel.getLname(), objModel.getImage())));
                            Toast.makeText(getActivity().getApplicationContext(), "successfully saved", Toast.LENGTH_SHORT).show();

                        } else {
                            db.updateProfile(new ProfileModel(objModel.getID(), objModel.getName(), objModel.getLname(), objModel.getImage()));
                            Toast.makeText(getActivity().getApplicationContext(), "successfully Updated ", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getActivity().getApplicationContext(), "Invalid First Name Or last Name. exam Ravi kumar ", Toast.LENGTH_SHORT).show();

                    }

                }

            }
        });

        return root;
    }
    // validate first name
    public static boolean firstName( String firstName ) {
        return firstName.matches( "^[A-Za-z]\\w{2,30}$" );
    }
    // validate last name
    public static boolean lastName( String lastName ) {
        return lastName.matches( "^[A-Za-z]\\w{2,10}$" );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GALLERY && resultCode != 0) {
            Uri mImageUri = data.getData();
            try {
                Image = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), mImageUri);
                if (getOrientation(getActivity().getApplicationContext(), mImageUri) != 0) {
                    Matrix matrix = new Matrix();
                    matrix.postRotate(getOrientation(getActivity().getApplicationContext(), mImageUri));
                    if (rotateImage != null)
                        rotateImage.recycle();
                    rotateImage = Bitmap.createBitmap(Image, 0, 0, Image.getWidth(), Image.getHeight(), matrix,true);
                    imageView.setImageBitmap(rotateImage);

                } else
                    imageView.setImageBitmap(Image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static int getOrientation(Context context, Uri photoUri) {
        Cursor cursor = context.getContentResolver().query(photoUri,
                new String[] { MediaStore.Images.ImageColumns.ORIENTATION },null, null, null);

        if (cursor.getCount() != 1) {
            return -1;
        }
        cursor.moveToFirst();
        return cursor.getInt(0);
    }
}