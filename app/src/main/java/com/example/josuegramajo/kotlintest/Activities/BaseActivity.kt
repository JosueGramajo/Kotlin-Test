package com.example.josuegramajo.kotlintest.Activities

import android.app.ProgressDialog
import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.example.josuegramajo.kotlintest.Utils.Companions
import com.example.josuegramajo.kotlintest.Utils.FirestoreUtils
import org.jetbrains.anko.alert
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.progressDialog
import org.jetbrains.anko.yesButton

/**
 * Created by josuegramajo on 8/22/18.
 */
open class BaseActivity : AppCompatActivity(){
    lateinit var dialog : ProgressDialog
    val firestore = FirestoreUtils()

    fun makeAnkoAlert(content:String,title:String){
        alert(content,title) {
            yesButton {  }
        }.show()
    }

    fun showProgressDialog(title: String, message: String){
        dialog = indeterminateProgressDialog(title, message)
        dialog.show()
    }

    fun hideProgressDialog(){
        dialog.hide()
    }
}