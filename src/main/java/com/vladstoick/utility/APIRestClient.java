package com.vladstoick.utility;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;

/**
 * Created by vlad on 6/8/13.
 */
public interface APIRestClient {
    @GET("/user/{id}/wall")
    void getWall(Callback<Response> cb);

}
