package com.ground0.repository.store;

import com.ground0.model.TransactionObject;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
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
  Observable<Response<TransactionObject>> getTransactions();

  @PUT("jsonBlob/2cb44bc9-c760-11e6-b16a-5db109a5b68e")
  Observable<Response<ResponseBody>> updateTransactions(@Body TransactionObject transactionObject);
}
