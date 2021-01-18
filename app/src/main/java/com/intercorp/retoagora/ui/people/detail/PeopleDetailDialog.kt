package com.intercorp.retoagora.ui.people.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.intercorp.retoagora.data.response.Result
import com.intercorp.retoagora.R
import kotlinx.android.synthetic.main.dialog_people_detail.*

class PeopleDetailDialog : DialogFragment() {

    lateinit var people: Result

    companion object {
        const val KEY_PEOPLE = "KEY_PEOPLE"

        fun newInstance(people: Result): PeopleDetailDialog {
            val args = Bundle()
            args.putSerializable(KEY_PEOPLE, people)
            val fragment = PeopleDetailDialog()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MyDialog);
        arguments?.let { people = it.getSerializable(KEY_PEOPLE) as Result }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_people_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name = view.findViewById<TextView>(R.id.tvName)
        name.text = people.name
        val height = view.findViewById<TextView>(R.id.tvHeight)
        height.text = "Tamaño: "+people.height
        val mass = view.findViewById<TextView>(R.id.tvMass)
        mass.text = "Masa: "+people.mass
        val hair = view.findViewById<TextView>(R.id.tvHairColor)
        hair.text = "Color de Cabello: "+people.hairColor
        val skin = view.findViewById<TextView>(R.id.tvSkinColor)
        skin.text = "Color de piel: "+people.skinColor
        val eye = view.findViewById<TextView>(R.id.tvEyeColor)
        eye.text = "Color de ojos: "+people.eyeColor
        val birthyear = view.findViewById<TextView>(R.id.tvBirthyear)
        birthyear.text = "Año de Nacimiento: "+people.birthYear
        val gender = view.findViewById<TextView>(R.id.tvGender)
        gender.text = "Gènero: "+people.gender
        btnAcceot.setOnClickListener {
            dismiss()
        }

    }

}