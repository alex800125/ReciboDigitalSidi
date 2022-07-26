package com.example.recibodigitalalexventurini.api

import com.example.recibodigitalalexventurini.model.*
import com.example.recibodigitalalexventurini.utils.ConstantsUtils.Companion.CATEGORY_RECEIPT
import com.example.recibodigitalalexventurini.utils.ConstantsUtils.Companion.URL_CATEGORY
import com.example.recibodigitalalexventurini.utils.ConstantsUtils.Companion.URL_RECEIPT
import com.example.recibodigitalalexventurini.utils.ConstantsUtils.Companion.URL_USER
import com.example.recibodigitalalexventurini.utils.ConstantsUtils.Companion.URL_USER_LOGIN
import com.example.recibodigitalalexventurini.utils.ConstantsUtils.Companion.URL_USER_RESET_PASSWORD
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {
    @FormUrlEncoded
    @POST(URL_USER_LOGIN)
    fun userLogin(
        @Field("email") email: String,
        @Field("pass") pass: String
    ): Call<LoginResponse>

    @GET(URL_RECEIPT)
    fun getReceipts(): Call<ListReceiptsResponse>

    @GET(URL_CATEGORY)
    fun getCategories(): Call<ListCategoriesResponse>

    @FormUrlEncoded
    @POST(URL_CATEGORY)
    fun postNewCategory(
        @Field("categoryName") categoryName: String
    ): Call<NewCategoryResponse>

    @FormUrlEncoded
    @PUT(CATEGORY_RECEIPT)
    fun putCategoryReceipt(
        @Path("receiptId") receiptId: String,
        @Field("categoryId") categoryId: String,
        @Field("status") status: Boolean
    ): Call<AddCategoryForReceiptResponse>

    @FormUrlEncoded
    @POST(URL_USER)
    fun postNewUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("cpf") cpf: String,
        @Field("phonenumber") phonenumber: String,
        @Field("acceptTerms") acceptTerms: Boolean
    ): Call<UserCreateResponse>

    @FormUrlEncoded
    @POST(URL_USER_RESET_PASSWORD)
    fun postResetPassword(
        @Field("phonenumber") phonenumber: String,
        @Field("email") email: String,
        @Field("cpf") cpf: String
    ): Call<ForgotPasswordResponse>
}