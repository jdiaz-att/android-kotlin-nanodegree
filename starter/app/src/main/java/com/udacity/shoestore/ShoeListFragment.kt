package com.udacity.shoestore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.databinding.ShoeListFragmentBinding
import com.udacity.shoestore.models.ShoeModel

class ShoeListFragment : Fragment() {

    private val shoeModel: ShoeModel by activityViewModels()
    private lateinit var binding: ShoeListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.shoe_list_fragment, container, false)
        binding.lifecycleOwner = this

        binding.floatingActionButton.setOnClickListener {
            val action = ShoeListFragmentDirections.actionShoeListFragmentToShoeDetailFragment()
            findNavController().navigate(action)
        }

        shoeModel.errorMessageDisplayed()
        shoeModel.shoeList.observe(viewLifecycleOwner, Observer { list ->
            for (shoe in list) {
                addShoeToList(shoe.name)
            }
        })
        return binding.root
    }

    private fun addShoeToList(name: String) {
        val shoeTextView = TextView(context, null, R.style.ShoeTextViewStyle)
        shoeTextView.text = name
        binding.shoeLinearLayout.addView(shoeTextView)
    }
}