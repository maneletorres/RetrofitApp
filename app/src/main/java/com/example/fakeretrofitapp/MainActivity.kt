package com.example.fakeretrofitapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fakeretrofitapp.models.Comment
import com.example.fakeretrofitapp.models.Post
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val repository: Repository = Repository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // getPosts()
        // getComments()
        // createPost()
        // updatePost()
        deletePost()
    }

    private fun getPosts() {
        val parameters = HashMap<String, String>()
        parameters["userId"] = "1"
        parameters["_sort"] = "id"
        parameters["_order"] = "desc"

        //val call = repository.client.getPosts(1, "Title", "Body")
        val call = repository.client.getPosts(parameters)
        call.enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (!response.isSuccessful) {
                    text_view_result.text = "Code: ${response.code()}"
                    return
                }
                val posts = response.body()
                posts?.let { postList ->
                    for (post in postList) {
                        val content = "ID: ${post.id} User ID: ${post.userId} Title: ${post.title} Body: ${post.body}"
                        text_view_result.append(content)
                    }
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                text_view_result.text = t.message
            }
        })
    }

    private fun getComments() {
        val call = repository.client.getComments("posts/3/comments")
        call.enqueue(object : Callback<List<Comment>> {
            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                if (!response.isSuccessful) {
                    text_view_result.text = "Code: ${response.code()}"
                    return
                }
                val comments = response.body()
                comments?.let { commentList ->
                    for (comment in commentList) {
                        val content = "Post ID: ${comment.postId} ID: ${comment.id} Name: ${comment.name} Email: ${comment.email} Body: ${comment.body}"
                        text_view_result.append(content)
                    }
                }
            }

            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                text_view_result.text = t.message
            }
        })
    }

    private fun createPost() {
        val fields = HashMap<String, String>()
        fields["userId"] = "23"
        fields["title"] = "New Title"
        fields["body"] = "New Text"

        val call = repository.client.createPost(fields)
        call.enqueue(object : Callback<Post?> {
            override fun onResponse(call: Call<Post?>, response: Response<Post?>) {
                if (!response.isSuccessful) {
                    text_view_result.text = "Code: ${response.code()}"
                    return
                }
                val postResponse = response.body()
                postResponse?.let {
                    val content = "Code: ${response.code()} ID: ${postResponse.id} User ID: ${postResponse.userId} Title: ${postResponse.title} Body: ${postResponse.body}"
                    text_view_result.text = content
                }
            }

            override fun onFailure(call: Call<Post?>, t: Throwable) {
                text_view_result.text = t.message
            }
        })
    }

    private fun updatePost() {
        val post = Post(12, null, "New Text")
        val headers = HashMap<String, String>()
        headers["Map-Header1"] = "def"
        headers["Map-Header2"] = "ghi"
        val call = repository.client.patchPost(headers, 5, post)
        call.enqueue(object : Callback<Post?> {
            override fun onResponse(call: Call<Post?>, response: Response<Post?>) {
                if (!response.isSuccessful) {
                    text_view_result.text = "Code: ${response.code()}"
                    return
                }
                val postResponse = response.body()
                var content = ""
                content += """
                    Code: ${response.code()}
                    ID: ${postResponse?.id}
                    User ID: ${postResponse?.userId}
                    Title: ${postResponse?.title}
                    Body: ${postResponse?.body}


                    """.trimIndent()
                text_view_result.text = content
            }

            override fun onFailure(call: Call<Post?>, t: Throwable) {
                text_view_result.text = t.message
            }
        })
    }

    private fun deletePost() {
        val call = repository.client.deletePost(5)
        call.enqueue(object : Callback<Void?> {
            override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                text_view_result.text = "Code: ${response.code()}"
            }

            override fun onFailure(call: Call<Void?>, t: Throwable) {
                text_view_result.text = t.message
            }
        })
    }
}