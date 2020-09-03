package com.manager.managester.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.manager.managester.R;
import com.manager.utils.NotificationsAdapter;
import com.manager.managester.databinding.FragmentNotificationsBinding;
import com.manager.managester.employee.employeemodel.NotificationModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import timber.log.Timber;

import static com.manager.utils.BaseApplication.CHANNEL_ID;

public class NotificationFragment extends Fragment {

    private NotificationViewModel notificationViewModel;
    private FragmentNotificationsBinding binding;
    private List<NotificationModel> notificationModelList;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding  = FragmentNotificationsBinding.inflate(inflater,container,false);

        notificationViewModel = ViewModelProviders.of(this).get(NotificationViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        notificationModelList = new ArrayList<>();


        setUpNotificationModel();
    }

    private void setUpNotificationModel(){

        binding.notificationRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.notificationRecycler.setHasFixedSize(true);


        notificationModelList.add(new NotificationModel("https://cdn.pixabay.com/photo/2016/01/09/18/27/old-1130731__340.jpg","https://cdn.pixabay.com/photo/2016/01/09/18/27/old-1130731__340.jpg","Sonatrach","This is a good product..."));
        notificationModelList.add(new NotificationModel("https://cdn.pixabay.com/photo/2016/01/09/18/27/old-1130731__340.jpg","https://cdn.pixabay.com/photo/2016/01/09/18/27/old-1130731__340.jpg","Camera","This is a good camera"));
        notificationModelList.add(new NotificationModel("https://cdn.pixabay.com/photo/2016/01/06/12/52/camera-1124074__340.jpg","https://cdn.pixabay.com/photo/2016/01/06/12/52/camera-1124074__340.jpg","Naftal","This is a good company"));


        NotificationsAdapter notificationsAdapter = new NotificationsAdapter(notificationModelList,requireContext());
        binding.notificationRecycler.setAdapter(notificationsAdapter);



    }
}