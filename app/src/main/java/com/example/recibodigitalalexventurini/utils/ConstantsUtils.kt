package com.example.recibodigitalalexventurini.utils

class ConstantsUtils {
    companion object {
        val LOGTAG = "ReciptDigital:"

        val USER_INFO_EXTRAS = "extra_UserInfo"

        val EMPTY_STRING = ""

        // API Default items
        val API_PATH = "https://sidi-proj-capacitacao.herokuapp.com"
        val API_VERSION = "/api/v2"

        /** USER - GET, POST, PUT and DELETE methods in the API. Used for login and password
         * reset as well. */
        val URL_USER = "$API_PATH$API_VERSION/users"

        /** USER - POST method in the API. Used to validate login. */
        val URL_USER_LOGIN = "$URL_USER/login"

        /** USER - POST method in the API. Used to password reset.  */
        val URL_USER_RESET_PASSWORD = "$URL_USER/resetPassword"

        /** RECEIPT - GET and POST methods in the API. Get and create new receipts. */
        val URL_RECEIPT = "$API_PATH$API_VERSION/receipt"

        /** CATEGORY - GET and POST methods in the API. Get and create new category. */
        val URL_CATEGORY = "$API_PATH$API_VERSION/category"
    }

    enum class typePost {
        USER, RECEIPT, CATEGORY
    }
}