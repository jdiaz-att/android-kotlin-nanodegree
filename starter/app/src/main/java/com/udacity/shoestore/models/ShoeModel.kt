package com.udacity.shoestore.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.R
import timber.log.Timber

class ShoeModel : ViewModel() {

    private val _shoeList = MutableLiveData<MutableList<Shoe>>()
    val shoeList: LiveData<MutableList<Shoe>>
        get() = _shoeList

    private val _errorMessageId = MutableLiveData<Int>()
    val errorMessageId: LiveData<Int>
        get() = _errorMessageId

    init {
        _shoeList.value = ArrayList()
    }

    fun addShoe(name: String, company: String, size: String, description: String) {
        if (!validateTextField(name) || !validateTextField(company) ||
            !validateTextField(description) || !validateShoeSize(size)) {
            _errorMessageId.value = R.string.shoe_detail_text_error
        } else {
            val shoe = Shoe(name, size.toDouble(), company, description)
            _shoeList.value?.add(shoe)
            _shoeList.value = _shoeList.value
            Timber.d("New shoe: $shoe.")
            _errorMessageId.value = 0
        }
    }

    fun errorMessageDisplayed() {
        _errorMessageId.value = -1
    }

    private fun validateTextField(input: String): Boolean {
        return input.length >= MIN_TEXT_LENGTH
    }

    private fun validateShoeSize(size: String): Boolean {
        val sizeDouble = size.toDoubleOrNull()
        return sizeDouble != null && sizeDouble > 0 && sizeDouble <= MAX_SHOE_SIZE
    }

    companion object {
        private const val MIN_TEXT_LENGTH = 3
        private const val MAX_SHOE_SIZE = 30
    }
}