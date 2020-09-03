package com.manager.managester.employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.manager.managester.R;
import com.manager.managester.databinding.ActivityEmployeeForgotPasswodBinding;

import timber.log.Timber;

public class EmployeeForgotPasswodActivity extends AppCompatActivity {

    private ActivityEmployeeForgotPasswodBinding binding;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmployeeForgotPasswodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("employee_details");

        binding.resetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String user_email = binding.forgotPasswordEdit.getText().toString().trim();

                if(user_email.isEmpty()){
                    Toast.makeText(EmployeeForgotPasswodActivity.this,"Please Fill In With An Email",Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                String email = snapshot.child("email").getValue().toString();
                                if(email.equals(user_email)) {
                                    mAuth.sendPasswordResetEmail(user_email).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(EmployeeForgotPasswodActivity.this,"Reset Email Successfully Sent",Toast.LENGTH_SHORT).show();
                                            }else {
                                                Toast.makeText(EmployeeForgotPasswodActivity.this,"Something went wrong..",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Timber.d("Error Password Reset ..." + e.getMessage());
                                        }
                                    });

                                }else {
                                    Toast.makeText(EmployeeForgotPasswodActivity.this,"No Such Email Found..",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }
        });
    }
}

