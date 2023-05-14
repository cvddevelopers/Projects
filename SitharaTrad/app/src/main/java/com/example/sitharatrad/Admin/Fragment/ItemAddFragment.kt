package com.example.sitharatrad.Admin.Fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import com.example.sitharatrad.Model.Cart
import com.example.sitharatrad.R
import com.example.sitharatrad.databinding.FragmentHomeBinding
import com.example.sitharatrad.databinding.FragmentItemAddBinding
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.UUID


class ItemAddFragment : Fragment() {

    lateinit var binding: FragmentItemAddBinding
    lateinit var reference: DatabaseReference
    lateinit var auth: FirebaseAuth
    lateinit var storageReference: StorageReference
    lateinit var uri: Uri
    lateinit var startForResult: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_item_add, container, false)
        reference = FirebaseDatabase.getInstance().getReference("ItemsData")
        auth = FirebaseAuth.getInstance()
        storageReference = FirebaseStorage.getInstance().getReference().child("images/"+UUID.randomUUID().toString())
        setUpLauncher()
        binding.addPimage.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            intent.putExtra(Intent.EXTRA_LOCAL_ONLY,true)
            startForResult.launch(intent)
        }
        binding.additem.setOnClickListener {
            val pname:String = binding.addPname.text.toString()
            val ptype:String = binding.addPtype.text.toString()
            val pquantity:String = binding.addPquantity.text.toString()
            val poffer:String = binding.addPoffer.text.toString()
            val pcost:String = binding.addPcost.text.toString()
            saveData(pname,ptype,pquantity,poffer,pcost)
        }

        return binding.root
    }
    fun setUpLauncher(){
        startForResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(), ActivityResultCallback<ActivityResult>(){
                if (it.resultCode == Activity.RESULT_OK){
                    val intent = it.data
                    uri = intent?.data!!
                    binding.addPimage.setImageURI(uri)
                }
            }
        )
    }
    fun saveData(pname:String,ptype:String,pquantity:String,poffer:String,pcost:String){
        activity?.let {
            storageReference.putFile(uri).addOnSuccessListener{
            storageReference.downloadUrl.addOnSuccessListener {
                val link = it.toString()
                val cat = binding.spinner.selectedItem.toString()
                val cart = Cart(cat,pname,ptype,pquantity,poffer,pcost,link)
                reference.child(cat).child(pname).setValue(cart)
            }
            }.addOnFailureListener{
                Log.i("RDBERROR",it.toString())
            }
        }
    }


}