package com.example.josuegramajo.kotlintest.Activities

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.Toast
import com.example.josuegramajo.kotlintest.R
import java.net.URL
import android.os.StrictMode
import android.util.Log.println
import android.widget.EditText
import android.widget.TextView
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.beust.klaxon.int
import com.beust.klaxon.string
import org.jetbrains.anko.custom.async
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


/**
 * Created by josuegramajo on 6/5/17.
 */

class DetailActivity : AppCompatActivity(){

    lateinit var name : TextView
    lateinit var description : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_description)

        var btn_ws = findViewById(R.id.btn_ws) as Button
        btn_ws.setOnClickListener() {
            callWebService()
        }

        name = findViewById(R.id.tv_detail_name) as TextView
        description = findViewById(R.id.tv_detail_description) as TextView

        name.setText(intent.getStringExtra("name"))
        description.setText(intent.getStringExtra("description"))
    }

    fun callWebService(){
        doAsync {
            val result = URL("http://rest-service.guides.spring.io/greeting").readText()
            uiThread {
                Toast.makeText(getActivityContext(),parseJSON(StringBuilder(result)),Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getActivityContext() : Context = this

    fun parseJSON(value: StringBuilder) : String{
        val parser: Parser = Parser()
        val json: JsonObject = parser.parse(value) as JsonObject
        return "Id : ${json.int("id")}, \nContent : ${json.string("content")}"
    }
}