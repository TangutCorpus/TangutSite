package com.example.service

import com.example.repository.ImageRepository
import io.ktor.http.content.*
import io.ktor.server.plugins.*
import io.ktor.utils.io.jvm.javaio.*
import java.io.ByteArrayInputStream
import java.io.File
import java.util.*
import javax.imageio.ImageIO.createImageInputStream
import javax.imageio.ImageIO.getImageReaders

class ImageService(private val imageRepository: ImageRepository) {
    private val supportedFormats = listOf("jpg", "jpeg", "png")
    private val maxBytes = 50 * 1024 * 1024

    fun store(file: PartData.FileItem): UUID {
        val fileName = file.originalFileName ?: throw BadRequestException("File name is null")

        val extension = fileName.substringAfterLast('.', "").lowercase()
        val contentType = file.contentType?.contentSubtype?.lowercase()
        if (contentType !in supportedFormats && extension !in supportedFormats) {
            throw BadRequestException("Unsupported file type '$contentType' / extension '$extension'")
        }

        val tempFile = kotlin.io.path.createTempFile().toFile()
        file.provider().toInputStream().use { input ->
            tempFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }

        val fileBytes = tempFile.readBytes()
        if (fileBytes.size > maxBytes) {
            tempFile.delete()
            throw BadRequestException("File too large")
        }

        if (!isImage(fileBytes)) {
            tempFile.delete()
            throw BadRequestException("File is not a valid image")
        }

        val id = imageRepository.store(extension, fileBytes)
        tempFile.delete()
        return id
    }

    fun getImage(id: UUID): File {
        return imageRepository.get(id)
    }

    private fun isImage(fileBytes: ByteArray): Boolean = runCatching {
        createImageInputStream(ByteArrayInputStream(fileBytes)).use { stream ->
            getImageReaders(stream).hasNext()
        }
    }.getOrDefault(false)
}
