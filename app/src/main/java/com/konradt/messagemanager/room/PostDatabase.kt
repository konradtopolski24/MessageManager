package com.konradt.messagemanager.room

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.konradt.messagemanager.models.Post
import com.konradt.messagemanager.models.PostsResponse
import com.konradt.messagemanager.retrofit.ApiService
import com.konradt.messagemanager.retrofit.ServiceClient
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

@Database(entities = [Post::class], version = 1)
abstract class PostDatabase : RoomDatabase() {

    abstract fun postDao(): PostDao

    companion object {
        private var instance: PostDatabase? = null
        private const val DB_LOG = "DB_LOG"

        fun getInstance(context: Context): PostDatabase? {
            if (instance == null) {
                synchronized(PostDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PostDatabase::class.java,
                        "post.db"
                    )
                        .addCallback(postCallback)
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return instance
        }

        private var postCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                populateDatabase()
            }
        }

        private fun populateDatabase() {
            val apiService = ServiceClient.getService(ApiService::class.java)
            val requestCall = apiService.getPosts()

            requestCall.enqueue(object : retrofit2.Callback<PostsResponse> {
                override fun onResponse(
                    call: Call<PostsResponse>,
                    response: Response<PostsResponse>
                ) {
                    if (response.isSuccessful) {
                        val posts = response.body()!!.posts
                        GlobalScope.launch {
                            instance!!.postDao().insertPosts(posts)
                        }
                    }
                }

                override fun onFailure(call: Call<PostsResponse>, t: Throwable) {
                    Log.d(DB_LOG, "Retrofit request call failure")
                }
            })
        }
    }
}