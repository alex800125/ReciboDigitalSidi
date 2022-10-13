package com.example.recibodigitalalexventurini.model

class CreateUser(
    name: String,
    phoneNumber: String,
    email: String,
    cpf: String,
    password: String,
    acceptTerms: Boolean
) {

    var name: String? = name
    var phoneNumber: String? = phoneNumber
    var email: String? = email
    var pass: String? = password
    var cpf: String? = cpf
    var acceptTerms: Boolean? = acceptTerms
}
