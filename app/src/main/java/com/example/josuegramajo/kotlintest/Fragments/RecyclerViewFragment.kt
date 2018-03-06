package com.example.josuegramajo.kotlintest.Fragments

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import com.example.josuegramajo.kotlintest.Adapters.RecyclerViewAdapter
import com.example.josuegramajo.kotlintest.Objects.Activities
import com.example.josuegramajo.kotlintest.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.toast

/**
 * Created by josuegramajo on 6/7/17.
 */

class RecyclerViewFragment : Fragment(){

    var arreglo = arrayListOf<Activities>()
    lateinit var recycler : RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_recycler_view,container,false)

        if(savedInstanceState == null){
            arreglo.add(Activities("Natacion","100 mts pecho"))
        }

        recycler = view.findViewById(R.id.my_recycler_view) as RecyclerView
        recycler.layoutManager = GridLayoutManager(activity,1);
        recycler.adapter = RecyclerViewAdapter(arreglo) {
            //startActivity(Intent(this@MainActivity,DetailActivity::class.java))
            //startActivity<DetailActivity>("name" to it.name, "description" to it.description)
            toast(it.name)
        }

        val addFloatingButton = view.findViewById(R.id.fb_add_button) as FloatingActionButton
        addFloatingButton.setOnClickListener(){
            addFloatingButton -> this.makeAnkoAlert()
        }

        return view
    }
    fun makeAnkoAlert(){
        var titleAlert = EditText(activity)
        var descriptionAlert = EditText(activity)

        alert("Alert","Testing alerts") {
            customView{ linearLayout {
                orientation = LinearLayout.VERTICAL
                lparams(width = matchParent)
                padding = dip(26)
                titleAlert = editText{ hint = "Title"; setSingleLine(true) }
                descriptionAlert = editText{ hint = "Description"; setSingleLine(true); topPadding = dip(10) }
            } }
            yesButton { addElement(titleAlert.text.toString(),descriptionAlert.text.toString()) }
            noButton { }
        }.show()
    }
    fun addElement(title:String, description:String){
        arreglo.add(Activities(title,description))
        recycler.adapter.notifyDataSetChanged()
    }
}