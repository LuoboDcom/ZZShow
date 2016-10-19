package com.ys.yoosir.zzshow.retrofitdemo;


import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 *  Retrofit turns your HTTP API into a Java interface
 * Created by yoosir on 2016/10/18 0018.
 */
public interface GitHubService {

    /**
     *  API Declaration
     *
     *  REQUEST METHOD
     *
     *  @GET("users/list")
     *
     *  @GET("users/list?sort=desc")
     **/

    @GET("users/{user}/repos")
    Call<List<Repo>> listRepos(@Path("user") String user);

    @GET("users/{user}/repos")
    Call<List<Repo>> lisReposWithSort(@Path("user") String user, @Query("sort")String sort);

    @GET("users/{user}/repos")
    Call<List<Repo>> listReposWithMap(@Path("user") String user, @QueryMap Map<String ,String> options);

    //--------------------------  REQUEST body --------------------------
    /**
     * The object will also be converted using a converter specified on the Retrofit instance. If no converter is added, only RequestBody can be used.
     *
     **/
    @POST("user/new")
    Call<User> createUser(@Body User user);

    // ------------------------ Form Encode and Manipulation --------------

    @FormUrlEncoded
    @POST("user/edit")
    Call<User> updateUser(@Field("first_name") String first,@Field("last_name") String last);

    @Multipart
    @PUT("user/photo")
    Call<User> updateUser(@Part("photo")RequestBody photo,@Part("description") RequestBody description);

    // -----------------  Header Manipulation  -----------------------

    @Headers("Cache-Control: max-age = 640000")
    @GET("users/{username}")
    Call<User> getUser(@Path("username") String username);

    @Headers({
            "Accept: application/vnd.github.v3.full+json",
            "User-Agent: Retrofit-Sample-App"
    })
    @GET("users/{username}")
    Call<User> getUserWithHeader(@Path("username") String username);

    @GET("user")
    Call<User> getUserWithDynamHeader(@Header("Authorization") String authorization);
}
