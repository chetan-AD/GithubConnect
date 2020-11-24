package com.sample.githubconnect.models.entities

class UserListModel(
    var totalCount: Int = 0,
    var incomplete_result: Boolean = false,
    var items: List<User> = mutableListOf()
)
