package com.felipecoronado.pruebaamoba.ui.components

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import java.net.InetAddress


@Composable
fun ConnectivityCheck(isConnected: MutableState<Boolean>) {
    val context = LocalContext.current

    DisposableEffect(Unit) {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                CoroutineScope(Dispatchers.IO).launch {
                    isConnected.value = canReachInternet()
                }
            }

            override fun onLost(network: Network) {
                isConnected.value = false
            }
        }

        val request = NetworkRequest.Builder().build()
        connectivityManager.registerNetworkCallback(request, networkCallback)

        onDispose {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        }
    }
}

suspend fun canReachInternet(): Boolean {
    return try {
        withTimeout(1000) {
            withContext(Dispatchers.IO) {
                withContext(Dispatchers.IO) {
                    InetAddress.getByName("www.google.com")
                }.isReachable(1000)
            }
        }
    } catch (e: Exception) {
        false
    }
}




