package com.kewirausahaan.okgaspartner.ui.move

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kewirausahaan.okgaspartner.databinding.FragmentMoveBinding

class MoveFragment : Fragment() {

    private var _binding: FragmentMoveBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MoveViewModel
    private lateinit var adapter: OrderMoveAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(MoveViewModel::class.java)
        adapter = OrderMoveAdapter()

        binding.rvSearch.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSearch.adapter = adapter

        // Mengamati data dari ViewModel
        viewModel.orderMoveList.observe(viewLifecycleOwner) { orderMoveList ->
            adapter.submitList(orderMoveList)
        }

        // Memanggil fungsi untuk mengambil data dari Firebase
        viewModel.getOrderMoveData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}