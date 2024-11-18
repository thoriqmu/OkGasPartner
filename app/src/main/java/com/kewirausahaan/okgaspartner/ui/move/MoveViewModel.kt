package com.kewirausahaan.okgaspartner.ui.move

import android.util.Log
import androidx.fragment.app.add
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MoveViewModel : ViewModel() {

    private val _orderMoveList = MutableLiveData<List<OrderMove>>()
    val orderMoveList: LiveData<List<OrderMove>> = _orderMoveList

    fun getOrderMoveData() {
        val database = FirebaseDatabase.getInstance()
        val orderMoveRef = database.getReference("order_move")

        orderMoveRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val orderMoveList = mutableListOf<OrderMove>()
                for (orderSnapshot in dataSnapshot.children) {
                    val orderMove = orderSnapshot.getValue(OrderMove::class.java)
                    if (orderMove != null && orderMove.status == "Mencari Mitra") {
                        orderMoveList.add(orderMove)
                    }
                }
                _orderMoveList.value = orderMoveList
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("MoveViewModel", "loadPost:onCancelled", databaseError.toException())
            }
        })
    }
}