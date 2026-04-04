package com.kopim.productlist.data.model.profile

import androidx.core.graphics.toColorInt
import java.util.Locale
import kotlin.random.Random

/**
 * Цвет профиля в виде строки, в том же смысле что и [com.kopim.productlist.data.model.network.apimodels.getcart.GetCartResponseData.GetCartResponseItemData.user_color]
 * (парсинг через [String.toColorInt]).
 */
object ProfileColorString {

    const val DEFAULT = "#FF808080"

    /** Случайный непрозрачный цвет в формате `#AARRGGBB`. */
    fun randomOpaque(): String {
        val r = Random.nextInt(256)
        val g = Random.nextInt(256)
        val b = Random.nextInt(256)
        return String.format(Locale.US, "#FF%02X%02X%02X", r, g, b)
    }

    fun normalizeOrDefault(input: String?): String {
        if (input.isNullOrBlank()) return DEFAULT
        val s = input.trim()
        val candidate = if (s.startsWith("#")) s else "#$s"
        return try {
            candidate.toColorInt()
            candidate
        } catch (_: IllegalArgumentException) {
            DEFAULT
        }
    }
}
