package com.example.crudappjava.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.crudappjava.database.UserRepository;
import com.example.crudappjava.model.MessageCallBack;
import com.example.crudappjava.model.User;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private UserRepository mRepository;
    private MutableLiveData<MessageCallBack> mMessageCallBack;
    private LiveData<List<User>> mAllUsers;

    public UserViewModel (Application application) {
        super(application);
        mRepository = new UserRepository(application);
        mAllUsers = mRepository.getAllUsers();
        mMessageCallBack = mRepository.getmMessageCallBacks();
    }

    public LiveData<List<User>> getAllUsers() { return mAllUsers; }

    public LiveData<MessageCallBack> getMessageCallBack(){ return mMessageCallBack;}


    public void insert(User user) { mRepository.insert(user); }

    public void fetchAllUsers(){ mRepository.fetchAllUsers();}
}
