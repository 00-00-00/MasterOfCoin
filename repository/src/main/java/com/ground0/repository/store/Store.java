package com.ground0.repository.store;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import rx.Observable;

/**
 * Created by zer0 on 22/12/16.
 *
 * Retrofit interface
 */

public interface Store {

  @GET("jsonBlob/2cb44bc9-c760-11e6-b16a-5db109a5b68e")
  Observable<Response<Object>> getTransactions();

  //Not a good idea
  @PUT("jsonBlob/2cb44bc9-c760-11e6-b16a-5db109a5b68e")
  Observable<Response<Object>> updateTransactions();
}
