package android.project.shoppingapp.data.local

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val USER_PREFERENCES_NAME = "user_preferences"


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = USER_PREFERENCES_NAME
)

class DataStoreManager(context: Context) {

    companion object PreferencesKeys {
        val firstTimeLogin = booleanPreferencesKey("first_time_login")
        val isUserAuthenticated = booleanPreferencesKey("is_user_authenticated")
        val userId = stringPreferencesKey("userId")
    }

    private val dataStore = context.dataStore

    val firstTimeLogin: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.firstTimeLogin] ?: true
    }

    val userAuthentication: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.isUserAuthenticated] ?: false
    }

    val userId: Flow<String> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.userId] ?: ""
    }

    suspend fun updateFirstTimeLogin(firstTime: Boolean) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[PreferencesKeys.firstTimeLogin] = firstTime
        }
    }

    suspend fun updateUserAuthentication(userAuth: Boolean) {
        dataStore.edit { preferences ->
            Log.d("DATASTOREUPDATE", userAuth.toString())
            preferences[PreferencesKeys.isUserAuthenticated] = userAuth
        }
    }

    suspend fun setUserId(userId: String?) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.userId] = userId ?: ""
        }
    }



}