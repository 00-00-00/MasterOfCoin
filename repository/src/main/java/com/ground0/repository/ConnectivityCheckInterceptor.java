package com.ground0.repository;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import com.ground0.event.Event;
import com.ground0.event.NoNetworkEvent;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Response;
import rx.subjects.PublishSubject;

/**
 * Created by zer0 on 24/12/16.
 */

public class ConnectivityCheckInterceptor implements Interceptor {

  private final Context context;
  private final PublishSubject<Event> systemPublishBus;

  public ConnectivityCheckInterceptor(Context context, PublishSubject<Event> systemBus){
    this.context = context;
    this.systemPublishBus = systemBus;
  }

  @Override public Response intercept(Interceptor.Chain chain) throws IOException {
    Log.d("Connectivity", "Inside connectivity interceptor");
    ConnectivityManager cm =
        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
    boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    Log.d("Connectivity", "Network available:" + isConnected);

    if(!isConnected){
      systemPublishBus.onNext(new NoNetworkEvent());
    }
    Response response = chain.proceed(chain.request());
    return response;
  }
}