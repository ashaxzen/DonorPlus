package com.donorplus.ui.donor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.donorplus.R
import com.donorplus.data.DataDarah
import com.donorplus.databinding.ActivityDonorDarahBinding
import com.donorplus.ui.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Calendar

class DonorDarahActivity : AppCompatActivity() {

    lateinit var binding: ActivityDonorDarahBinding
    lateinit var auth: FirebaseAuth
    lateinit var db : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDonorDarahBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = FirebaseDatabase.getInstance().reference

        binding.apply {
            val adapterGolonganDarah = ArrayAdapter.createFromResource(
                this@DonorDarahActivity,
                R.array.filter_options,
                R.layout.item_goldar
            )

            adapterGolonganDarah.setDropDownViewResource(R.layout.item_goldar)
            spGd.adapter = adapterGolonganDarah

            val adapterLocation = ArrayAdapter.createFromResource(
                this@DonorDarahActivity,
                R.array.pmi,
                R.layout.item_goldar
            )

            adapterLocation.setDropDownViewResource(R.layout.item_loc)
            spLocation.adapter = adapterLocation

            btnMulaiDonor.setOnClickListener {

                val tipeDarah = spGd.selectedItem.toString()
                val today = Calendar.getInstance()
                today.add(Calendar.DAY_OF_MONTH, 35)
                val futureDay = today.time
                val sdf = SimpleDateFormat("dd/MM/yyyy")
                val formatDate = sdf.format(futureDay)
                val location = spLocation.selectedItem.toString()
                val id = db.database.getReference("Data_Darah").push().key.toString()

                val dataDarah = DataDarah(id,tipeDarah, formatDate,location, true )

                pushData(dataDarah)
            }
        }

    }

    private fun pushData(dataDarah: DataDarah) {
        auth = FirebaseAuth.getInstance()
        db = FirebaseDatabase
            .getInstance()
            .getReference("Data_Darah").child(dataDarah.id)



        db.setValue(dataDarah).addOnCompleteListener{
            Toast.makeText(this, "Kamu Berhasil Donor Darah",Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}