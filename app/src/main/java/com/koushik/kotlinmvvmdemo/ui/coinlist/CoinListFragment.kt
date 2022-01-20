package com.koushik.kotlinmvvmdemo.ui.coinlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.koushik.kotlinmvvmdemo.databinding.FragmentCoinListBinding
import com.koushik.kotlinmvvmdemo.ui.coinlist.adapter.CoinListAdapter
import com.koushik.kotlinmvvmdemo.ui.coinlist.viewmodel.CoinListViewModel
import com.koushik.kotlinmvvmdemo.utils.OnItemClickListener
import com.koushik.kotlinmvvmdemo.utils.Resource
import com.koushik.kotlinmvvmdemo.utils.addOnItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class CoinListFragment : Fragment() {

    private var _binding: FragmentCoinListBinding? = null
    private val binding get() = _binding!!

    private val coinListViewModel by viewModels<CoinListViewModel>()
    private lateinit var coinListAdapter: CoinListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        coinListAdapter = CoinListAdapter(arrayListOf())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCoinListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvCoinList.adapter = coinListAdapter

        fetchCoinsAndRender()

        binding.rvCoinList.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                val action =
                    CoinListFragmentDirections.actionCoinListFragmentToCoinDetailFragment(coinListAdapter.getCurrentCoin(position).id)
                binding.rootLayout.findNavController().navigate(action)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fetchCoinsAndRender() {
        fetchCoins()

        coinListViewModel.response.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let {
                        coinListAdapter.updateCoinList(response.data)
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

    private fun fetchCoins() {
        coinListViewModel.getCoins()
        binding.pbLoader.visibility = View.VISIBLE
    }
}