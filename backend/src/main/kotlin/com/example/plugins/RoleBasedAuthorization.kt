package com.example.plugins

import com.example.model.UserRoles
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.createRouteScopedPlugin
import io.ktor.server.auth.AuthenticationChecked
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.response.respond
import io.ktor.server.routing.Route

/**
 * Configuration class for the Role-Based Authorization Plugin.
 *
 * This class allows specifying a set of roles that are permitted to access a route.
 *
 * @property roles A set of UserRoles that define access permissions for routes.
 */
class RoleBasedPluginConfiguration {
    var roles: Set<UserRoles> = emptySet()
}

/**
 * A Ktor plugin for role-based authorization.
 *
 * This plugin restricts access to routes based on user roles defined in JWT tokens.
 * If users does not have a required role, they receive a 403 Forbidden response.
 */
val RoleBasedAuthorizationPlugin =
    createRouteScopedPlugin("RoleBasedAuthorizationPlugin", createConfiguration = ::RoleBasedPluginConfiguration) {
        val roles = pluginConfig.roles

        on(AuthenticationChecked) { call ->
            val userRole = getRoleFromToken(call)

            if (userRole == null || userRole !in roles) {
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
    return call.principal<JWTPrincipal>()
        ?.payload
        ?.getClaim("role")
        ?.asString()
        ?.let { runCatching { UserRoles.valueOf(it) }.getOrNull() }
}

/**
 * Extension function for defining role-protected routes.
 *
 * Installs the RoleBasedAuthorizationPlugin and restricts access to users with any of the specified roles.
 *
 * @param hasAnyRole A list of roles that are allowed to access the route.
 * @param build The route configuration.
 */
fun Route.hasAnyRole(vararg hasAnyRole: UserRoles, build: Route.() -> Unit) {
    install(RoleBasedAuthorizationPlugin) {
        roles = hasAnyRole.toSet()
    }
    build()
}
