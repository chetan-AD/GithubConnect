package com.sample.githubconnect.models.response

enum class ErrorCodes(var code : Int) {
    SOCKET_TIMEOUT(900),
    UNAUTHORIZED(401),
    NOT_FOUND(404)

}
