package com.springkotlin.controller

import com.ninjasquad.springmockk.MockkBean
import com.springkotlin.controller.controller.CourseController
import com.springkotlin.controller.dto.CourseDTO
import com.springkotlin.controller.services.CourseService
import com.springkotlin.controller.util.CourseUtil
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.util.Assert


@WebMvcTest(controllers = [CourseController::class])
@AutoConfigureWebTestClient
class CourseControllerUnitTest {

    @MockkBean // Important -> check the correct dependency from ninjasquad mockk
    lateinit var courseServiceMock: CourseService

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    fun addCourse() {
        val courseDTO = CourseDTO(id = null, "Android development", "Tech")
        val response = courseDTO.copy(1)
        every {  courseServiceMock.addCourse(any()) } returns response
        val result = webTestClient
            .post()
            .uri("/v1/courses")
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().isCreated
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        assert(result?.id != null)
        assert(result?.id == 1)
        verify { courseServiceMock.addCourse(any()) } // This verifies if the mock call was called or not.
    }

    @Test
    fun addCourse_validation() {
        val courseDTO = CourseDTO(id = null, "", "")
        val response = courseDTO.copy(1)
        every {  courseServiceMock.addCourse(any()) } returns response
        webTestClient
            .post()
            .uri("/v1/courses")
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().isBadRequest // This will test the bad request

    }

    @Test
    fun addCourse_exception_handler() {
        val courseDTO = CourseDTO(id = null, "Android", "Tech")
        val errorMessage = "Error occurred"
        every {  courseServiceMock.addCourse(any()) } throws RuntimeException(errorMessage)

        val response = webTestClient
            .post()
            .uri("/v1/courses")
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().is5xxServerError // This will test the bad request
            .expectBody(String::class.java)
            .returnResult()
            .responseBody

        Assertions.assertEquals(errorMessage, response)

    }

    @Test
    fun deleteCourseTest(){

        every { courseServiceMock.deleteCourse(any()) } just runs

        webTestClient.delete()
            .uri("/v1/courses/{course_id}", 100)
            .exchange()
            .expectStatus().isNoContent

        verify { courseServiceMock.deleteCourse(any()) }
    }
}