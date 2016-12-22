package com.ground0.repository.store;

import com.ground0.repository.BuildConfig;
import com.ground0.repository.CustomObjectMapper;
import com.ground0.repository.HttpResponseStatusOperator;
import com.ground0.repository.repository.UserRepository;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import rx.schedulers.Schedulers;

/**
 * Created by zer0 on 22/12/16.
 *
 * Rest Client builder
 */

public class ServerStore implements UserRepository {

  static final int CONNECT_TIMEOUT_MILLIS = 15; // 15s
  static final int READ_TIMEOUT_MILLIS = 20; // 20

  private String host;
  private CustomObjectMapper objectMapper;
  private Store restImpl;
  HttpResponseStatusOperator responseStatusOperator;

  public ServerStore() {
    this.objectMapper = new CustomObjectMapper();
    host = BuildConfig.HOST;
    responseStatusOperator = new HttpResponseStatusOperator(this.objectMapper);

    OkHttpClient client =
        new OkHttpClient.Builder().connectTimeout(CONNECT_TIMEOUT_MILLIS, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT_MILLIS, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build();

    Retrofit retrofit = new Retrofit.Builder().baseUrl(host)
        .client(client)
        .addConverterFactory(JacksonConverterFactory.create(objectMapper))
        .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
        .build();

    restImpl = retrofit.create(Store.class);
  }
}
