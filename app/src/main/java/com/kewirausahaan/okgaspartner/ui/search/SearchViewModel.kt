package com.kewirausahaan.okgaspartner.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.collections.getValue

class SearchViewModel : ViewModel() {

    private val _orderSearchList = MutableLiveData<List<OrderSearch>>()
    val orderSearchList: LiveData<List<OrderSearch>> = _orderSearchList

    fun getOrderSearchData() {
        val database = FirebaseDatabase.getInstance()
        val orderSearchRef = database.getReference("order_search")

        orderSearchRef.addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val orderSearchList = mutableListOf<OrderSearch>()
                for (orderSnapshot in dataSnapshot.children) {
                    val orderSearch = orderSnapshot.getValue(OrderSearch::class.java)
                    if (orderSearch != null && orderSearch.status == "Mencari Mitra") {
                        orderSearchList.add(orderSearch)
                    }
                }
                _orderSearchList.value = orderSearchList
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("SearchViewModel", "loadPost:onCancelled", databaseError.toException())
            }
        })
    }
}