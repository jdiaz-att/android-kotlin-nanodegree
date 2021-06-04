package com.udacity.shoestore.models

import androidx.core.util.PatternsCompat.EMAIL_ADDRESS
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.R
import timber.log.Timber

class LoginModel : ViewModel() {

    enum class Result(val stringId: Int) {
        EMAIL_INVALID(R.string.email_invalid),
        PASSWORD_INVALID(R.string.password_invalid),
        BOTH_INVALID(R.string.both_invalid),
        OK_CREATE(R.string.ok_create),
        OK_LOGIN(R.string.ok_login)
    }

    private val _result = MutableLiveData<Result>()
    val result: LiveData<Result>
        get() = _result

    fun processInput(email: String, password: String, isLogin: Boolean) {
        Timber.d("Login - email: $email / pwd: $password. isLogin: $isLogin")
        val emailOK = verifyEmail(email)
        val passwordOK = verifyPassword(password)
        _result.value = if (emailOK && passwordOK) {
            if (isLogin) Result.OK_LOGIN else Result.OK_CREATE
        } else if (!emailOK && !passwordOK) {
            Result.BOTH_INVALID
        } else if (!emailOK) {
            Result.EMAIL_INVALID
        } else {
            Result.PASSWORD_INVALID
        }
    }

    private fun verifyEmail(email: String) : Boolean {
        return EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun verifyPassword(password: String) : Boolean {
        return password.length >= MIN_PASSWORD_LENGTH
    }

    companion object {
        private const val MIN_PASSWORD_LENGTH = 4
    }
}