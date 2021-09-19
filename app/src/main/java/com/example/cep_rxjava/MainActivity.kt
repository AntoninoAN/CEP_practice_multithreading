package com.example.cep_rxjava


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.reactivex.Observable
//import io.reactivex.ObservableEmitter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.schedulers.Schedulers

//import io.reactivex.schedulers.Schedulers


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    var mTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mTextView = findViewById<View>(R.id.example3_textView) as TextView
        findViewById<FloatingActionButton>(R.id.next_activity).setOnClickListener { nextActivity() }
    }

    private fun nextActivity() {
        startActivity(
            Intent(this, MainActivity2::class.java)
        )
    }

    override fun onResume() {
        super.onResume()

        io.reactivex.rxjava3.core.Observable.create(object : ObservableOnSubscribe<String> {
            override fun subscribe(emitter: ObservableEmitter<String>) {
                for (i in 0..10) {
                    emitter.onNext(i.toString())
                    try {
                        Thread.sleep(2000)
                    } catch (e: InterruptedException) {
                        emitter.onError(e)
                    }
                }
                emitter.onComplete()
            }
        })// all of these do() operators are optional, but can be useful
            /**
             * You subscribe to the Observable, you can use this for Loading
             */
            .doOnSubscribe {
                Log.d(TAG, "doOnSubscribe()")
            }
            /**
             * This is your "next" data in your Observable
             */
            .doOnNext {
                Log.d(TAG, "doOnNext() $it")
            }
            /**
             * No more data in your Observable
             */
            .doOnComplete {
                Log.d(TAG, "doOnCompleted()")
            }
            /**
             * You wil "disconnect" from Observables ref. Lifecycle aware
             */
            .doOnDispose {
                Log.d(TAG, "doOnUnSubscribe()")
            }
            /**
             * Non-happy path. Support for Errors.
             */
            .doOnError {
                Log.d(TAG, "doOnError() " + it.localizedMessage)
            }
            /**
             * Terminate the Observable, you can finish the Loading
             */
            .doOnTerminate {
                Log.d(TAG, "doOnTerminate()")
            }
            /**
             * After this Observable signals onError or onCompleted or gets disposed by
             * the downstream.
             */
            .doFinally {
                Log.d(TAG, "finallyDo()")
            }
            .subscribeOn(Schedulers.io())//upstream
            .observeOn(AndroidSchedulers.mainThread())//downstream
            .subscribe({ it ->
                mTextView!!.text = it
            }, {
                it.printStackTrace()
            })
    }
}