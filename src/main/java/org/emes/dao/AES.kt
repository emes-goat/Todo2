package org.emes.dao

import org.emes.dao.AES
import java.nio.ByteBuffer
import java.security.SecureRandom
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

class AES {
    companion object {
        private const val KEY_DERIVATION_FUNCTION = "PBKDF2WithHmacSHA512"
        private const val KEY_DERIVATION_ITERATIONS = 210000
        private const val AES_KEY_SIZE = 128
        private const val AES_MODE = "AES/GCM/NoPadding"
        private const val AES = "AES"

        private const val AES_SALT_LENGTH = 16
        private const val AAD_LENGTH = 12
        private const val PASSWORD_SALT_LENGTH = 16
    }

    fun encrypt(password: CharArray?, content: ByteArray?): ByteArray? {
        Objects.requireNonNull<CharArray?>(password)
        Objects.requireNonNull<ByteArray?>(content)
        require(password!!.size > 0)
        require(content!!.size > 0)

        val secureRandom = SecureRandom()
        val aesSalt = ByteArray(AES_SALT_LENGTH)
        secureRandom.nextBytes(aesSalt)
        val aad = ByteArray(AAD_LENGTH)
        secureRandom.nextBytes(aad)
        val passwordSalt = ByteArray(PASSWORD_SALT_LENGTH)
        secureRandom.nextBytes(passwordSalt)
        val key = getKey(password, passwordSalt)

        val cipher = Cipher.getInstance(AES_MODE)
        val keySpec = SecretKeySpec(key, AES)
        val gcmParameterSpec = GCMParameterSpec(AES_KEY_SIZE, aesSalt)

        cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmParameterSpec)
        cipher.updateAAD(aad)
        val ciphertext = cipher.doFinal(content)

        return joinArrays(passwordSalt, aesSalt, aad, ciphertext)
    }

    fun decrypt(password: CharArray?, aesResult: ByteArray?): ByteArray {
        Objects.requireNonNull<CharArray?>(password)
        Objects.requireNonNull<ByteArray?>(aesResult)
        require(password!!.size > 0)
        require(aesResult!!.size > 0)

        val byteBuffer = ByteBuffer.wrap(aesResult)

        val passwordSalt = ByteArray(PASSWORD_SALT_LENGTH)
        val aesSalt = ByteArray(AES_SALT_LENGTH)
        val aad = ByteArray(AAD_LENGTH)
        val ciphertext = ByteArray(
            (aesResult.size - PASSWORD_SALT_LENGTH - AES_SALT_LENGTH
                    - AAD_LENGTH)
        )

        byteBuffer.get(passwordSalt, 0, passwordSalt.size)
        byteBuffer.get(aesSalt, 0, aesSalt.size)
        byteBuffer.get(aad, 0, aad.size)
        byteBuffer.get(ciphertext, 0, ciphertext.size)

        val key = getKey(password, passwordSalt)

        val cipher = Cipher.getInstance(AES_MODE)
        val keySpec = SecretKeySpec(key, AES)
        val gcmParameterSpec = GCMParameterSpec(AES_KEY_SIZE, aesSalt)

        cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmParameterSpec)
        cipher.updateAAD(aad)

        return cipher.doFinal(ciphertext)
    }

    private fun getKey(password: CharArray?, salt: ByteArray): ByteArray {
        val derivationSpec =
            PBEKeySpec(
                password,
                salt,
                KEY_DERIVATION_ITERATIONS,
                AES_KEY_SIZE
            )
        return SecretKeyFactory.getInstance(KEY_DERIVATION_FUNCTION)
            .generateSecret(derivationSpec)
            .getEncoded()
    }

    private fun joinArrays(
        passwordSalt: ByteArray,
        aesSalt: ByteArray,
        aad: ByteArray,
        ciphertext: ByteArray
    ): ByteArray? {
        val result = ByteBuffer.allocate(
            (PASSWORD_SALT_LENGTH + AES_SALT_LENGTH + AAD_LENGTH
                    + ciphertext.size)
        )

        result.put(passwordSalt)
        result.put(aesSalt)
        result.put(aad)
        result.put(ciphertext)

        return result.array()
    }

    private fun require(condition: Boolean) {
        require(condition) { "Illegal argument" }
    }


}
