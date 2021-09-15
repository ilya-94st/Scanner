package com.example.scanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scanner.BaceFragment.BaceFragment
import com.example.scanner.DataBases.UsersViewModel
import com.example.scanner.databinding.FragmentArrayPersonBinding


class ArrayPersonFragment : BaceFragment<FragmentArrayPersonBinding>() {

    lateinit var mUsersViewModel: UsersViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = CustomRecucler()
        binding.recucler.adapter = adapter
        binding.recucler.layoutManager = LinearLayoutManager(context)
        mUsersViewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
        mUsersViewModel.readAllData.observe(viewLifecycleOwner, {
            user ->
            adapter.saveData(user)
        })
    }
    override fun getFragment(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentArrayPersonBinding.inflate(inflater,container,false)
}


