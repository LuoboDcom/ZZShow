package com.ys.yoosir.zzshow.retrofitdemo;

/**
 *  GitHub Repositories model
 * Created by Yoosir on 2016/10/18 0018.
 */
public class Repo {

    public String name;       //项目名
    public String html_url;   //项目地址

    @Override
    public String toString() {
        return "Repo{" +
                "name='" + name + '\'' +
                ", html_url='" + html_url + '\'' +
                '}';
    }
}
