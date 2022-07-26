package com.example.recibodigitalalexventurini.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

// Example login response:
//   "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImFsZXgyQGVtYWlsLmNvbSIsImlkIjoiNjMxMTM5Y2QzNDYyNmY2NzExYzEzMDM4IiwiaWF0IjoxNjYyMDc0NDI4LCJleHAiOjE2NjIxMTA0Mjh9.0SP66dzIwu50RVmE7yOnN-xpVko3kSu0ChMYc3kv9Ps",
//   "idUser": "631139cd34626f6711c13038",
//   "name": "alex2",
//   "email": "alex2@email.com",
//   "cpf": "55555555555",
//   "avatar": "https://cloudflare-ipfs.com/ipfs/Qmd3W5DuhgHirLHGVixi6V76LhCkZUz6pnFt5AJBiyvHye/avatar/1135.jpg"

data class LoginResponse(
    @SerializedName("token")
    var token: String,
    @SerializedName("idUser")
    var idUser: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("cpf")
    var cpf: String,
    @SerializedName("avatar")
    var avatar: String
) : Serializable
