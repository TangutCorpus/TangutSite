package com.example.config

import com.example.plugins.RoleBasedAuthorizationPlugin
import com.example.routes.authRoutes
import com.example.routes.searchRoutes
import com.example.routes.textPageRoutes
import com.example.routes.textRoutes
import com.example.routes.userRoutes
import com.example.service.SearchService
import com.example.service.SecurityService
import com.example.service.TextPageService
import com.example.service.TextService
import com.example.service.UserService
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    userService: UserService,
    textService: TextService,
    searchService: SearchService,
    textPageService: TextPageService,
    securityService: SecurityService,
    imageService: ImageService,
) {
    install(RoleBasedAuthorizationPlugin)
    
    routing {
        authRoutes(securityService, userService)
        searchRoutes(searchService)
        userRoutes(userService)
        textRoutes(textService)
        textPageRoutes(textPageService)
        imageRoutes(imageService)
    }
}
