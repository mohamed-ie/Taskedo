package com.mohamedie.taskedo.di.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.mohamedie.taskedo.R
import com.mohamedie.taskedo.data.auth.FirebaseAuthManagerImpl
import com.mohamedie.taskedo.domain.data.AuthManager
import com.mohamedie.taskedo.domain.helpers.TextFieldValidator
import com.mohamedie.taskedo.helpers.TextFieldValidatorImpl
import com.mohamedie.taskedo.ui.auth.sign_up.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule = module(false) {
    single { FirebaseAuth.getInstance() }
    single<TextFieldValidator> { TextFieldValidatorImpl() }
    single<AuthManager> { FirebaseAuthManagerImpl(get()) }

    single {
        val context = get<Context>()
        GoogleSignIn.getClient(context,
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        ).signInIntent
    }

    single<ActivityResultContract<Intent, Task<GoogleSignInAccount>?>> {
        object : ActivityResultContract<Intent, Task<GoogleSignInAccount>?>() {
            override fun createIntent(context: Context, input: Intent): Intent {
                return input
            }

            override fun parseResult(resultCode: Int, intent: Intent?): Task<GoogleSignInAccount>? {
                if (resultCode != Activity.RESULT_OK) {
                    return null
                }
                return GoogleSignIn.getSignedInAccountFromIntent(intent)
            }
        }
    }

    viewModel { SignUpViewModel(get(), get()) }
}