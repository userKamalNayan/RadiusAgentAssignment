package com.radiusagent.domain.model.network

/**
Created by Kamal Nayan on 05/09/22
 **/

sealed class Failure {
    object NetworkConnection : Failure(){
        override fun toString(): String {
            return "NetworkConnection"
        }
    }
    object ServerError : Failure()
    {
        override fun toString(): String {
            return "ServerError"
        }
    }
    object ParsingError : Failure()
    {
        override fun toString(): String {
            return "ParsingError"
        }
    }
    object UnauthorizedError: Failure() {
        override fun toString(): String {
            return "UnauthorizedError"
        }
    }
    object  Error404NotFound: Failure(){
        override fun toString(): String {
            return "Error404NotFound"
        }
    }

    object BadRequest: Failure(){
        override fun toString(): String {
            return "Bad Request : 400"
        }
    }

    object NotAcceptable: Failure(){
        override fun toString(): String {
            return "Not Acceptable : 406"
        }
    }
}