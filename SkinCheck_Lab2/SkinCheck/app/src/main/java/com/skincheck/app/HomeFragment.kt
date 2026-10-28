package com.skincheck.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.skincheck.app.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ProductAdapter(DummyData.products.take(3)) { product ->
            findNavController().navigate(
                R.id.action_home_to_result, bundleOf("productId" to product.id)
            )
        }
        binding.rvRecent.adapter = adapter

        binding.btnScan.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_scan)
        }
        binding.btnHistory.setOnClickListener {
            findNavController().navigate(R.id.historyFragment)
        }
        binding.btnFavorites.setOnClickListener {
            findNavController().navigate(R.id.historyFragment, bundleOf("filter" to "fav"))
        }
        binding.btnProfile.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.update(DummyData.products.take(3)) // Favoriten-Sterne aktualisieren
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
