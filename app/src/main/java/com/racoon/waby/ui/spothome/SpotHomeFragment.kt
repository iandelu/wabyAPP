package com.racoon.waby.ui.spothome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.racoon.waby.data.model.Spot
import com.racoon.waby.data.repository.SpotRepository
import com.racoon.waby.databinding.FragmentSpotHomeBinding
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpotHomeFragment @Inject constructor ( spotHomeViewModel: SpotHomeViewModel) : Fragment() {

    private lateinit var spotHomeViewModel: SpotHomeViewModel
    private var _binding: FragmentSpotHomeBinding? = null
    private lateinit var adapter:MySpotAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        spotHomeViewModel =
            ViewModelProvider(this).get(SpotHomeViewModel::class.java)

        _binding = FragmentSpotHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MySpotAdapter(context)
        binding.spotList.layoutManager = LinearLayoutManager(context)
        binding.spotList.adapter = adapter

    }

    fun observeData(){
        spotHomeViewModel.getSpotList().observe(this, Observer {
            adapter.setSpotList(it)
            adapter.notifyDataSetChanged()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}