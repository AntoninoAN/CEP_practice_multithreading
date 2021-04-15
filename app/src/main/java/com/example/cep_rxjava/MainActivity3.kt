package com.example.cep_rxjava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject

private const val TAG = "MainActivity3"

/**
 * At its core, RxJava and reactive programming is designed as a means of handling a stream of
 * events. This is the same goal accomplished by event buses. An RxJava PublishSubject makes an
 * ideal event bus, so there is no good reason to include an event bus library in an app utilizing
 * RxJava
 */
class MainActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        val bus = MyEventBus()
        bus.toObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d(TAG, "onCreate: $it")
            }
        bus.send(12)
    }
}

class MyEventBus{
    private val bus: PublishSubject<Any> = PublishSubject.create()

    fun send(data: Any){ bus.onNext(data) }

    fun toObservable() = bus
}