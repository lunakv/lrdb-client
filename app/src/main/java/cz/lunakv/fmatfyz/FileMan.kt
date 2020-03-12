package cz.lunakv.fmatfyz

import android.content.Context
import java.io.FileNotFoundException
import java.nio.charset.Charset

// Handles reading and writing information from/to persistent storage
class FileMan(private val context: Context) {
    val tokenFile = "token_v2"         // stores token number
    val subFile = "subscription"       // stores "true" if listening is activated, "false" otherwise
    fun existsToken(): Boolean {
        if (!context.filesDir.list().contains(tokenFile)) {
            return false
        }

        var token = ""
        context.openFileInput(tokenFile).use {
            token = it.readBytes().toString(Charset.defaultCharset())
        }

        return token.isNotBlank()
    }

    fun isSubscribed(): Boolean {
        if (!context.filesDir.list().contains(subFile)) {
            return false
        }

        context.openFileInput(subFile).use {
            val value = it.readBytes().toString(Charset.defaultCharset())
            return value == "true"
        }
    }

    fun logSub(){
        context.openFileOutput(subFile, Context.MODE_PRIVATE).use {
            it.write("true".toByteArray(Charset.defaultCharset()))
        }
    }

    fun logUnsub(){
        context.openFileOutput(subFile, Context.MODE_PRIVATE).use {
            it.write("false".toByteArray(Charset.defaultCharset()))
        }
    }

    fun getToken(): String {
        if (!existsToken()) {
            throw FileNotFoundException("Token does not exist.")
        }

        var token = ""
        context.openFileInput(tokenFile).use {
            token = it.readBytes().toString(Charset.defaultCharset())
        }

        return token
    }

    fun saveToken(token: String) {
        context.openFileOutput(tokenFile, Context.MODE_PRIVATE).use {
            it.write(token.toByteArray(Charset.defaultCharset()))
        }
    }
}