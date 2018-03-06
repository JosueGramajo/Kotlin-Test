package com.example.josuegramajo.kotlintest.Fragments

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import com.example.josuegramajo.kotlintest.Activities.MainActivity
import com.example.josuegramajo.kotlintest.Adapters.CardViewAdapter
import com.example.josuegramajo.kotlintest.Objects.Activities
import com.example.josuegramajo.kotlintest.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.alert

/**
 * Created by josuegramajo on 6/7/17.
 */

class CardViewFragment : Fragment(){
    var arreglo = arrayListOf<Activities>()
    lateinit var recycler : RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_recycler_view,container,false)

        arreglo.add(Activities("Natacion","100 mts pecho"))

        recycler = view.findViewById(R.id.my_recycler_view) as RecyclerView
        recycler.layoutManager = GridLayoutManager(activity,1);
        recycler.adapter = CardViewAdapter(arreglo) {
            changeFragment(it.name,it.description)
        }

        val addFloatingButton = view.findViewById(R.id.fb_add_button) as FloatingActionButton
        addFloatingButton.setOnClickListener{
            addFloatingButton -> this.makeAnkoAlert()
        }
        return view
    }

    fun changeFragment(nombre:String, descripcion:String){
        arreglo.clear()
        val bundle = Bundle()
        bundle.putString("name",nombre)
        bundle.putString("description",descripcion)
        val scrollView = ScrollFragment()
        scrollView.arguments = bundle
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout,scrollView, MainActivity.fragments.FRAGMENT_SCROLL.name)
        transaction.addToBackStack("")
        transaction.commit()
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
            yesButton { addElement(titleAlert.text.toString(), descriptionAlert.text.toString()) }
            noButton { }
        }.show()
    }

    fun addElement(title:String, description:String){
        arreglo.add(Activities(title,description))
        recycler.adapter.notifyDataSetChanged()
    }
}