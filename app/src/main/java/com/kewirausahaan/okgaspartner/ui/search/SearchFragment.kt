package com.kewirausahaan.okgaspartner.ui.search

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kewirausahaan.okgaspartner.R
import com.kewirausahaan.okgaspartner.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SearchViewModel
    private lateinit var adapter: OrderSearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        adapter = OrderSearchAdapter()

        binding.addKostButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_search_to_navigation_add_kost)
        }

        binding.rvSearch.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSearch.adapter = adapter

        // Mengamati data dari ViewModel
        viewModel.orderSearchList.observe(viewLifecycleOwner) { orderSearchList ->
            adapter.submitList(orderSearchList)
        }

        // Memanggil fungsi untuk mengambil data dari Firebase
        viewModel.getOrderSearchData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}