package com.koushik.kotlinmvvmdemo.ui.coindetail

import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import com.koushik.kotlinmvvmdemo.data.model.CoinDetail
import com.koushik.kotlinmvvmdemo.databinding.FragmentCoinDetailBinding
import com.koushik.kotlinmvvmdemo.ui.coindetail.viewmodel.CoinDetailViewModel
import com.koushik.kotlinmvvmdemo.utils.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CoinDetailFragment : Fragment() {

    private var coinId: String? = null

    private var _binding: FragmentCoinDetailBinding? = null
    private val binding get() = _binding!!

    private val coinDetailViewModel by viewModels<CoinDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            coinId = CoinDetailFragmentArgs.fromBundle(it).coinId
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCoinDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchCoinDetailAndRender()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fetchCoinDetailAndRender() {
        fetchCoinDetail()

        coinDetailViewModel.response.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let {
                        renderCoinDetail(response.data)
                    }
                    binding.pbLoader.visibility = View.GONE
                }

                is Resource.Error -> {
                    binding.pbLoader.visibility = View.GONE
                    Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
                }

                is Resource.Loading -> {
                    binding.pbLoader.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun fetchCoinDetail() {
        coinDetailViewModel.getCoinById(coinId!!)
        binding.pbLoader.visibility = View.VISIBLE
    }

    private fun renderCoinDetail(coinDetail: CoinDetail) {
        binding.tvName.text =
            String.format("%s. %s (%s)", coinDetail.rank, coinDetail.name, coinDetail.symbol)
        binding.tvStatus.text = if (coinDetail.isActive) "active" else "inactive"
        val color = if (coinDetail.isActive) Color.GREEN else Color.RED
        binding.tvStatus.setTextColor(color)
        binding.tvDesc.text = coinDetail.description

        binding.tvTag.visibility =
            if (coinDetail.tags.isNotEmpty()) View.VISIBLE else View.INVISIBLE
        setTag(coinDetail.tags)
    }

    private fun setTag(tagList: List<String>) {
        binding.cgTag.removeAllViews()

        for (index in tagList.indices) {
            val tagName = tagList[index]
            val chip = Chip(context)
            val paddingDp = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 8f,
                resources.displayMetrics
            ).toInt()
            chip.setPadding(paddingDp, paddingDp, paddingDp, paddingDp)
            chip.text = tagName
            binding.cgTag.addView(chip)
        }
    }
}