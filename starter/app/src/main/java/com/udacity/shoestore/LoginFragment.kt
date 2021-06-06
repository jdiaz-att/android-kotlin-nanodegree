package com.udacity.shoestore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.databinding.LoginFragmentBinding
import com.udacity.shoestore.models.LoginModel

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: LoginFragmentBinding = DataBindingUtil.inflate(inflater,
            R.layout.login_fragment, container, false)
        binding.lifecycleOwner = this

        val viewModel = ViewModelProvider(this).get(LoginModel::class.java)
        viewModel.result.observe(viewLifecycleOwner, Observer { result ->
            val message = context?.getString(result.stringId)
            Toast.makeText(context, message, LENGTH_SHORT).show()
            if (result == LoginModel.Result.OK_CREATE || result == LoginModel.Result.OK_LOGIN) {
                val action = LoginFragmentDirections.actionLoginFragmentToWelcomeFragment()
                findNavController().navigate(action)
            }
        })

        binding.buttonCreate.setOnClickListener {
            viewModel.processInput(binding.emailText.text.toString(),
                    binding.passwordText.text.toString(), false)
        }
        binding.buttonLogin.setOnClickListener {
            viewModel.processInput(binding.emailText.text.toString(),
                    binding.passwordText.text.toString(), true)
        }

        return binding.root
    }
}