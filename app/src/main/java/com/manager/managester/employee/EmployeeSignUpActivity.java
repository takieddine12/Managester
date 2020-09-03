package com.manager.managester.employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.manager.managester.R;
import com.manager.managester.databinding.ActivityEmployeeSignUpBinding;
import com.manager.managester.employee.employeemodel.EmployeeModel;


import javax.security.auth.login.LoginException;

import timber.log.Timber;

public class EmployeeSignUpActivity extends AppCompatActivity {

    private ActivityEmployeeSignUpBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmployeeSignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // d9i9a


        mAuth  = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference  = firebaseDatabase.getReference("employee details");

        binding.employeeSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUpEmployee();
                finish();
            }
        });

        binding.employeeAlreadyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EmployeeSignUpActivity.this,EmployeeLoginActivity.class));
                finish();
            }
        });



    }


    private void SignUpEmployee(){
        String name = binding.editEmployeeAccountName.getText().toString();
        String email = binding.editEmployeeAccountEmail.getText().toString();
        String password = binding.editEmployeeAccountPassword.getText().toString();
        String phoneNumber = binding.editEmployeeAccountPhoneNumber.getText().toString();
        String companyCode = binding.editEmployeeAccountCompanyCode.getText().toString();
        String employeeID = FirebaseAuth.getInstance().getUid(); //rah yghoul bel heda null ghrib hedi , attends


        final EmployeeModel employeeModel = new EmployeeModel(name,email,password,phoneNumber,companyCode,employeeID);

        CheckTextInputs(name,email,password,phoneNumber,companyCode);

        //emulator mafichi gogle play services !!

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(EmployeeSignUpActivity.this,"Employee Successfully Registered..",Toast.LENGTH_SHORT).show();
                    databaseReference.child(mAuth.getCurrentUser().getUid()).setValue(employeeModel)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    goToHomeActivity();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        }
                    });

                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Timber.d("Something is wrong..");
                    }
                });
    }

    private void goToHomeActivity() {
        //hna lazem nvidou editteexts mbaad maa luser ydir registrer
        binding.editEmployeeAccountName.setText("");
        binding.editEmployeeAccountEmail.setText("");
        binding.editEmployeeAccountPassword.setText("");
        binding.editEmployeeAccountPhoneNumber.setText("");
        binding.editEmployeeAccountCompanyCode.setText("");

        startActivity(new Intent(EmployeeSignUpActivity.this, EmployeeLoginActivity.class));
        finish();
    }

    private void CheckTextInputs(String name,String email ,String password , String phoneNumber , String companyCode){
        if(TextUtils.isEmpty(name)){
            binding.editEmployeeAccountName.setError("Please enter a name");
            binding.editEmployeeAccountName.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(email)){
            binding.editEmployeeAccountEmail.setError("Please enter an email");
            binding.editEmployeeAccountEmail.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(password)){
            binding.editEmployeeAccountPassword.setError("Please enter a password");
            binding.editEmployeeAccountPassword.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(phoneNumber)){
            binding.editEmployeeAccountPhoneNumber.setError("Please enter a confirm password");
            binding.editEmployeeAccountPhoneNumber.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(companyCode)){
            binding.editEmployeeAccountCompanyCode.setError("Please enter a comapny code");
            binding.editEmployeeAccountCompanyCode.requestFocus();
            return;
        }
    }
}