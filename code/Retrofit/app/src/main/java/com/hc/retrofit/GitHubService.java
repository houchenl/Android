package com.hc.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubService {

    /*
    * @GET 表示是发送一个get请求。
    * @GET后面括号中的参数表示get请求的地址，该地址与baseUrl一起组成get请求的完整地址。
    * 参数中大括号内的内容表示待定内容，具体值由下面listRepos方法的参数确定。
    * @Path("user")将listRepos方法中的参数user指定给@GET的参数，得到具体的地址。
    * Call<>表示请求的返回，List<Repo>表示请求返回的数据格式
    * */
    @GET("users/{user}/repos")
    Call<List<Repo>> listRepos(@Path("user") String user);

    @GET("users")
    Call<List<User>> getUsers();

}
