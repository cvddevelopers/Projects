package com.example.sitharatrad.Fragments

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.*
import android.widget.EditText
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.sitharatrad.Admin.AdminHomeActivity

import com.example.sitharatrad.R
import com.example.sitharatrad.User.UserHomeActivity
import com.example.sitharatrad.databinding.FragmentAuthenticationBinding
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import java.util.concurrent.TimeUnit


class AuthenticationFragment : Fragment() {
    lateinit var binding: FragmentAuthenticationBinding
    lateinit var firebaseAuth: FirebaseAuth
    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_authentication, container, false)
        firebaseAuth = FirebaseAuth.getInstance()
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                if (e is FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(context, "" + e, Toast.LENGTH_LONG).show()
                } else if (e is FirebaseTooManyRequestsException) {
                    Toast.makeText(context, "" + e, Toast.LENGTH_LONG).show()
                }
            }

            override fun onCodeSent(verificationId: String,token: PhoneAuthProvider.ForceResendingToken) {
                storedVerificationId = verificationId
                resendToken = token
            }
        }
        binding.sendOtp.setOnClickListener {
//            val progressDialog = ProgressDialog(context)
//            progressDialog.setMessage(getString(R.string.hint_text))
//            progressDialog.show()
            val number = binding.unumber.text
            phoneAuthentication(number)
            val view: View = layoutInflater.inflate(R.layout.dailog_screen, null)
            var editText: EditText = view.findViewById(R.id.uotp)
            val builder = AlertDialog.Builder(context)
            builder.create()
            //builder.setCancelable(false)
            builder.setView(view)
            builder.setPositiveButton("Verify", DialogInterface.OnClickListener { dialog, which ->
                verifyPhoneNumberWithCode(storedVerificationId, editText.text.toString())
            })
            builder.setNegativeButton("Resend", DialogInterface.OnClickListener { dialog, which ->
                resendVerificationCode("+91 " + number, resendToken)
            })
            builder.setNeutralButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()
            })
            builder.show()
//            progressDialog.dismiss()

        }

        val menuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.mymenu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.person -> {
                        findNavController().navigate(R.id.action_authenticationFragment_to_adminAuthFragment)
                        true
                    }
                    R.id.info -> {
                        Toast.makeText(
                            context,
                            "Admin Info Will be Displayed Here",
                            Toast.LENGTH_LONG
                        ).show()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return binding.root
    }

    private fun phoneAuthentication(number: Editable?) {
        val options = activity?.let {
            PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber("+91 " + number.toString())       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this.requireActivity())                 // Activity (for callback binding)
                .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
                .build()
        }
        if (options != null) {
            PhoneAuthProvider.verifyPhoneNumber(options)
        }
    }

    private fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        signInWithPhoneAuthCredential(credential)

    }

    private fun resendVerificationCode(phoneNumber: String,token: PhoneAuthProvider.ForceResendingToken?) {
        val optionsBuilder = activity?.let {
            PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber("+91 " + phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(it)
                .setCallbacks(callbacks)
        }          // OnVerificationStateChangedCallbacks
        if (token != null) {
            optionsBuilder?.setForceResendingToken(token)
        }
        PhoneAuthProvider.verifyPhoneNumber(optionsBuilder!!.build())
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                   // findNavController().navigate(R.id.action_authenticationFragment_to_userProfileFragment)
                    startActivity(Intent(context, UserHomeActivity::class.java))
                    activity?.finish()
                    val user = task.result?.user
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }

    }


}