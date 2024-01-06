package com.donorplus.ui.history

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.donorplus.data.DataUsers
import com.donorplus.databinding.ItemMainBinding
import com.donorplus.ui.detail.DetailDonorActivity

class HistoryAdapter(private val users: ArrayList<DataUsers>): RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    class ViewHolder (val binding: ItemMainBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.ViewHolder =
        ViewHolder(ItemMainBinding.inflate(LayoutInflater.from(parent.context),parent, false))

    override fun onBindViewHolder(holder: HistoryAdapter.ViewHolder, position: Int){
        val currentItem = users[position]
        holder.apply {
            binding.apply {
                tvItemExp.text = "Expired Sebelum ${currentItem.expiredDate}"
                tvItemGolonganDarah.text = currentItem.tipeDarah
                tvItemLocation.text = currentItem.location
                if (currentItem.sts) {
                    tvItemSts.text = "Tersedia"
                } else {
                    tvItemSts.text = "Kantong Darah Sedang Kamu Ambil"
                }
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailDonorActivity::class.java)
                    intent.apply {
                        putExtra(DetailDonorActivity.EXTRA_ID, currentItem.id)
                    }
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun getItemCount(): Int = users.size
}