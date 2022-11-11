package com.quiza.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.quiza.R


class ExplainFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_explain, container, false)
        var text: TextView = view.findViewById(R.id.explainText)
       // val extras = intent.extras
        //text.setText(extras!!.getString("explainText"))
        return view
    }


}