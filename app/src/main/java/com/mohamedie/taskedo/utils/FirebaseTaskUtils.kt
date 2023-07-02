package com.mohamedie.taskedo.utils

import com.google.android.gms.tasks.Task
import com.mohamedie.taskedo.R
import kotlinx.coroutines.tasks.await

suspend fun <D> Task<D>.awaitResource(): Resource<D> {
    return try {
        Resource.Success(await())
    } catch (e: Exception) {
        val error = e.localizedMessage?.let { UIText.DynamicString(it) }
            ?: UIText.StringResource(R.string.unexpected_error_occurred_please_try_again_later)
        Resource.Error(error)
    }
}