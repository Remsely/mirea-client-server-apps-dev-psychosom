package ru.remsely.psyhosom.security.jwt

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Component
import java.security.KeyFactory
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*


@Component
class RsaKeyProperties(
    private val resourceLoader: ResourceLoader,
) {
    lateinit var publicKey: RSAPublicKey
    lateinit var privateKey: RSAPrivateKey

    @Value("\${security.jwt.public-key-path}")
    private lateinit var publicKeyPath: String

    @Value("\${security.jwt.private-key-path}")
    private lateinit var privateKeyPath: String

    @PostConstruct
    private fun init() {
        val keyFactory = KeyFactory.getInstance("RSA")

        publicKey = resourceLoader.getResource(publicKeyPath)
            .inputStream.use { it.readBytes() }
            .decodePemKey("-----BEGIN PUBLIC KEY-----", "-----END PUBLIC KEY-----")
            .let { keyFactory.generatePublic(X509EncodedKeySpec(it)) as RSAPublicKey }

        privateKey = resourceLoader.getResource(privateKeyPath)
            .inputStream.use { it.readBytes() }
            .decodePemKey("-----BEGIN PRIVATE KEY-----", "-----END PRIVATE KEY-----")
            .let { keyFactory.generatePrivate(PKCS8EncodedKeySpec(it)) as RSAPrivateKey }
    }

    private fun ByteArray.decodePemKey(beginMarker: String, endMarker: String) =
        String(this, Charsets.UTF_8)
            .replace(beginMarker, "")
            .replace(endMarker, "")
            .replace("\\s+".toRegex(), "")
            .let { Base64.getDecoder().decode(it) }
}
