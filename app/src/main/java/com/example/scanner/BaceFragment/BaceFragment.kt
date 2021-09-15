package com.example.scanner.BaceFragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding


abstract class BaceFragment<B: ViewBinding> : Fragment() {
   protected lateinit var binding: B
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
binding = getFragment(inflater, container)
        return binding.root
    }
   abstract fun getFragment(inflater: LayoutInflater, container: ViewGroup?) : B
}