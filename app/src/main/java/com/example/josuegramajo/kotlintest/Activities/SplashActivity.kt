package com.example.josuegramajo.kotlintest.Activities

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import com.example.josuegramajo.kotlintest.R
import org.jetbrains.anko.*

/**
 * Created by josuegramajo on 6/2/17.
 */

class SplashActivity : Activity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            startActivity<LoginActivity>()
          //startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            finish()
        },3000)
    }
}
