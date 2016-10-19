package com.ys.yoosir.zzshow.retrofitdemo;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *  Retrofit
 * Created by yoosir on 2016/10/18 0018.
 */
public class RetrofitSimple {

    private GitHubService service;
    public RetrofitSimple(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(GitHubService.class);
    }

    public void getUserRepos(){
        Call<List<Repo>> reposCall = service.listRepos("LuoboDcom");

        try {
            List<Repo> repos = reposCall.execute().body();
            for ( Repo bean: repos) {
                System.out.println(bean.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void getUserReposWithSort(){
        Call<List<Repo>> reposCall = service.lisReposWithSort("LuoboDcom","id");

        try {
            List<Repo> repos = reposCall.execute().body();
            for ( Repo bean: repos) {
                System.out.println(bean.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getUserReposWithMap(){
        Map<String,String> param = new HashMap<>();
        param.put("sort","id");
        Call<List<Repo>> reposCall = service.listReposWithMap("LuoboDcom",param);

        try {
            List<Repo> repos = reposCall.execute().body();
            for ( Repo bean: repos) {
                System.out.println(bean.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
