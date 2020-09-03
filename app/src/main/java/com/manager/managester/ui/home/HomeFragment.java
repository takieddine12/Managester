package com.manager.managester.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.manager.utils.EmployeeAdapter;
import com.manager.managester.databinding.FragmentHomeBinding;
import com.manager.managester.employee.employeemodel.ProductsModel;


import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private EmployeeAdapter employeeAdapter;
    private DatabaseReference databaseReference;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =  new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater,container,false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databaseReference =  FirebaseDatabase.getInstance().getReference("product_details");

        setUpRecyclerView();
    }

    private void setUpRecyclerView(){
        final List<ProductsModel> list = new ArrayList<>();
        binding.homeFragmentRecyclerview.setLayoutManager(new LinearLayoutManager(requireContext()));
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot snapshot: dataSnapshot.getChildren()){

                        if(snapshot.exists()){

                            String productimage = snapshot.child("productimage").getValue().toString();
                            String producticon = snapshot.child("producticon").getValue().toString();
                            String brand = snapshot.child("brand").getValue().toString();
                            String number = snapshot.child("number").getValue().toString();
                            String productiondate = snapshot.child("productiondate").getValue().toString();
                            String productionexpiry = snapshot.child("productionexpiry").getValue().toString();
                            String productname = snapshot.child("productname").getValue().toString();

                            ProductsModel model = new ProductsModel(productimage,producticon,productname,brand,number,productiondate,productionexpiry);

                            list.add(model);

                            employeeAdapter = new EmployeeAdapter(list,requireContext());
                            binding.homeFragmentRecyclerview.setAdapter(employeeAdapter);
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    //error
                }
            });
        }
}