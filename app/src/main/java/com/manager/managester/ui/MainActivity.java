package com.manager.managester.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.manager.managester.R;
import com.manager.managester.databinding.ActivityMainBinding;
import com.manager.managester.employee.EmployeeLoginActivity;
import com.manager.managester.employee.employeemodel.ProductsModel;
import com.manager.managester.products.add_product_activity;
import com.manager.managester.services.NotificationService;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.appcompat.app.AppCompatActivity;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private NavController navController;
    private DatabaseReference firebaseDatabase;
    private static final int  REQUEST_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMainId.toolbar);
        binding.appBarMainId.fabMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, add_product_activity.class);
                startActivityForResult(intent,REQUEST_CODE);

            }
        });

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_notifications, R.id.nav_add_product)
                .setDrawerLayout(binding.drawerLayout)
                .build();
         navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        binding.navView.setNavigationItemSelectedListener(this);
        binding.navView.bringToFront();

        firebaseDatabase = FirebaseDatabase.getInstance().getReference("product_details");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent startServiceIntent = new Intent(this,NotificationService.class);
        startService(startServiceIntent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);
        binding.drawerLayout.closeDrawers();
        switch (item.getItemId()) {
            case R.id.nav_home:
                    navController.navigate(R.id.nav_home);
                break;

            case R.id.nav_notifications:
                    navController.navigate(R.id.nav_notifications);
                break;

            case R.id.nav_add_product:
                    navController.navigate(R.id.nav_add_product);
                break;

            case R.id.nav_sign_out:
                signOut();
                break;

        }
        return true;
    }
    private void signOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Signout")
                .setMessage("Do you really want to sign out?")
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                /*clear sharedPreferences

                */
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(MainActivity.this, EmployeeLoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }

        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && data != null){
            String imageurl  = data.getStringExtra("urlimage");
            String content1  = data.getStringExtra("productname");
            String content2  = data.getStringExtra("brand");
            String content3  = data.getStringExtra("number");
            String content4  = data.getStringExtra("productiondate");
            String content5  = data.getStringExtra("productionexpiry");

            ProductsModel productsModel = new ProductsModel(imageurl,imageurl,content1,content2,content3,content4,content5);
            firebaseDatabase.push().setValue(productsModel)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){


                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Timber.d("Exception : " + e.getMessage());
                }
            });

        }
    }


}