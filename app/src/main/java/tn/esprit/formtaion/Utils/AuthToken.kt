/*package tn.esprit.formtaion.Utils

import android.content.Context
import android.content.SharedPreferences

class AuthToken private constructor(context: Context) {
    companion object {
        private const val TOKEN = "TOKEN"

        private const val TOKEN_VALUE = "TOKEN_VALUE"

        @Volatile
        private var instance: AuthToken? = null

        fun getInstance(context: Context): AuthToken = instance ?: synchronized(this) {
            AuthToken(context)
                .apply { instance = this }
        }
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(TOKEN, Context.MODE_PRIVATE)

    var token: String? = null
        set(value) = sharedPreferences.edit().putString(TOKEN_VALUE, value).apply()
            .also { field = value }
        get() = field ?: sharedPreferences.getString(TOKEN_VALUE, null)
}*/
// AuthToken.kt

package tn.esprit.formtaion.Utils

import android.content.Context
import android.content.SharedPreferences

class AuthToken private constructor(context: Context) {
    companion object {
        private const val TOKEN = "TOKEN"
        private const val TOKEN_VALUE = "TOKEN_VALUE"
        private const val USER_ID_VALUE = "USER_ID_VALUE"

        @Volatile
        private var instance: AuthToken? = null

        fun getInstance(context: Context): AuthToken = instance ?: synchronized(this) {
            AuthToken(context)
                .apply { instance = this }
        }
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(TOKEN, Context.MODE_PRIVATE)

    var token: String?
        get() = sharedPreferences.getString(TOKEN_VALUE, null)
        set(value) = sharedPreferences.edit().putString(TOKEN_VALUE, value).apply()

    var userId: String?
        get() = sharedPreferences.getString(USER_ID_VALUE, null)
        set(value) = sharedPreferences.edit().putString(USER_ID_VALUE, value).apply()
}