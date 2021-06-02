package com.udacity.shoestore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.udacity.shoestore.databinding.ShoeDetailFragmentBinding
import com.udacity.shoestore.databinding.WelcomeFragmentBinding

class ShoeDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: ShoeDetailFragmentBinding = DataBindingUtil.inflate(inflater,
            R.layout.shoe_detail_fragment, container, false)
        return binding.root
    }
}