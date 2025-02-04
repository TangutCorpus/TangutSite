package com.example.plugins

import com.example.model.UserRoles
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.createApplicationPlugin
import io.ktor.server.auth.AuthenticationChecked
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.util.AttributeKey

/**
 * Role-Based Authorization Plugin for Ktor.
 *
 * This plugin checks if the authenticated user has the required role(s) to access a route.
 * If the user does not have the required role, it returns a 403 Forbidden response.
 */
val RoleBasedAuthorizationPlugin = createApplicationPlugin("RoleBasedAuthorizationPlugin") {
    on(AuthenticationChecked) { call ->
        val userRole = getRoleFromToken(call)
        val requiredRoles = call.attributes.getOrNull(requiredRolesKey)

        if (userRole == null || (requiredRoles != null && userRole !in requiredRoles)) {
            call.respond(HttpStatusCode.Forbidden)
        }
    }
}

/**
 * Extracts the user role from the JWT token in the request.
 *
 * @param call The current application call.
 * @return The user's role if available, otherwise null.
 */
private fun getRoleFromToken(call: ApplicationCall): UserRoles? {
    return call.principal<JWTPrincipal>()?.payload?.getClaim("role")?.asString()
        ?.let { runCatching { UserRoles.valueOf(it) }.getOrNull() }
}

private val requiredRolesKey = AttributeKey<Set<UserRoles>>("requiredRoles")

/**
 * Extension function for defining role-protected routes.
 *
 * This function allows specifying one or more roles required to access a route.
 * The required roles are stored as an attribute and checked by the authorization plugin.
 *
 * @param hasAnyRole A list of roles allowed to access the route.
 * @param build The route configuration.
 */
fun Route.hasAnyRole(vararg hasAnyRole: UserRoles, build: Route.() -> Unit) {
    attributes.put(requiredRolesKey, hasAnyRole.toSet())
    build()
}
