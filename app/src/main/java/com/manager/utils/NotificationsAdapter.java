package com.manager.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.manager.managester.R;
import com.manager.managester.databinding.NotificationrowslayoutBinding;
import com.manager.managester.employee.employeemodel.NotificationModel;

import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.viewholder> {

    private List<NotificationModel> notificationModelList;
    private Context context;

    public NotificationsAdapter(List<NotificationModel> notificationModelList,Context context){
        this.notificationModelList = notificationModelList;
        this.context = context;
    }
    public class viewholder extends RecyclerView.ViewHolder{
       private NotificationrowslayoutBinding binding;
        public viewholder(NotificationrowslayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NotificationrowslayoutBinding binding  = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.notificationrowslayout,parent,false);
        return new viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
          NotificationModel model = notificationModelList.get(position);
          holder.binding.setModel(model);
    }

    @Override
    public int getItemCount() {
        return notificationModelList.size();
    }


}
