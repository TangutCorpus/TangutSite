package com.example.service

import com.example.repository.ImageRepository
import io.ktor.http.content.PartData
import io.ktor.server.plugins.BadRequestException
import io.ktor.utils.io.readRemaining
import kotlinx.io.readByteArray
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.File
import java.util.UUID
import javax.imageio.ImageIO

class ImageServiceImpl(private val imageRepository: ImageRepository) : ImageService {
    override suspend fun store(file: PartData.FileItem): UUID {
        val fileName = file.originalFileName ?: throw BadRequestException("File name is null")
        val fileBytes = file.provider().readRemaining().readByteArray()

        val extension = fileName.substringAfterLast('.', "").lowercase()
        if (extension !in listOf("png", "jpg", "jpeg")) throw BadRequestException("Invalid file extension.")

        val mimeType = file.contentType?.toString() ?: ""
        if (mimeType !in listOf("image/png", "image/jpeg")) throw BadRequestException("Invalid MIME type.")

        if (!isImage(fileBytes)) throw BadRequestException("File content is not a valid image.")

        return imageRepository.store(extension, fileBytes)
    }

    override fun getImage(id: UUID): File {
        return imageRepository.get(id)
    }

    private fun isImage(fileBytes: ByteArray): Boolean {
        return try {
            val inputStream = ByteArrayInputStream(fileBytes)
            val image: BufferedImage? = ImageIO.read(inputStream)
            image != null
        } catch (e: Exception) {
            false
        }
    }
}
