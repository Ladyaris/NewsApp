package com.chores.newsapp

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.chores.newsapp.adapter.NewsAdapter
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.chores.newsapp.databinding.ActivityMainBinding
import com.chores.newsapp.model.ResponseNews
import com.chores.newsapp.service.RetrofitConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    val date = getCurreDateTime()
    var refUsers : DatabaseReference? =  null
    var firebaseUser : FirebaseUser? = null
    lateinit var mainBinding: ActivityMainBinding

    private fun getCurreDateTime(): Date {
        return Calendar.getInstance().time
    }

    fun Date.toString(format: String, locale : Locale = Locale.getDefault()):String{
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    companion object {
        fun getLaunchService(from: Context) = Intent(from, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        supportActionBar?.hide()
        ibProfile.setOnClickListener(this@MainActivity)
        tvDateMain.text = date.toString("dd/MM/yyyy")
        getNews()

    }

    private fun getNews() {
        val country = "id"
        val apikey = "9d97a9bc85b54dc8804f9ef68e5d3c5c"

        val loading = ProgressDialog.show(this, "Request Data", "Loading ..")
        RetrofitConfig.getInstance().getNewsHeadLines(country, apikey).enqueue(
            object : Callback<ResponseNews>{
                override fun onResponse(
                    call: Call<ResponseNews>,
                    response: Response<ResponseNews>
                ) {
                    Log.d ("Response", "Success" + response.body()?.articles)
                    loading.dismiss()
                    if (response.isSuccessful) {
                        val status = response.body()?.articles
                        val newsAdapter = NewsAdapter(this@MainActivity)

                    }
                }

                override fun onFailure(call: Call<ResponseNews>, t: Throwable) {
                    Log.d("Response","Failed" + t.localizedMessage)
                    loading.dismiss()
                }

            }
        )
    }


    companion object {
        fun getLaunchService(from: Context) = Intent(from,
            MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.ibProfile -> startActivity(Intent(ProfileActivity.getLaunchService(this)))
        }
    }
}