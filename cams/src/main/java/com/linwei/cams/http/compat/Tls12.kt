package com.linwei.cams.http.compat

import android.os.Build
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import java.security.GeneralSecurityException
import java.security.KeyStore
import java.util.*
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager


fun OkHttpClient.Builder.enableTls12OnPreLollipop(): OkHttpClient.Builder {
    if (Build.VERSION.SDK_INT in 16..21) {
        try {
            val sc: SSLContext = SSLContext.getInstance("TLSv1.2")
            sc.init(null, null, null)
            sslSocketFactory(
                Tls12SocketFactory(sc.socketFactory),
                getSystemDefaultTrustManager()
            )

            val cs: ConnectionSpec = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_2)
                .build()

            val specs: List<ConnectionSpec> =
                listOf(cs, ConnectionSpec.COMPATIBLE_TLS, ConnectionSpec.CLEARTEXT)
            connectionSpecs(specs)
        } catch (exc: Exception) {
            exc.printStackTrace()
        }
    }

    return this
}

@Throws(GeneralSecurityException::class)
private fun getSystemDefaultTrustManager(): X509TrustManager {
    val trustManagerFactory: TrustManagerFactory =
        TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
    trustManagerFactory.init(null as KeyStore?)
    val trustManagers: Array<TrustManager> = trustManagerFactory.trustManagers
    check(!(trustManagers.size != 1 || trustManagers[0] !is X509TrustManager)) {
        "Unexpected default trust managers:" + Arrays.toString(
            trustManagers
        )
    }
    return trustManagers[0] as X509TrustManager
}