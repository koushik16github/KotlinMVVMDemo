package com.koushik.kotlinmvvmdemo.ui.coinlist.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.koushik.kotlinmvvmdemo.data.model.Coin
import com.koushik.kotlinmvvmdemo.databinding.ListItemCoinBinding

class CoinListAdapter(
    var coinList: ArrayList<Coin>,
) : RecyclerView.Adapter<CoinListAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ListItemCoinBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemCoinBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(coinList[position]) {
                binding.tvName.text =
                    String.format("%s. %s (%s)", this.rank, this.name, this.symbol)
                binding.tvStatus.text = if (this.isActive) "active" else "inactive"
                val color = if (this.isActive) Color.GREEN else Color.RED
                binding.tvStatus.setTextColor(color)
            }
        }
    }

    override fun getItemCount(): Int {
        return coinList.size
    }

    fun updateCoinList(list: List<Coin>) {
        coinList.clear()
        coinList.addAll(list)
        notifyDataSetChanged()
    }

    fun getCurrentCoin(position: Int) = coinList[position]
}