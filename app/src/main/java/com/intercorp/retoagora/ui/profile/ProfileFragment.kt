package com.intercorp.retoagora.ui.profile

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.intercorp.retoagora.R
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    private lateinit var galleryViewModel: ProfileViewModel

    val database = FirebaseDatabase.getInstance()
    val table = database.getReference("users")

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
                ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(ProfileViewModel::class.java)
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    @SuppressLint("ShowToast")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val edtEmail = view.findViewById<TextInputEditText>(R.id.edtEmail)
        val edtName = view.findViewById<TextInputEditText>(R.id.edtName)
        val edtAge = view.findViewById<TextInputEditText>(R.id.edtAge)
        val edtBirthday = view.findViewById<TextInputEditText>(R.id.edtBirthday)
        val prefs = activity?.getSharedPreferences(getString(R.string.preferences_file), Context.MODE_PRIVATE)
        val email = prefs?.getString("email",null)
        if(email!=null){
            edtEmail.setText(email)
            table.child(email.replace(".","")).addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if(dataSnapshot.exists()){
                        val name = dataSnapshot.child("name").value.toString()
                        val age = dataSnapshot.child("age").value.toString()
                        val birthday = dataSnapshot.child("birthday").value.toString()
                        edtName.setText(name)
                        edtAge.setText(age)
                        edtBirthday.setText(birthday)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                }

            })
        }

        edtBirthday.setOnClickListener{showDatePickerDialog()}
        btnSave.setOnClickListener {
            val name = edtName.text.toString()
            val age = edtAge.text.toString()
            val birthday = edtBirthday.text.toString()
            if (email != null && name.isNotBlank() && age.isNotBlank() && birthday.isNotBlank()) {
                table.child(email.replace(".","")).setValue(hashMapOf("name" to name, "age" to age, "birthday" to birthday))
                Toast.makeText(activity,"Guardado con exito",Toast.LENGTH_SHORT)
            }
        }


    }

    private fun showDatePickerDialog() {
        val newFragment = DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
            // +1 because January is zero
            val selectedDate = day.toString() + " / " + (month + 1) + " / " + year
            edtBirthday.setText(selectedDate)
        })

        newFragment.show(fragmentManager!!, "datePicker")
    }
}