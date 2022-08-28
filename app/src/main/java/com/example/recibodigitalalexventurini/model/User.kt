package com.example.recibodigitalalexventurini.model

class User {

    // Example user infos:
    //  "id": 123,
    //  "username": "m.aleixo",
    //  "first_name": "Marcos",
    //  "last_name": "Aleixo",
    //  "email": "m.aleixo@sidi.org.br",
    //  "role": "SuperUser",
    //  "age": 30,
    //  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiMTIzIiwiaWF0IjoxNjQzMjAwOTQ2LCJleHAiOjE2NDMyODczNDZ9.kGFSAU2qsYS3DamSXDPBnAfG-xQAw-OKSirf18yDxU4"

    data class UserInfo(
        val id: String,
        val username: String,
        val first_name: String,
        val last_name: String,
        val email: String,
        val role: String,
        val age: String,
        val token: String
    )

    var id: Int? = null
    var username: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var email: String? = null
    var pass: String? = null
    var role: String? = null
    var age: Int? = null
    var token: String? = null
}
