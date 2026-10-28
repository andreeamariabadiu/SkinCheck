package com.skincheck.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.skincheck.app.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ProductAdapter(DummyData.products) { product ->
            findNavController().navigate(
                R.id.action_history_to_result, bundleOf("productId" to product.id)
            )
        }
        binding.rvHistory.adapter = adapter

        // Startfilter aus dem Argument (z. B. "fav" vom Home-Button "Favoriten")
        if (arguments?.getString("filter") == "fav") binding.chipFav.isChecked = true

        binding.chipGroup.setOnCheckedStateChangeListener { _, _ -> applyFilter() }
        applyFilter()
    }

    private fun applyFilter() {
        val all = DummyData.products
        val filtered = when (binding.chipGroup.checkedChipId) {
            R.id.chipGreen -> all.filter { it.rating == Rating.GREEN }
            R.id.chipYellow -> all.filter { it.rating == Rating.YELLOW }
            R.id.chipRed -> all.filter { it.rating == Rating.RED }
            R.id.chipFav -> all.filter { it.isFavorite }
            else -> all
        }
        adapter.update(filtered)
        binding.tvEmpty.visibility = if (filtered.isEmpty()) View.VISIBLE else View.GONE
    }

    override fun onResume() {
        super.onResume()
        if (_binding != null) applyFilter() // Favoriten koennen im Ergebnis geaendert worden sein
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
