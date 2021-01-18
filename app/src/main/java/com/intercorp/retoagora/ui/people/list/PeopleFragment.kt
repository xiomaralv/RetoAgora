package com.intercorp.retoagora.ui.people.list

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.intercorp.retoagora.R
import com.intercorp.retoagora.data.response.Result
import com.intercorp.retoagora.ui.people.detail.PeopleDetailDialog
import kotlinx.android.synthetic.main.fragment_people.*
import org.koin.android.viewmodel.ext.android.viewModel

class PeopleFragment : Fragment() {
    private val peopleListModel: PeopleViewModel by viewModel()
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_people, container, false)
    }

    override fun onStart() {
        super.onStart()
        recyclerView = view!!.findViewById<RecyclerView>(R.id.rvPeople)
        recyclerView.layoutManager = LinearLayoutManager(view!!.context, RecyclerView.VERTICAL, false)
        searchPeople("")
        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchPeople(s.toString())
            }
        })
    }

    fun searchPeople(search:String){
        peopleListModel.getPeople(search)
        peopleListModel.listPeople.observe(this, Observer(function = fun(peopleList: List<Result>?) {
            peopleList?.let {
                val peopleListAdapter = PeopleAdapter(peopleList)
                recyclerView.adapter = peopleListAdapter
                peopleListAdapter.setItemClickListener(object : PeopleAdapter.ItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        val fm: FragmentManager = activity!!.supportFragmentManager
                        val newFragment = PeopleDetailDialog.newInstance(peopleList[position])
                        newFragment.show(fm, "fragment_edit_name")
                    }
                })
            }
        }))

    }
}