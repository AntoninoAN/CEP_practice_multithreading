package com.example.cep_rxjava.remote

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.http.GET

interface API {
    //https://github.com/public-apis/public-apis
    @GET()
    fun getData(): Observable<Any>

    companion object{
        fun createApi(): API =
            Retrofit.Builder()
                .baseUrl("")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(API::class.java)
        fun customApi() = Observable.just("One","","Three")
    }
}