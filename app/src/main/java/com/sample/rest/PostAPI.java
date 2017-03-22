package com.sample.rest;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PostAPI {

    @GET("/posts")
    Call<List<Post>> listPosts();

    @GET("/posts/{id}")
    Call<Post> getById(@Path("id") int id);

    @POST("/posts")
    Call<Post> createPost(@Body Post post);

    @PUT("/posts/{id}")
    Call<Post> updatePost(@Path("id") int id, @Body Post post);

    @DELETE("/posts/{id}")
    Call<Void> deletePost(@Path("id") int id);

}
