package com.quiza.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.navigation.fragment.findNavController

import com.quiza.R


class CategoryFragment : Fragment() {
    private lateinit var android_category: ImageButton


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


}



