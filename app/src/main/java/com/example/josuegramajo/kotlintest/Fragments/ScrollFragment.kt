package com.example.josuegramajo.kotlintest.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.josuegramajo.kotlintest.R

/**
 * Created by josuegramajo on 6/8/17.
 */

class ScrollFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_scroll,container,false)
        val toolbar = view.findViewById(R.id.scroll_toolbar) as Toolbar
        val tv_description = view.findViewById(R.id.description) as TextView
        toolbar.title = arguments.getString("name")
        if(!arguments.getString("description").equals("")){
            tv_description.setText(arguments.getString("description"))
        }else{
            tv_description.setText(resources.getString(R.string.large_text))
        }

        return view
    }
}