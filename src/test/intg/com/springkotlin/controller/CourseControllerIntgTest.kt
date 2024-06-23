package com.springkotlin.controller

import com.springkotlin.CatalogueServiceApplication
import com.springkotlin.controller.dto.CourseDTO
import com.springkotlin.controller.repository.CourseRepository
import com.springkotlin.controller.util.CourseUtil
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBodyList

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = [CatalogueServiceApplication::class]
) // Go and run at random port[mostly not 8080]
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class CourseControllerIntgTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var courseRepository: CourseRepository


    @BeforeEach
    fun setup(){
        courseRepository.deleteAll()
        courseRepository.saveAll(CourseUtil.getCourseList())
    }

    @Test
    fun testCoursePostMethod() {

        val courseDTO = CourseDTO(
            id = null,
            name = "Building Restful API",
            category = "Prakash courses"
        )

        val savedCourseDto = webTestClient.post()
            .uri("/v1/courses")
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().isCreated
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody


        assert(savedCourseDto?.id != null)
    }


    @Test
    fun getAllCoursesTest(){

        val courseDtos = webTestClient.get()
            .uri("/v1/courses")
            .exchange()
            .expectStatus().isOk
            .expectBodyList<CourseDTO>()
            .returnResult()
            .responseBody

        Assertions.assertEquals(5, courseDtos?.size)
    }

    @Test
    fun updateCourseTest(){
        val actualCourse = CourseUtil.getCourseList()[3]
        courseRepository.save(actualCourse)
        val updatedCourse = actualCourse.copy(name = "Updated Name")

        val updatedCourseFromDb = webTestClient.put()
            .uri("/v1/courses/{course_id}", actualCourse.id)
            .bodyValue(updatedCourse)
            .exchange()
            .expectStatus().isOk
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertEquals(updatedCourseFromDb?.name, updatedCourse.name)
    }

    @Test
    fun deleteCourseTest(){
        val actualCourse = CourseUtil.getCourseList()[3]
        courseRepository.save(actualCourse)

        webTestClient.delete()
            .uri("/v1/courses/{course_id}", actualCourse.id)
            .exchange()
            .expectStatus().isNoContent

    }

}