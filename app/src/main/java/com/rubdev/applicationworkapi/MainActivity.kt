package com.rubdev.applicationworkapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rubdev.applicationworkapi.api.MyRetrofit
import com.rubdev.applicationworkapi.model.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var recycleProducts: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        recycleProducts = findViewById(R.id.recycle_products)
        recycleProducts.layoutManager = LinearLayoutManager(this)
        getData()

    }

    private fun getData(){
        val call: Call<List<Product>> = MyRetrofit.instance?.productApi()?.getProductApi() as Call<List<Product>>
        // fila de requisição enqueue
        call.enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
               val adapter = response.body()?.let {
                   ProductAdapter(this@MainActivity ,
                       it.toList())
               }
                recycleProducts.adapter = adapter
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity,
                    t.message,
                    Toast.LENGTH_LONG
                ).show()
            }

        })
    }
}