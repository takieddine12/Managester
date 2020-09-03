package com.manager.managester.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.manager.managester.R;
import com.manager.managester.databinding.ProductslayoutBinding;
import com.manager.managester.employee.employeemodel.ProductsModel;

import java.util.List;


public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.viewHolder> {

    private List<ProductsModel> list;
    private Context context;

    public class viewHolder extends RecyclerView.ViewHolder {
        private ProductslayoutBinding binding;

        public viewHolder(ProductslayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public EmployeeAdapter(List<ProductsModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductslayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.productslayout, parent, false);
        return new viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        ProductsModel model = list.get(position);
        holder.binding.setModel(model);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}