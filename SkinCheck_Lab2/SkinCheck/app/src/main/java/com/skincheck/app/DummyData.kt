package com.skincheck.app

/** Beispieldaten nur fuer die GUI (Lab 2). In Lab 3 kommen die Daten aus der lokalen Datenbank. */
object DummyData {

    private const val SRC = "Beispieldaten (spaeter: CosIng / Open Beauty Facts)"

    private val aqua = Ingredient(
        "Aqua", "Lösungsmittel", Rating.GREEN,
        "Wasser, Grundlage fast aller Pflegeprodukte. Unproblematisch.", SRC
    )
    private val glycerin = Ingredient(
        "Glycerin", "Feuchthaltemittel", Rating.GREEN,
        "Bindet Feuchtigkeit in der Haut und wird im Allgemeinen gut vertragen.", SRC
    )
    private val tocopherol = Ingredient(
        "Tocopherol", "Antioxidans", Rating.GREEN,
        "Vitamin E. Schützt die Haut und das Produkt vor Oxidation.", SRC
    )
    private val panthenol = Ingredient(
        "Panthenol", "Hautberuhigend", Rating.GREEN,
        "Provitamin B5. Beruhigt und unterstützt die Hautbarriere.", SRC
    )
    private val alcoholDenat = Ingredient(
        "Alcohol Denat.", "Lösungsmittel", Rating.YELLOW,
        "Kann trockene und empfindliche Haut austrocknen oder reizen.", SRC
    )
    private val parfum = Ingredient(
        "Parfum", "Duftstoff", Rating.RED,
        "Duftstoffe gehören zu den häufigsten Kontaktallergenen.", SRC
    )
    private val retinol = Ingredient(
        "Retinol", "Wirkstoff (Vitamin A)", Rating.YELLOW,
        "Kann Haut reizen und lichtempfindlich machen. In Schwangerschaft und Stillzeit vorsichtig verwenden.", SRC
    )
    private val limonene = Ingredient(
        "Limonene", "Duftstoff", Rating.RED,
        "Muss in der EU als Duftstoff-Allergen deklariert werden.", SRC
    )
    private val zinc = Ingredient(
        "Zinc Oxide", "UV-Filter (mineralisch)", Rating.GREEN,
        "Mineralischer Lichtschutzfilter, gilt als gut verträglich.", SRC
    )
    private val sles = Ingredient(
        "Sodium Laureth Sulfate", "Tensid", Rating.YELLOW,
        "Reinigt gründlich, kann empfindliche Haut reizen.", SRC
    )

    val products: MutableList<Product> = mutableListOf(
        Product(1, "Feuchtigkeitscreme Sensitiv", "DemoCare", "4001234567890",
            listOf(aqua, glycerin, alcoholDenat, parfum, tocopherol), isFavorite = true),
        Product(2, "Retinol Nachtserum", "DemoDerm", "4009876543210",
            listOf(aqua, retinol, limonene, tocopherol)),
        Product(3, "Sonnencreme SPF 30", "SunDemo", "4005555555555",
            listOf(aqua, zinc, glycerin, tocopherol), isFavorite = true),
        Product(4, "Mildes Waschgel", "PureDemo", "4001111222233",
            listOf(aqua, sles, glycerin, panthenol))
    )

    fun byId(id: Int): Product? = products.firstOrNull { it.id == id }
    fun byBarcode(code: String): Product? = products.firstOrNull { it.barcode == code }
}
