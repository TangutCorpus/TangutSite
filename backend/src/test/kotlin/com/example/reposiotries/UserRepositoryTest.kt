import com.example.model.User
import com.example.repository.UserRepository
import com.example.repository.UserRepositoryImpl
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDate
import org.jetbrains.exposed.sql.Database
import java.util.UUID
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class UserRepositoryTest {
    private lateinit var repository: UserRepository
    private val EXCEPTION_MESSAGE = "An operation is not implemented: Not yet implemented"

    @BeforeTest
    fun setup() {
        val database = mockk<Database>(relaxed = true)
        repository = UserRepositoryImpl(database)
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
            repository.createUser(user)
        }
        assertEquals(EXCEPTION_MESSAGE, exception.message)
    }

    @Test
    fun `updateUser should throw NotImplementedError`() = runBlocking {
        val id = UUID.randomUUID()
        val user = User(
            id = id,
            nickname = "updatedUser",
            email = "updated@example.com",
            password = "newPassword",
            name = "Updated User",
            biography = "Updated biography for user",
            role = "Moderator",
            createdAt = LocalDate(2025, 1, 1)
        )
        val exception = assertFailsWith<NotImplementedError> {
            repository.updateUser(id, user)
        }
        assertEquals(EXCEPTION_MESSAGE, exception.message)
    }

    @Test
    fun `deleteUser should throw NotImplementedError`() = runBlocking {
        val id = UUID.randomUUID()
        val exception = assertFailsWith<NotImplementedError> {
            repository.deleteUser(id)
        }
        assertEquals(EXCEPTION_MESSAGE, exception.message)
    }

    @Test
    fun `getAllUsers should throw NotImplementedError`() = runBlocking {
        val exception = assertFailsWith<NotImplementedError> {
            repository.getAllUsers()
        }
        assertEquals(EXCEPTION_MESSAGE, exception.message)
    }

    @Test
    fun `getUserById should throw NotImplementedError`() = runBlocking {
        val id = UUID.randomUUID()
        val exception = assertFailsWith<NotImplementedError> {
            repository.getUserById(id)
        }
        assertEquals(EXCEPTION_MESSAGE, exception.message)
    }
}
