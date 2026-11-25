package com.zulalnb.contactmanager

data class Contact(
    var id: Int? = null,
    var firstName: String,
    var lastName: String,
    var email: String,
    var createdAt: String? = null,
    var updatedAt: String? = null
)
