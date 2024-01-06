package com.donorplus.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.donorplus.R
import com.donorplus.data.DataDarah
import com.donorplus.data.DataUsers
import com.donorplus.databinding.ActivityDetailDonorBinding
import com.donorplus.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.concurrent.TimeUnit

class DetailDonorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailDonorBinding
    private lateinit var db: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailDonorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance().getReference("Data_Darah")
        val id = intent.getStringExtra(EXTRA_ID)

        fetchData(id)

    }

    private fun fetchData(id: String?) {
        db.child(id!!).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.getValue(DataDarah::class.java)
                binding.apply {
                    data.let {
                        if (it != null) {
                           tvDetailExp.text = it.expiredDate
                            tvDetailTpDarah.text = it.tipeDarah
                            tvDetailLocation.text = it.location
                            if (it.sts) {
                                tvDetailSts.text = "Tersedia"
                            } else {
                                tvDetailSts.text = "Tidak Tersedia"
                            }



                            btnDetailPesan.setOnClickListener {
                                val user = data?.let { it1 ->
                                    DataUsers(data.id,data.tipeDarah,data.expiredDate,
                                        it1.location,false, auth.currentUser?.uid.toString())
                                }

                                val newSts = hashMapOf<String, Any> ("sts" to false)

                                db.child(id).updateChildren(newSts)
                                db = FirebaseDatabase.getInstance().getReference("Users").child(auth.currentUser?.uid.toString())
                                db.push().setValue(user)
                                val intent = Intent(this@DetailDonorActivity,ActivityMainBinding::class.java)
                                startActivity(intent)
                            }
                        }

                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }

        )
    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }

}