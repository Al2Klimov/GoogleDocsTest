package de.al2klimov.android.googledocstest

import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.StringRequest

class Utf8StringRequest(
    method: Int,
    url: String?,
    listener: Response.Listener<String>?,
    errorListener: Response.ErrorListener?
) : StringRequest(method, url, listener, errorListener) {
    override fun parseNetworkResponse(response: NetworkResponse?): Response<String> {
        return Response.success(
            String(response!!.data, Charsets.UTF_8),
            HttpHeaderParser.parseCacheHeaders(response)
        )
    }
}