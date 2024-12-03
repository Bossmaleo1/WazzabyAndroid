package com.android.wazzabysama.data.api

import com.android.wazzabysama.data.api.service.PublicMessageAPIService
import com.android.wazzabysama.data.api.service.UserAPIService
import com.google.common.truth.Truth
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
import java.util.Date


class PublicMessageAPIServiceTest {

    private lateinit var service: PublicMessageAPIService
    private lateinit var server: MockWebServer


    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PublicMessageAPIService::class.java)
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
    fun getPublicMessage_sentRequest_receivedExpected() {
        runBlocking {
            enqueueMockResponse("publicmessageresponse.json")
            val responseBody = service.getPublicMessage(
                problematic = "3",
                page = 1,
                pagination = true,
                token = ""
            ).body()
            val request = server.takeRequest()
            Truth.assertThat(responseBody).isNotNull()
            Truth.assertThat(request.path).isEqualTo("/api/public_messages?problematic=3&_page=1&pagination=true")
            Truth.assertThat(responseBody?.publicMessageList?.size).isEqualTo(10)
            Truth.assertThat(responseBody?.publicMessageList?.get(0)?.id).isEqualTo(19)
            Truth.assertThat(responseBody?.publicMessageList?.get(0)?.wording).isEqualTo("I should say \"With what porpoise?\"' 'Don't you mean that you couldn't")
        }
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}