package com.android.wazzabysama.data.api

import com.android.wazzabysama.data.api.service.UserAPIService
import com.android.wazzabysama.data.model.api.ApiLogin
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
            val responseBody = service.getUser("sidneymaleoregis@gmail.com","").body()
            val request = server.takeRequest()
            assertThat(responseBody).isNotNull()
            assertThat(request.path).isEqualTo("/api/users?username=sidneymaleoregis%40gmail.com")
        }
    }

    @Test
    fun getToken_sentRequest_receivedExpected() {
        runBlocking {
            enqueueMockResponse("tokenresponse.json")
            val responseBody = service.getToken(ApiLogin("sidneymaleoregis@gmail.com", "Nfkol3324012020@!")).body()
            val request = server.takeRequest()
            assertThat(responseBody).isNotNull()
            assertThat(request.path).isEqualTo("/api/login_check")
        }
    }

    @Test
    fun getUser_receivedResponse_correctContent() {
        runBlocking {
            enqueueMockResponse("usersresponse.json")
            val responseBody = service.getUser("sidneymaleoregis@gmail.com","").body()
            val user = responseBody!!.Users[0]
            assertThat(user.firstName).isEqualTo("Sidney")
            assertThat(user.lastName).isEqualTo("MALEO")
            assertThat(user.email).isEqualTo("sidneymaleoregis@gmail.com")
            assertThat(user.images[0].imageName).isEqualTo("photo-2-62129c4b0a5a0818619410.jpg")
            assertThat(user.roles[0]).isEqualTo("ROLE_SUPER_ADMIN")
        }
    }

    @Test
    fun getToken_receivedResponse_correctContent() {
        runBlocking {
            enqueueMockResponse("tokenresponse.json")
            val responseBody = service.getToken(ApiLogin("sidneymaleoregis@gmail.com", "Nfkol3324012020@!")).body()
            val token = responseBody!!.token
            assertThat(token).isEqualTo("eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpYXQiOjE2NTA3NjQ1ODgsImV4cCI6MTY1MDg1MDk4OCwicm9sZXMiOlsiUk9MRV9TVVBFUl9BRE1JTiJdLCJ1c2VybmFtZSI6InNpZG5leW1hbGVvcmVnaXNAZ21haWwuY29tIn0.l7nkaIu1p3opJnXPkiEdJVCAmY-8IUyUicWiCUX-aWt7V50EIe9v8ygnQEL4nsS5g-uHwW9v2AvWZg9qCpusY8veLK0n83L4qkglk3OH0BltcAk3ASPPVSwULRI-QQ7XtiEOJkz-vx5smp2kqhMlTJt7OpzcckghpwYbpRq9_iDnVg-p6PIxFDkbxmWlu35vQ3teeLxrh4XQSe_8-w09eip-iJqK5ZklUiDTZ-cgp9-1cJvDMyk7jQ8OMQ-z81P8AXsz-wEiZS7RtDUpJwPqi1QSI8VII-txnau6DITCYsTotSKDtMaO2bl-uIKIzaeIRXejzPez_-2oEIl53fWVHdubiJJdrr6rMYiaWi5BJHzeijgCFV1TP3V4iO71FZFk0xJ0IcjcIUq2qfC4b9v4jLTkFiviejlkygCKtqX1FSPhCW2mBmixka0qUpz22CbmuvqsyuLkduEbxe71wLT4cyeDGf-5e-bxaNB7CiaW85YUa-PQGyVFuTNgiydVrzp1_vBUZ6BG_I9QQ04xdVWp5Q82Gt2u3eS_-hKVfKAv9pCM6DsQcoYzR_Cm0xxvKm6-rWYvWIJHUE4em4HC5DiRxyX3N6bd819zVzgD1di31whSUYuuxIky5dk7Lthq8-zE6sL2SF8vbiQhdKLn-5wI7_XJi54qcsKzYo-xEu94tK0")
        }
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}