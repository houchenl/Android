package com.hc.retrofit;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface ObserverService {

    @GET("users/{user}/repos")
    Observable<List<Repo>> listRepos(@Path("user") String user);

}
