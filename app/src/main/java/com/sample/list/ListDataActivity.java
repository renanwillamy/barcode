package com.sample.list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.sample.adapters.RecyclerViewListAdapter;
import com.sample.barcode.R;
import com.sample.restapi.Post;
import com.sample.restapi.PostAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListDataActivity extends AppCompatActivity {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private RecyclerView mRecyclerView;
    private Retrofit mRetrofit;
    private PostAPI mPostAPI;
    private EditText mEdtUserId, mEdtTitle, mEdtBody, mEdtId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);

        mEdtUserId = (EditText) findViewById(R.id.edt_user_id);
        mEdtTitle = (EditText) findViewById(R.id.edt_post_title);
        mEdtBody = (EditText) findViewById(R.id.edt_body);
        mEdtId = (EditText) findViewById(R.id.edt_post_id);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }

    private void updateListView() {

        Call<List<Post>> posts = mPostAPI.listPosts();
        posts.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                List<Post> posts = response.body();
                RecyclerViewListAdapter mAdapter = new RecyclerViewListAdapter(posts);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Toast.makeText(ListDataActivity.this, "Failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list_data, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.btn_post_menu:
                executePost();
                return true;
            case R.id.btn_put_menu:
                executePut();
                return true;
            case R.id.btn_get_by_id_menu:
                executeGetById();
                return true;
            case R.id.btn_delete_menu:
                executeDelete();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void executeGetById() {
        Post post = new Post();
        post.setId(Integer.parseInt(mEdtId.getText().toString()));
        Call<Post> myPost = mPostAPI.getById(post.getId());
        myPost.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Toast.makeText(ListDataActivity.this, "Success " + response.body().toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(ListDataActivity.this, "Failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void executeDelete() {
        int id = (Integer.parseInt(mEdtId.getText().toString()));
        Call<Void> myPost = mPostAPI.deletePost(id);
        myPost.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(ListDataActivity.this, "Success ",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ListDataActivity.this, "Failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void executePost() {
        try {
            Post post = new Post();
            post.setUserId(Integer.valueOf(mEdtUserId.getText().toString()));
            post.setTitle(mEdtTitle.getText().toString());
            post.setBody(mEdtBody.getText().toString());
            Call<Post> myPost = mPostAPI.createPost(post);
            myPost.enqueue(new Callback<Post>() {
                @Override
                public void onResponse(Call<Post> call, Response<Post> response) {
                    Toast.makeText(ListDataActivity.this, "Success " + response.body().toString(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<Post> call, Throwable t) {
                    Toast.makeText(ListDataActivity.this, "Failed", Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception ec) {
            Toast.makeText(ListDataActivity.this, "Complete all fields", Toast.LENGTH_LONG).show();
        }
    }

    private void executePut() {
        try {
            Post post = new Post();
            post.setId(Integer.parseInt(mEdtId.getText().toString()));
            post.setUserId(Integer.valueOf(mEdtUserId.getText().toString()));
            post.setTitle(mEdtTitle.getText().toString());
            post.setBody(mEdtBody.getText().toString());
            Call<Post> myPost = mPostAPI.updatePost(post.getId(), post);
            myPost.enqueue(new Callback<Post>() {
                @Override
                public void onResponse(Call<Post> call, Response<Post> response) {
                    Toast.makeText(ListDataActivity.this, "Success " + response.body().toString(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<Post> call, Throwable t) {
                    Toast.makeText(ListDataActivity.this, "Failed", Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception ec) {
            Toast.makeText(ListDataActivity.this, "Complete all fields", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mPostAPI = mRetrofit.create(PostAPI.class);
        }
        updateListView();
    }
}
