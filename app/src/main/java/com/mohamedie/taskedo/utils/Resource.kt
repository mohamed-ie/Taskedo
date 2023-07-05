package com.mohamedie.taskedo.utils

import com.mohamedie.taskedo.R

sealed interface Resource<out D> {
    class Success<D>(val data: D) : Resource<D>
    class Error(val error: UIText = UIText.StringResource(R.string.unexpected_error_occurred_please_try_again_later)) :
        Resource<Nothing>


    suspend fun <O> map(
        transform: suspend (D) -> O
    ): Resource<O> {
        return run {
            when (this) {
                is Resource.Error -> this
                is Resource.Success -> Resource.Success(transform(data))
            }
        }
    }


    fun getOrNull() = when (this) {
        is Resource.Error -> null
        is Resource.Success -> data
    }


    suspend fun handle(
        onError: suspend (UIText) -> Unit = {},
        onSuccess: suspend (D) -> Unit = {}
    ) {
        when (this) {
            is Resource.Error -> onError(error)
            is Resource.Success -> onSuccess(this.data)
        }
    }
}
