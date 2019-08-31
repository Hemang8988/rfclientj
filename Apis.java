package com.example.crudappjava.apis;

import com.example.crudappjava.model.UserResponse;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;

public interface Apis {
    @GET("/api/users?page=2")
    Observable<UserResponse> getUsers();
}
