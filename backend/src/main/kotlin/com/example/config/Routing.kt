package com.example.config

import com.example.routes.*
import com.example.service.*
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
    routing {
        authRoutes(securityService, userService)
        searchRoutes(searchService)
        userRoutes(userService)
        textRoutes(textService)
        textPageRoutes(textPageService)
        imageRoutes(imageService)
    }
}
