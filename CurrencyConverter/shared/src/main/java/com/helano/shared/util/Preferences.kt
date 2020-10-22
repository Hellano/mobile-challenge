package com.helano.shared.util

import android.content.Context
import androidx.core.content.edit
import com.helano.shared.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Preferences @Inject constructor(@ApplicationContext context: Context) {
    private val prefs = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE)

    var lastUpdate: Long
        get() = prefs.getLong(LAST_UPDATE, 0)
        set(value) = prefs.edit { putLong(LAST_UPDATE, value) }

    var fromCurrencyCode: String
        get() = getCurrencyCode(FROM_CURRENCY_CODE, FROM_CURRENCY_CODE_DEFAULT)
        set(value) = prefs.edit { putString(FROM_CURRENCY_CODE, value) }

    var toCurrencyCode: String
        get() = getCurrencyCode(TO_CURRENCY_CODE, TO_CURRENCY_CODE_DEFAULT)
        set(value) = prefs.edit { putString(TO_CURRENCY_CODE, value) }

    private fun getCurrencyCode(code: String, defaultCode: String): String {
        val currency = prefs.getString(code, "")
        return if (currency.isNullOrEmpty()) {
            prefs.edit { putString(code, defaultCode) }
            defaultCode
        } else {
            currency
        }
    }

    companion object {
        private const val FROM_CURRENCY_CODE_DEFAULT = "USD"
        private const val TO_CURRENCY_CODE_DEFAULT = "BRL"
        private const val FROM_CURRENCY_CODE = "from_currency_code"
        private const val TO_CURRENCY_CODE = "to_currency_code"
        private const val LAST_UPDATE = "last_update"
    }
}