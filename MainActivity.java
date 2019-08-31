package com.example.crudappjava.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.crudappjava.App;
import com.example.crudappjava.R;
import com.example.crudappjava.adapter.UserAdapter;
import com.example.crudappjava.databinding.ActivityMainBinding;
import com.example.crudappjava.model.MessageCallBack;
import com.example.crudappjava.model.User;
import com.example.crudappjava.utils.NetworkUtils;
import com.example.crudappjava.viewmodel.UserViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private List<User> userList = new ArrayList<>();
    private UserAdapter adapter;
    private RecyclerView recyclerView;
    private ActivityMainBinding activityMainBinding;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        initRecyclerView();
        if(!App.prefs.isInseted()){
            if(NetworkUtils.isInternetAvailable(this)) {
                activityMainBinding.progressCircular.setVisibility(View.VISIBLE);
                userViewModel.fetchAllUsers();
            }else {
                showMessage("Internet not available");
            }
        }else {
            showMessage("Data already inserted");
        }

        userViewModel.getAllUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                activityMainBinding.progressCircular.setVisibility(View.GONE);
                userList.clear();
                userList.addAll(users);
                adapter.notifyDataSetChanged();
            }
        });

        userViewModel.getMessageCallBack().observe(this, new Observer<MessageCallBack>() {
            @Override
            public void onChanged(MessageCallBack messageCallBack) {
                showMessage(messageCallBack.getMessage());
            }
        });

    }

    private void showMessage(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
    }

    private void initRecyclerView() {
        recyclerView = activityMainBinding.rvUserlist;
        adapter = new UserAdapter(userList, this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View view) {
        int clickedPosition = (int) view.getTag();
        switch (view.getId()) {
            case R.id.img_user:
                showMessage("Image Clicked");
                startDetailActivity(clickedPosition);
                break;
            default:
                showMessage("Item Clicked");
                startDetailActivity(clickedPosition);
                break;
        }
    }

    private void startDetailActivity(int clickedPosition) {
        User user = userList.get(clickedPosition);
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("USER_OBJ", user);
        startActivity(intent);
    }
}
