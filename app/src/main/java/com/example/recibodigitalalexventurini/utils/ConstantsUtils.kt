package com.example.recibodigitalalexventurini.utils

class ConstantsUtils {
    companion object {
        const val LOGTAG = "ReciptDigital:"

        const val USER_EXTRA = "extra_user"
        const val RECEIPTS_EXTRA = "extra_receipts"
        const val RECEIPT_DETAILS_EXTRA = "extra_details_receipt"

        // API Default items
        const val API_PATH = "https://sidi-proj-capacitacao.herokuapp.com"
        const val API_VERSION = "/api/v2"

        /** USER - GET, POST, PUT and DELETE methods in the API. Used for login and password
         * reset as well. */
        const val URL_USER = "$API_PATH$API_VERSION/users"

        /** USER - POST method in the API. Used to validate login. */
        const val URL_USER_LOGIN = "$URL_USER/login"

        /** USER - POST method in the API. Used to password reset.  */
        const val URL_USER_RESET_PASSWORD = "$URL_USER/resetPassword"

        /** RECEIPT - GET and POST methods in the API. Get and create new receipts. */
        const val URL_RECEIPT = "$API_PATH$API_VERSION/receipt"

        /** CATEGORY - GET and POST methods in the API. Get and create new category. */
        const val URL_CATEGORY = "$API_PATH$API_VERSION/category"

        /** CATEGORY for RECEIPT - PUT method in the API. PUT a category for a exist receipt. */
        const val CATEGORY_RECEIPT = "$API_PATH$API_VERSION/categoryreceipt/{receiptId}"
    }
}