package com.android.wazzabysama.data.api

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserAPIServiceTest {
    private lateinit var service: UserAPIService
    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserAPIService::class.java)
    }

    private fun enqueueMockResponse(
        fileName: String
    ) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)
    }

    @Test
    fun getUser_sentRequest_receivedExpected() {
        runBlocking {
            enqueueMockResponse("usersresponse.json")
            val responseBody = service.getUser("sidneymaleoregis@gmail.com").body()
            val request = server.takeRequest()
            assertThat(responseBody).isNotNull()
            assertThat(request.path).isEqualTo("/api/users?username=sidneymaleoregis%40gmail.com")
        }
    }

    @Test
    fun getUser_receivedResponse_correctContent() {
        runBlocking {
            enqueueMockResponse("usersresponse.json")
            val responseBody = service.getUser("sidneymaleoregis@gmail.com").body()
            val user = responseBody!!.Users[0]
            assertThat(user.firstName).isEqualTo("Sidney")
            assertThat(user.lastName).isEqualTo("MALEO")
            assertThat(user.email).isEqualTo("sidneymaleoregis@gmail.com")
            assertThat(user.images[0].imageName).isEqualTo("photo-2-62129c4b0a5a0818619410.jpg")
            assertThat(user.roles[0]).isEqualTo("ROLE_SUPER_ADMIN")
        }
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}