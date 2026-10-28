package com.skincheck.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.skincheck.app.databinding.FragmentScanBinding

class ScanFragment : Fragment() {

    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding!!
    private var flashOn = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnClose.setOnClickListener { findNavController().popBackStack() }

        // Taschenlampe: hier nur die Optik (Echte Taschenlampe: Lab 3 / CameraX)
        binding.btnFlash.setOnClickListener {
            flashOn = !flashOn
            val color = if (flashOn) R.color.skin_yellow else R.color.white
            binding.btnFlash.setColorFilter(ContextCompat.getColor(requireContext(), color))
        }

        // Scan simulieren: zufaelliges Beispielprodukt oeffnen
        binding.btnSimulate.setOnClickListener {
            openResult(DummyData.products.random().id)
        }

        binding.btnSearch.setOnClickListener { searchManually() }
        binding.etBarcode.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchManually()
                true
            } else false
        }
    }

    private fun searchManually() {
        val code = binding.etBarcode.text?.toString()?.trim().orEmpty()
        if (code.length !in 8..14) {
            binding.tilBarcode.error = getString(R.string.scan_error)
            return
        }
        binding.tilBarcode.error = null

        val product = DummyData.byBarcode(code)
        if (product != null) {
            openResult(product.id)
        } else {
            Snackbar.make(binding.root, R.string.scan_not_found, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun openResult(productId: Int) {
        findNavController().navigate(
            R.id.action_scan_to_result, bundleOf("productId" to productId)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
