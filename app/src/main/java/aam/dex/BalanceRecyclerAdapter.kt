package aam.dex

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_wallet.view.*
import kotlin.random.Random

class BalanceRecyclerAdapter : RecyclerView.Adapter<BalanceRecyclerAdapter.BalanceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BalanceViewHolder {
        return BalanceViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_wallet, parent, false))
    }

    override fun getItemCount(): Int {
        return 40
    }

    override fun onBindViewHolder(holder: BalanceViewHolder, position: Int) {
        holder.bind()
    }


    inner class BalanceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind() {
            Glide.with(itemView.context).load(ColorDrawable(Color.GRAY)).into(itemView.assetImageView)
            itemView.assetNameTextView.text = "BTS" + Random.nextInt(100)
            itemView.assetBalanceTextView.text = Random.nextInt(500000).toString()

            itemView.setOnClickListener { itemView.context.startActivity(Intent(itemView.context, SendActivity::class.java)) }
        }

    }
}