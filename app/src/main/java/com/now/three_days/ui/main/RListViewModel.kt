package com.now.three_days.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
import com.now.three_days.data.model.RelayDTO
import com.now.three_days.service.impl.RelayServiceImplV1

class RListViewModel : ViewModel() {

//    private val _data = MutableLiveData<List<RelayVO>>()
//    val data:LiveData<List<RelayVO>> get() = _data

    private lateinit var rs: RelayServiceImplV1

    var rList: MutableLiveData<List<RelayDTO>> = MutableLiveData()

//    init {
//        _data.value = listOf()
//    }

    fun list(): MutableLiveData<List<RelayDTO>> {
        rs = RelayServiceImplV1()
        rs.select("릴레이").addSnapshotListener(EventListener<QuerySnapshot> { snapshot, exception ->
            val data: MutableLiveData<List<RelayDTO>> = MutableLiveData()
            var list: MutableList<RelayDTO> = mutableListOf()
            if (exception != null) {
                // w 로 해야지 exception 받아짐
                Log.w("파이어 베이스 ㅋ", exception)
                data.value = null
                return@EventListener
            }
            for (doc in snapshot!!) {
                var seq = doc.id
                var obj = doc.toObject(RelayDTO::class.java)
                obj.r_seq = seq
                list.add(obj)
            }
            rList.value = list
        })

        return rList
    }

}