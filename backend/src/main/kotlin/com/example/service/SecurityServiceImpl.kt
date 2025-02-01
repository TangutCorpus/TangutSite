package com.example.service

import at.favre.lib.crypto.bcrypt.BCrypt
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import com.example.model.AuthResponse
import com.example.model.RefreshToken
import com.example.model.RefreshTokenRequest
import com.example.model.RefreshTokenResponse
import com.example.model.User
import com.example.model.UserLoginRequest
import com.example.model.UserRoles
import com.example.repository.RefreshTokenRepository
import com.example.repository.UserRepository
import io.ktor.server.auth.jwt.JWTCredential
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.config.ApplicationConfig
import java.util.Date
import java.util.UUID
import javax.naming.AuthenticationException

class SecurityServiceImpl(
    val userRepository: UserRepository,
    val refreshTokenRepository: RefreshTokenRepository,
    val config: ApplicationConfig
) : SecurityService {
    override val realm = getConfigProperty("jwt.realm")
    private val secret = getConfigProperty("jwt.secret")
    private val issuer = getConfigProperty("jwt.issuer")
    private val audience = getConfigProperty("jwt.audience")
    private val accessTokenDuration = getConfigProperty("jwt.accessToken.duration").toLong()
    private val refreshTokenDuration = getConfigProperty("jwt.refreshToken.duration").toLong()

    override suspend fun getValidator(credential: JWTCredential): JWTPrincipal? {
        val email: String = credential.getCredentialClaim("email")
            ?: throw AuthenticationException("Invalid or missing email claim")
        val user: User? = userRepository.getUserByEmail(email)
        return user?.let {
            if (checkAudienceCorrectness(credential)) {
                JWTPrincipal(credential.payload)
            } else {
                null
            }
        }
    }

    override fun getDefaultJWTVerifier(): JWTVerifier =
        JWT.require(Algorithm.HMAC256(secret))
            .withAudience(audience)
            .withIssuer(issuer)
            .build()

    override suspend fun authenticate(loginRequest: UserLoginRequest): AuthResponse? {
        val user = userRepository.getUserByEmail(loginRequest.email) ?: return null
        return if (BCrypt.verifyer().verify(loginRequest.password.toCharArray(), user.password).verified) {            val accessToken = createAccessToken(user.email, user.role)
            val refreshToken = createRefreshToken(user.email, user.role)
            refreshTokenRepository.save(RefreshToken(user.id, refreshToken))
            AuthResponse(accessToken, refreshToken)
        } else {
            null
        }
    }

    override suspend fun refreshToken(refreshTokenRequest: RefreshTokenRequest): RefreshTokenResponse? {
        val refreshToken = refreshTokenRequest.token
        val decodedToken = decodeRefreshToken(refreshToken) ?: return null
        val email = decodedToken.getClaim("email").asString()
        val user = userRepository.getUserByEmail(email) ?: return null
        val savedToken = refreshTokenRepository.findIdByToken(refreshToken)

        return if (savedToken != null && !decodedToken.isExpired()) {
            RefreshTokenResponse(createAccessToken(user.email, user.role))
        } else {
            null
        }
    }

    override fun saveRefreshToken(id: UUID, token: String) {
        refreshTokenRepository.save(RefreshToken(id, token))
    }

    private fun decodeRefreshToken(token: String): DecodedJWT? =
        try {
            val decodedJWT = getDefaultJWTVerifier().verify(token)
            if (!decodedJWT.isExpired()) decodedJWT else null
        } catch (ex: JWTVerificationException) {
            null
        }

    private fun DecodedJWT.isExpired(): Boolean = expiresAt.before(Date())

    override fun createAccessToken(email: String, role: UserRoles) =
        generateJWTToken(email, role, accessTokenDuration)

    override fun createRefreshToken(email: String, role: UserRoles) =
        generateJWTToken(email, role, refreshTokenDuration)

    private fun generateJWTToken(userEmail: String, userRole: UserRoles, expireIn: Long): String =
        JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("email", userEmail)
            .withClaim("role", userRole.toString())
            .withExpiresAt(Date(System.currentTimeMillis() + expireIn))
            .sign(Algorithm.HMAC256(secret))

    private fun getConfigProperty(name: String): String =
        config.property(name).getString()

    private fun JWTCredential.getCredentialClaim(name: String): String? =
        payload.getClaim(name).asString()

    private fun checkAudienceCorrectness(credential: JWTCredential): Boolean =
        credential.payload.audience.contains(audience)

}