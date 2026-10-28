package com.skincheck.app

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.skincheck.app.databinding.DialogIngredientBinding
import com.skincheck.app.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!
    private lateinit var product: Product

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getInt("productId") ?: 1
        product = DummyData.byId(id) ?: DummyData.products.first()
        val ctx = requireContext()

        binding.tvName.text = product.name
        binding.tvBrand.text = product.brand
        binding.tvBarcode.text = getString(R.string.result_barcode, product.barcode)

        // grosse Gesamtbewertung in Gruen / Gelb / Rot
        binding.cardRating.setCardBackgroundColor(ContextCompat.getColor(ctx, product.rating.colorRes))
        binding.tvRatingBig.text = getString(product.rating.labelRes)
        binding.tvRatingBig.setTextColor(
            if (product.rating == Rating.YELLOW) Color.BLACK else Color.WHITE
        )

        binding.rvIngredients.adapter = IngredientAdapter(product.ingredients) { showDetails(it) }

        updateFavoriteIcon()
        binding.btnFav.setOnClickListener {
            product.isFavorite = !product.isFavorite
            updateFavoriteIcon()
        }
        binding.btnBack.setOnClickListener { findNavController().popBackStack() }
    }

    private fun updateFavoriteIcon() {
        val color = if (product.isFavorite) R.color.skin_gold else R.color.gray
        binding.btnFav.setColorFilter(ContextCompat.getColor(requireContext(), color))
    }

    // Inhaltsstoff-Details als Dialog (Name, Funktion, Erklaerung der Farbe, Quelle)
    private fun showDetails(ingredient: Ingredient) {
        val d = DialogIngredientBinding.inflate(layoutInflater)
        val color = ContextCompat.getColor(requireContext(), ingredient.rating.colorRes)
        d.viewDot.backgroundTintList = ColorStateList.valueOf(color)
        d.tvRating.text = getString(ingredient.rating.labelRes)
        d.tvFunction.text = ingredient.function
        d.tvExplanation.text = ingredient.explanation
        d.tvSource.text = ingredient.source

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(ingredient.name)
            .setView(d.root)
            .setPositiveButton(R.string.detail_close, null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
