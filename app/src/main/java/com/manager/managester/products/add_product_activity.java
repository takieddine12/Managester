package com.manager.managester.products;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.manager.managester.databinding.ActivityAddProductActivityBinding;
import com.manager.utils.DatePickerDialog;


import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

import timber.log.Timber;

public class add_product_activity extends AppCompatActivity implements android.app.DatePickerDialog.OnDateSetListener {

    private ActivityAddProductActivityBinding binding;
    private static final int IMAGE_REQUEST_CODE = 1002;
    private StorageReference storageReference;
    private FirebaseAuth mAutuh;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddProductActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        mAutuh = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("products_images").child(UUID.randomUUID().toString());

        binding.cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent intent = new Intent();
              setResult(RESULT_CANCELED,intent);
              finish();
            }
        });

        //TODO : Getting Dates from EditText

        binding.productiondate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    if(motionEvent.getRawX() >= (binding.productiondate.getRight() - binding.productiondate.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        DatePickerDialog datePickerDialog = new DatePickerDialog();
                        datePickerDialog.show(getSupportFragmentManager(),"TAG1");
                        return true;
                    }
                }
                return false;
            }
        });

        binding.productionexpiry.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;


                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    if(motionEvent.getRawX() >= (binding.productiondate.getRight() - binding.productiondate.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        DatePickerDialog datePickerDialog = new DatePickerDialog();
                        datePickerDialog.show(getSupportFragmentManager(),"TAG2");
                        return true;
                    }
                }
                return false;
            }
        });

        binding.setImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,IMAGE_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1002 && data != null){
            final Uri uri = data.getData();
            try {


                //TODO : Convertng Uri into Bitmap
                final InputStream inputStream = getContentResolver().openInputStream(uri);
                final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                binding.productImage.setImageBitmap(bitmap);

                // TODO : Pushing Image Into Firebase Storage
                storageReference.putFile(uri);


                binding.addBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO : Getting Image Url And Send Data Back to Start Activity For Result
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String imageurl = uri.toString();
                                String productName = binding.productname.getText().toString();
                                String productNumber = binding.number.getText().toString();
                                String productBrand = binding.brand.getText().toString();
                                String productDate= binding.productiondate.getText().toString();
                                String productExpiraryDate = binding.productionexpiry.getText().toString();


                                //TODO : Checking If Edittexts are empty or not
                                if (productName.trim().isEmpty() || productNumber.trim().isEmpty() || productBrand.trim().isEmpty() || productDate.trim().isEmpty()
                                        || productExpiraryDate.trim().isEmpty() ) {
                                    Toast.makeText(add_product_activity.this, "An information is missing..", Toast.LENGTH_SHORT).show();
                                    return;

                                }

                                Intent intent = new Intent();
                                intent.putExtra("urlimage",imageurl);
                                intent.putExtra("productname",productName);
                                intent.putExtra("brand",productBrand);
                                intent.putExtra("number",productNumber);
                                intent.putExtra("productiondate",productDate);
                                intent.putExtra("productionexpiry",productExpiraryDate);
                                setResult(RESULT_OK,intent);
                                finish();
                            }
                        });


                    }
                });

            }catch (Exception ex){
                Timber.d("Exception " + ex.getMessage());
            }
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,i);
        calendar.set(Calendar.MONTH,i1);
        calendar.set(Calendar.DAY_OF_MONTH,i2);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formatted_date = simpleDateFormat.format(calendar.getTime());

        binding.productionexpiry.setText(formatted_date);


    }
}