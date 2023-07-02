package com.mohamedie.taskedo.utils

sealed interface Resource<out D> {
    class Success<D>(val data: D) : Resource<D>
    class Error(val error: UIText) : Resource<Nothing>


    fun <O> map(
        transform: (D) -> O
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


    suspend fun handle(onError: suspend (UIText) -> Unit = {}, onSuccess: suspend (D) -> Unit = {}) {
        when (this) {
            is Resource.Error -> onError(error)
            is Resource.Success -> onSuccess(this.data)
        }
    }
}
