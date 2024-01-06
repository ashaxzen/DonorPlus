package com.donorplus.ui.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.service.autofill.UserData
import androidx.recyclerview.widget.LinearLayoutManager
import com.donorplus.data.DataUsers
import com.donorplus.databinding.ActivityHistoryDonorBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HistoryDonorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryDonorBinding
    private lateinit var db: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var usersList: ArrayList<DataUsers>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryDonorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance().getReference("Users").child(auth.currentUser?.uid.toString())

        usersList = arrayListOf()

        fetchData()

        binding.apply {
            rvGoldar.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this.context)
            }
        }


    }

    private fun fetchData() {
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                usersList.clear()
                if(snapshot.exists()) {
                    for (userSnap in snapshot.children) {
                        val users = userSnap.getValue(DataUsers::class.java)
                        usersList.add(users!!)
                    }
                    val usersAdapter = HistoryAdapter(usersList)
                    binding.rvGoldar.adapter = usersAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }

        )
    }
}