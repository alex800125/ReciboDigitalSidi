package com.example.recibodigitalalexventurini.utils

class ConstantsUtils {
    companion object {
        val LOGTAG = "ReciptDigital:"

        val USER_INFO_EXTRAS = "extra_UserInfo"

        // API Default items
        val API_PATH = "https://sidi-proj-capacitacao.herokuapp.com"
        val API_VERSION = "/api/v2"

        /** USER - GET, POST, PUT and DELETE methods in the API. Used for login and password
         * reset as well. */
        val USER = "$API_PATH$API_VERSION/users"

        /** USER - POST method in the API. Used to validate login. */
        val USER_LOGIN = "$USER/login"

        /** USER - POST method in the API. Used to password reset.  */
        val USER_RESET_PASSWORD = "$USER/resetPassword"

        /** RECEIPT - GET and POST methods in the API. Get and create new receipts. */
        val RECEIPT = "$API_PATH$API_VERSION/receipt"

        /** CATEGORY - GET and POST methods in the API. Get and create new category. */
        val CATEGORY = "$API_PATH$API_VERSION/category"
    }
}