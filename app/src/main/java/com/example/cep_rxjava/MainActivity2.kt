package com.example.cep_rxjava

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

private const val TAG = "MainActivity2"
class MainActivity2 : AppCompatActivity() {
    private val TAG = "Example6"
    private lateinit var createSub: Disposable
    private lateinit var startSub: Disposable
    private lateinit var resumeSub: Disposable

    private lateinit var mOutputTextView1: TextView
    private lateinit var mOutputTextView2:TextView
    private lateinit var mOutputTextView3:TextView

    // Observable.create starts as a cold observable, so this observable
    // will produce new function executions for each .subscribe().
    private val exampleObservable: Observable<Int> = Observable.range(0, 10)
        //.observeOn(AndroidSchedulers.mainThread())

    /**
     * Handle any error from the Downstream
     */
    var errorHandler = Consumer<Throwable?> {
        it.printStackTrace()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        mOutputTextView1 = findViewById<View>(R.id.tv_1) as TextView
        mOutputTextView2 = findViewById<View>(R.id.tv_2) as TextView
        mOutputTextView3 = findViewById<View>(R.id.tv_3) as TextView
        createSub = exampleObservable
            .doOnDispose {
                    Log.d(TAG, "Called unsubscribe OnDestroy()")
                }
            .subscribe({
                    mOutputTextView1.text = Integer.toString(it!!) + " OnCreate()"
                }, errorHandler)
    }

    override fun onStart() {
        super.onStart()
        startSub = exampleObservable
            .doOnDispose {
                    Log.d(TAG, "Called unsubscribe OnStop()")
                }
            .subscribe ({
                    mOutputTextView2.text = Integer.toString(it!!) + " OnStart()"
                }, errorHandler)
    }

    override fun onResume() {
        super.onResume()
        resumeSub = exampleObservable
            .doOnDispose {
                    Log.d(TAG, "Called unsubscribe OnPause()")
                }
            .subscribe({
                mOutputTextView3.text = Integer.toString(it!!) + " OnResume()"
            }, errorHandler)
    }

    override fun onPause() {
        super.onPause()
        resumeSub.dispose()
    }

    override fun onStop() {
        super.onStop()
        startSub.dispose()
    }

    override fun onDestroy() {
        super.onDestroy()
        createSub.dispose()
    }
}