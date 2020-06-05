package com.example.fakeretrofitapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView mTextViewResult;

    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewResult = findViewById(R.id.text_view_result);

        // Allows nulls to be sent to the RESTful service when the corresponding request is
        // executed.
        Gson gson = new GsonBuilder().serializeNulls().create();

        // Adds to Retrofit the appearance of log information after executing a request
        // such as the BODY or the HEADERS.
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() { // Headers that don't reach the RESTful service.
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request originRequest = chain.request();
                        Request newRequest = originRequest.newBuilder()
                                .header("Interceptor-Header", "xyz")
                                .build();
                        return chain.proceed(newRequest);
                    }
                })
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        getPosts();
        // getComments();
        // createPost();
        // updatePost();
        // deletePost();
    }

    private void getPosts() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("userId", "1");
        parameters.put("_sort", "id");
        parameters.put("_order", "desc");

        Call<List<Post>> call = jsonPlaceHolderApi.getPosts(parameters);
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    mTextViewResult.setText("Code: " + response.code());
                    return;
                }

                List<Post> posts = response.body();
                for (Post post : posts) {
                    String content = "";
                    content += "ID: " + post.getId() + "\n"
                            + "User ID: " + post.getUserId() + "\n"
                            + "Title: " + post.getTitle() + "\n"
                            + "Body: " + post.getBody() + "\n\n";

                    mTextViewResult.append(content);
                }

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                mTextViewResult.setText(t.getMessage());
            }
        });
    }

    private void getComments() {
        Call<List<Comment>> call = jsonPlaceHolderApi.getComments("posts/3/comments");
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (!response.isSuccessful()) {
                    mTextViewResult.setText("Code: " + response.code());
                    return;
                }

                List<Comment> comments = response.body();
                for (Comment comment : comments) {
                    String content = "";
                    content += "Post ID: " + comment.getPostId() + "\n"
                            + "ID: " + comment.getId() + "\n"
                            + "Name: " + comment.getName() + "\n"
                            + "Email: " + comment.getEmail() + "\n"
                            + "Body: " + comment.getBody() + "\n\n";

                    mTextViewResult.append(content);
                }

            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                mTextViewResult.setText(t.getMessage());
            }
        });
    }

    private void createPost() {
        Post post = new Post(23, "New Title", "New Text");
        Map<String, String> fields = new HashMap<>();
        fields.put("userId", "23");
        fields.put("title", "New Title");
        fields.put("body", "New Text");

        Call<Post> call = jsonPlaceHolderApi.createPost(fields);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    mTextViewResult.setText("Code: " + response.code());
                    return;
                }

                Post postResponse = response.body();
                String content = "";
                content += "Code: " + response.code() + "\n"
                        + "ID: " + postResponse.getId() + "\n"
                        + "User ID: " + postResponse.getUserId() + "\n"
                        + "Title: " + postResponse.getTitle() + "\n"
                        + "Body: " + postResponse.getBody() + "\n\n";

                mTextViewResult.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                mTextViewResult.setText(t.getMessage());
            }
        });
    }

    private void updatePost() {
        Post post = new Post(12, null, "New Text");

        Map<String, String> headers = new HashMap<>();
        headers.put("Map-Header1", "def");
        headers.put("Map-Header2", "ghi");

        Call<Post> call = jsonPlaceHolderApi.patchPost(headers, 5, post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    mTextViewResult.setText("Code: " + response.code());
                    return;
                }

                Post postResponse = response.body();
                String content = "";
                content += "Code: " + response.code() + "\n"
                        + "ID: " + postResponse.getId() + "\n"
                        + "User ID: " + postResponse.getUserId() + "\n"
                        + "Title: " + postResponse.getTitle() + "\n"
                        + "Body: " + postResponse.getBody() + "\n\n";

                mTextViewResult.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                mTextViewResult.setText(t.getMessage());
            }
        });
    }

    private void deletePost() {
        Call<Void> call = jsonPlaceHolderApi.deletePost(5);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                mTextViewResult.setText("Code: " + response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                mTextViewResult.setText(t.getMessage());
            }
        });
    }
}
