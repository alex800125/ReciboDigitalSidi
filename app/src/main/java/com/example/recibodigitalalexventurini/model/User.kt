package com.example.recibodigitalalexventurini.model

import java.io.Serializable

class User {

    // Example user infos:
    //   "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImFsZXgyQGVtYWlsLmNvbSIsImlkIjoiNjMxMTM5Y2QzNDYyNmY2NzExYzEzMDM4IiwiaWF0IjoxNjYyMDc0NDI4LCJleHAiOjE2NjIxMTA0Mjh9.0SP66dzIwu50RVmE7yOnN-xpVko3kSu0ChMYc3kv9Ps",
    //   "idUser": "631139cd34626f6711c13038",
    //   "name": "alex2",
    //   "email": "alex2@email.com",
    //   "cpf": "55555555555",
    //   "avatar": "https://cloudflare-ipfs.com/ipfs/Qmd3W5DuhgHirLHGVixi6V76LhCkZUz6pnFt5AJBiyvHye/avatar/1135.jpg"

    constructor(
        name: String,
        phoneNumber: String,
        email: String,
        cpf: String,
        password: String,
        accepTerms: Boolean
    ) {
        this.name = name
        this.phoneNumber = phoneNumber
        this.email = email
        this.cpf = cpf
        this.pass = password
        this.acceptTerms = accepTerms
    }

    constructor(
        email: String,
        password: String
    ) {
        this.email = email
        this.pass = password
    }

    data class UserInfo(
        val token: String,
        val idUser: String,
        val name: String,
        val email: String,
        val cpf: String,
        val avatar: String
    ) : Serializable

    var idUser: String? = null
    var name: String? = null
    var phoneNumber: String? = null
    var email: String? = null
    var pass: String? = null
    var cpf: String? = null
    var acceptTerms: Boolean? = false
    var token: String? = null
    var avatar: String? = null
}
