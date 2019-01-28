package com.hc.retrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://api.github.com/";

    private Subscription mSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        callResponse();
        observerResponse();
    }

    @Override
    protected void onDestroy() {
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }

        super.onDestroy();
    }

    private void callResponse() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                // 需要指定一个factory来deserializing返回的结果
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubService service = retrofit.create(GitHubService.class);

        Call<List<Repo>> repos = service.listRepos("octocat");
        repos.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                Log.d("houchen", "repos onResponse size " + response.body().size());
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                Log.d("houchen", "repos onFailure for " + t.getMessage());
            }
        });

        Call<List<User>> users = service.getUsers();
        users.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                Log.d("houchen", "users onResponse size " + response.body().size());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d("houchen", "users onFailure for " + t.getMessage());
            }
        });
    }

    private void observerResponse() {
        RxJavaCallAdapterFactory adapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                // 需要指定一个factory来deserializing返回的结果
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(adapter)
                .build();

        ObserverService apiService = retrofit.create(ObserverService.class);

        Observable<List<Repo>> users = apiService.listRepos("octocat");
        mSubscription = users
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Repo>>() {
                    @Override
                    public void onCompleted() {
                        Log.d("houchen", "onComplete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("houchen", "onError");
                    }

                    @Override
                    public void onNext(List<Repo> repos) {
                        Log.d("houchen", "onNext size " + repos.size());
                    }
                });
    }

}
