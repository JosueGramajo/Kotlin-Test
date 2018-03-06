package com.example.josuegramajo.kotlintest.Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.josuegramajo.kotlintest.R


/**
 * Created by josuegramajo on 6/7/17.
 */

class LandingFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_landing,container,false)
        return view
    }
}