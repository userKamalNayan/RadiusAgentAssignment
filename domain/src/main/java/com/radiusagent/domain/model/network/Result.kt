package com.radiusagent.domain.model.network

/**
Created by Kamal Nayan on 05/09/22
 **/
sealed class Result<out SomeType, out SomeFailure> {
    class Success<out SomeType>(val data: SomeType) : Result<SomeType, Nothing>()
    class Error<out SomeFailure>(val failure: SomeFailure, val message: ErrorResponse?) :
        Result<Nothing, SomeFailure>()

    fun successOrError(
        functionType: (SomeType) -> Any,
        functionFailure: (SomeFailure, ErrorResponse?) -> Any
    ): Any =
        when (this) {
            is Success -> functionType(data)
            is Error -> functionFailure(failure, message)
        }
}
