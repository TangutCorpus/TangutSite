import com.example.model.User
import com.example.repository.UserRepository
import com.example.service.UserService
import com.example.service.UserServiceImpl
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import java.util.UUID
import kotlinx.datetime.LocalDate

class UserServiceTest {
    private lateinit var service: UserService
    private val EXCEPTION_MESSAGE = "An operation is not implemented: Not yet implemented"

    @BeforeTest
    fun setup() {
        val repository = mockk<UserRepository>(relaxed = true)
        service = UserServiceImpl(repository)
    }

    @Test
    fun `createUser should throw NotImplementedError`() = runBlocking {
        val user = User(
            id = UUID.randomUUID(),
            nickname = "testUser",
            email = "test@example.com",
            password = "password123",
            name = "Test User",
            biography = "A test user for demonstration purposes",
            role = "Editor",
            createdAt = LocalDate(2025, 1, 1)
        )
        val exception = assertFailsWith<NotImplementedError> {
            service.createUser(user)
        }
        assertEquals(EXCEPTION_MESSAGE, exception.message)
    }

    @Test
    fun `updateUser should throw NotImplementedError`() = runBlocking {
        val id = UUID.randomUUID()
        val user = User(
            id = id,
            nickname = "updatedUser",
            email = "update@example.com",
            password = "newPassword",
            name = "Updated User",
            biography = "Updated biography for user",
            role = "Moderator",
            createdAt = LocalDate(2025, 1, 1)
        )
        val exception = assertFailsWith<NotImplementedError> {
            service.updateUser(id, user)
        }
        assertEquals(EXCEPTION_MESSAGE, exception.message)
    }

    @Test
    fun `deleteUser should throw NotImplementedError`() = runBlocking {
        val id = UUID.randomUUID()
        val exception = assertFailsWith<NotImplementedError> {
            service.deleteUser(id)
        }
        assertEquals(EXCEPTION_MESSAGE, exception.message)
    }

    @Test
    fun `getAllUsers should throw NotImplementedError`() = runBlocking {
        val exception = assertFailsWith<NotImplementedError> {
            service.getAllUsers()
        }
        assertEquals(EXCEPTION_MESSAGE, exception.message)
    }

    @Test
    fun `getUserById should throw NotImplementedError`() = runBlocking {
        val id = UUID.randomUUID()
        val exception = assertFailsWith<NotImplementedError> {
            service.getUserById(id)
        }
        assertEquals(EXCEPTION_MESSAGE, exception.message)
    }
}
