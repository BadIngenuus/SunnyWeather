package com.android.sunnyweather.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.sunnyweather.R
import com.android.sunnyweather.viewmodel.PlaceViewModel
import kotlinx.android.synthetic.main.fragment_place.*

class PlaceFragment : Fragment() {

    private lateinit var mAdapter : PlaceAdapter
    private val mViewModel by lazy {
        ViewModelProvider(this).get(PlaceViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_place,container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAdapter = PlaceAdapter()
        recyclerView.adapter = mAdapter
        editQUery.addTextChangedListener{
            val queryPlace = it.toString()
            if (queryPlace.isNotEmpty())
                mViewModel.searchPlace(queryPlace)
        }
        mViewModel.placeList.observe(viewLifecycleOwner, Observer {
            val places = it.getOrNull()
            if (places !=null){
                mAdapter.submitList(places)
                recyclerView.visibility = View.VISIBLE
            }else if (it.isFailure){
                recyclerView.visibility = View.INVISIBLE
                Toast.makeText(activity,"没有搜索到该地点",Toast.LENGTH_SHORT).show()
                it.exceptionOrNull()?.printStackTrace()
            }
        })
    }
    
    companion object{
//        private const val TAG = "PlaceFragment"
    }
}