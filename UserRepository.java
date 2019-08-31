package com.example.crudappjava.database;

import android.app.Application;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.crudappjava.App;
import com.example.crudappjava.apis.RetrofitClient;
import com.example.crudappjava.model.MessageCallBack;
import com.example.crudappjava.model.User;
import com.example.crudappjava.model.UserResponse;
import java.util.List;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class UserRepository {

    private static final String TAG = UserRepository.class.getSimpleName();
    private UserDao mUserDao;
    private LiveData<List<User>> mAllUsers;
    private MutableLiveData<MessageCallBack> mMessageCallBacks = new MutableLiveData<>();

    public UserRepository(Application application) {
        UserDatabase db = UserDatabase.getDatabase(application);
        mUserDao = db.userDao();
        mAllUsers = mUserDao.getAllUsers();
    }

    public LiveData<List<User>> getAllUsers() {
        return mAllUsers;
    }

    public MutableLiveData<MessageCallBack> getmMessageCallBacks(){return mMessageCallBacks;}


    public void insert (User user) {
        mUserDao.insert(user).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(Long aLong) {

                        Log.d(TAG,"Inserted Id:"+aLong);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG,"Error message:"+e.getMessage());
                    }
                });
    }

    public void fetchAllUsers(){
        RetrofitClient.getInstance().getApiService().getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<UserResponse>() {
                    @Override
                    public void onNext(UserResponse value) {
                        for(User user : value.getData()){
                            insert(user);
                        }
                        App.prefs.setInseted(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mMessageCallBacks.setValue(new MessageCallBack(1,e.getMessage()));
                    }

                    @Override
                    public void onComplete() {
                        mMessageCallBacks.setValue(new MessageCallBack(0,"Api Success"));
                    }
                });
    }
}
