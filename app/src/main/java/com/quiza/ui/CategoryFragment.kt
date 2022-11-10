package com.quiza.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import com.quiza.OnDataPass

import com.quiza.R


class CategoryFragment : Fragment(),OnDataPass {
    private lateinit var android_category: ImageButton
    lateinit var dataPasser: OnDataPass


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_category, container, false)
        android_category = view.findViewById(R.id.android_category)
        android_category.setOnClickListener {
            findNavController().navigate(R.id.categoryFragment_to_quizFragment)
        }

        return view
    }



    override fun onDataPass(data: String) {
        dataPasser.onDataPass(data)
    }


}



