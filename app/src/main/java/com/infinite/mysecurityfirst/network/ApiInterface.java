package com.infinite.mysecurityfirst.network;


import com.infinite.mysecurityfirst.pojo.LoginRequest;
import com.infinite.mysecurityfirst.pojo.LoginResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Rajini Ragupathy on 05-06-2020.
 */
public interface ApiInterface {

    //Login
    @POST("register")
    @Headers(ApiConstants.KEY_JSON)
    Observable<LoginResponse> Login(
            @Body LoginRequest loginRequest);
}
