package com.example.crudappjava.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.crudappjava.R;
import com.example.crudappjava.databinding.ActivityDetailBinding;
import com.example.crudappjava.model.User;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding detailBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        detailBinding = DataBindingUtil.setContentView(this,R.layout.activity_detail);

        User userObj = (User) getIntent().getSerializableExtra("USER_OBJ");
        if(userObj!=null){
            detailBinding.txtName.setText(userObj.getFirstName()+" "+userObj.getLastName());
            detailBinding.txtEmail.setText(userObj.getEmail());
            Picasso.with(this).load(userObj.getAvatar()).into(detailBinding.imgUser);
        }
    }
}
