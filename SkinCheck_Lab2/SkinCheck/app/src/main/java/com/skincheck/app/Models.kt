package com.skincheck.app

enum class Rating(val labelRes: Int, val colorRes: Int) {
    GREEN(R.string.rating_green, R.color.skin_green),
    YELLOW(R.string.rating_yellow, R.color.skin_yellow),
    RED(R.string.rating_red, R.color.skin_red)
}

data class Ingredient(
    val name: String,
    val function: String,
    val rating: Rating,
    val explanation: String,
    val source: String
)

data class Product(
    val id: Int,
    val name: String,
    val brand: String,
    val barcode: String,
    val ingredients: List<Ingredient>,
    var isFavorite: Boolean = false
) {
    // Gesamtbewertung: schlechteste Farbe gewinnt (Rot > Gelb > Gruen)
    val rating: Rating
        get() = when {
            ingredients.any { it.rating == Rating.RED } -> Rating.RED
            ingredients.any { it.rating == Rating.YELLOW } -> Rating.YELLOW
            else -> Rating.GREEN
        }
}
