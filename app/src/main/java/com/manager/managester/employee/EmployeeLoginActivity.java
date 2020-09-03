package com.manager.managester.employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.manager.managester.ui.MainActivity;
import com.manager.managester.databinding.ActivityEmployeeBinding;

public class EmployeeLoginActivity extends AppCompatActivity {

    private ActivityEmployeeBinding binding;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmployeeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        firebaseAuth  = FirebaseAuth.getInstance();
        binding.employeeLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginEmployee();
            }
        });
        binding.employeeCreateNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EmployeeLoginActivity.this,EmployeeSignUpActivity.class));
            }
        });
        binding.employeeForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EmployeeLoginActivity.this,EmployeeForgotPasswodActivity.class));
            }
        });


        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user != null){
                    startActivity(new Intent(EmployeeLoginActivity.this,MainActivity.class));
                    finish();
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
         firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        firebaseAuth.addAuthStateListener(authStateListener);
    }


    private void LoginEmployee(){
        String Email = binding.employeeLoginEmail.getText().toString();
        String Password = binding.employeeLoginPassword.getText().toString();

        if(TextUtils.isEmpty(Email)){
            binding.employeeLoginEmail.setError("confirm your email please!");
            binding.employeeLoginEmail.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(Password)){
            binding.employeeLoginPassword.setError("Enter your password please!");
            binding.employeeLoginPassword.requestFocus();
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                     if(task.isSuccessful()){

                         startActivity(new Intent(EmployeeLoginActivity.this, MainActivity.class));
                         finish();
                     }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                binding.employeeLoginPassword.setError("Email or Password incorrect!");
                binding.employeeLoginPassword.requestFocus();  }
        });


    }
}