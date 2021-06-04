package com.udacity.shoestore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.databinding.ShoeDetailFragmentBinding
import com.udacity.shoestore.models.ShoeModel

class ShoeDetailFragment : Fragment() {

    private val shoeModel: ShoeModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: ShoeDetailFragmentBinding = DataBindingUtil.inflate(inflater,
            R.layout.shoe_detail_fragment, container, false)
        binding.lifecycleOwner = this

        binding.saveButton.setOnClickListener {
            shoeModel.addShoe(binding.shoeNameText.text.toString(),
                binding.companyText.text.toString(), binding.shoeSizeText.text.toString(),
                binding.descriptionText.text.toString())
        }

        shoeModel.errorMessageId.observe(viewLifecycleOwner, Observer { id ->
            if (id > 0) {
                Toast.makeText(context, id, LENGTH_LONG).show()
            } else if (id ==0) {
                val action = ShoeDetailFragmentDirections.actionShoeDetailFragmentToShoeListFragment()
                findNavController().navigate(action)
            }
        })
        return binding.root
    }
}