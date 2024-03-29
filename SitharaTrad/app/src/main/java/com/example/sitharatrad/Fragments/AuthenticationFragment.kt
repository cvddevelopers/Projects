package com.example.sitharatrad.Fragments

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.*
import android.widget.EditText
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.sitharatrad.Admin.AdminHomeActivity

import com.example.sitharatrad.R
import com.example.sitharatrad.User.Fragment.UserProfileFragment
import com.example.sitharatrad.User.UserHomeActivity
import com.example.sitharatrad.databinding.FragmentAuthenticationBinding
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory

import com.google.firebase.auth.*
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.concurrent.TimeUnit


class AuthenticationFragment : Fragment() {
    lateinit var binding: FragmentAuthenticationBinding
    lateinit var firebaseAuth: FirebaseAuth
    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private val sharedPrefile = "sharedPref"
    lateinit var sharedPreferences:SharedPreferences
    lateinit var progressDialog:ProgressDialog
    //lateinit var circularProgressIndicator: CircularProgressIndicator
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_authentication, container, false)
        FirebaseApp.initializeApp(requireContext())
        progressDialog = ProgressDialog(requireActivity())
        progressDialog.setMessage("Please Wait.....")
        progressDialog.show()
        val firebaseAppCheck = FirebaseAppCheck.getInstance()
        firebaseAppCheck.installAppCheckProviderFactory(
            PlayIntegrityAppCheckProviderFactory.getInstance()
        )
        sharedPreferences = requireActivity().getSharedPreferences(sharedPrefile,Context.MODE_PRIVATE)
        firebaseAuth = FirebaseAuth.getInstance()
        val pat = Regex("^[6-9][0-9]{9}$")
        val sharedNameValue = sharedPreferences.getString("userType","defaultname")
        if(sharedNameValue.equals("Admin")){
            findNavController().navigate(R.id.action_authenticationFragment_to_adminAuthFragment)
            activity?.finish()
        }else if (sharedNameValue.equals("User")) {
            //startActivity(Intent(context, UserHomeActivity::class.java))
            navigate()
            //activity?.finish()
        } else{
            progressDialog.dismiss()
            Toast.makeText(context,"Welcome to Sithara Trad",Toast.LENGTH_LONG).show()
        }
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
            if (binding.unumber.text!!.isEmpty()){
                binding.tablayout.error = "Can't Be Empty"
            }else if(!pat.matches(binding.unumber.text!!)){
                binding.tablayout.error = "Enter a valid number"
            }else {
                val number = binding.unumber.text
                phoneAuthentication(number)
                val view: View = layoutInflater.inflate(R.layout.dailog_screen, null)
                var editText: EditText = view.findViewById(R.id.uotp)
                val builder = AlertDialog.Builder(context)
                builder.create()
                builder.setView(view)
                builder.setPositiveButton(
                    "Verify",
                    DialogInterface.OnClickListener { dialog, which ->
                        verifyPhoneNumberWithCode(storedVerificationId, editText.text.toString())
                        progressDialog.show()
                    })
                builder.setNegativeButton(
                    "Resend",
                    DialogInterface.OnClickListener { dialog, which ->
                        resendVerificationCode("+91 " + number, resendToken)
                    })
                builder.setNeutralButton(
                    "Cancel",
                    DialogInterface.OnClickListener { dialog, which ->
                        dialog.cancel()
                    })
                builder.show()
//            progressDialog.dismiss()
            }
        }

        val menuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.mymenu, menu)
                menu.getItem(0).icon = ResourcesCompat.getDrawable(resources,R.drawable.ic_person,null)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action -> {
                        findNavController().navigate(R.id.action_authenticationFragment_to_adminAuthFragment)
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

                    val editor: SharedPreferences.Editor =  sharedPreferences.edit()
                    editor.putString("userType","User")
                    editor.apply()

                    //startActivity(Intent(context, UserHomeActivity::class.java))
                    //startActivity(Intent(context,UserProfileFragment::class.java))
                    //activity?.finish()
                    //val user = task.result?.user
                    navigate()
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
    fun navigate(){
        firebaseAuth = FirebaseAuth.getInstance()
        var reference:DatabaseReference = FirebaseDatabase.getInstance().getReference("Profiles")
        reference.child(firebaseAuth.currentUser!!.phoneNumber.toString()).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    progressDialog.dismiss()
                    Toast.makeText(context,"Welcome Back",Toast.LENGTH_LONG).show()
                    startActivity(Intent(context, UserHomeActivity::class.java))
                    activity?.finish()
                }
                else{
                    progressDialog.dismiss()
                    Toast.makeText(context,"No data Available , Please fill the details",Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.action_authenticationFragment_to_userProfileFragment)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,""+error,Toast.LENGTH_LONG).show()
            }
    })


}
}