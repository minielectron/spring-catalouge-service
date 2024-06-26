package com.springkotlin.repository

import com.springkotlin.controller.util.CourseUtil
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import java.util.stream.Stream

@DataJpaTest
@ActiveProfiles("test")
class CourseRepositoryIntgTest {

    @Autowired
    lateinit var courseRepository: CourseRepository


    @BeforeEach
    fun setup(){
        courseRepository.deleteAll()
        courseRepository.saveAll(CourseUtil.getCourseList())
    }
    @Test
    fun findByNameContainingTest(){
        val courses = courseRepository.findByNameContaining("test")
        println(courses)
        Assertions.assertEquals(courses.size , 2)
    }

    @Test
    fun findNameContainingTest(){
        val courses = courseRepository.findCoursesByName("test")
        println(courses)
        Assertions.assertEquals(courses.size , 2)
    }

    @ParameterizedTest
    @MethodSource("courseAndSize")
    fun findNameContainingTest_approach2(name:String, expectedSize: Int){
        val courses = courseRepository.findCoursesByName(name)
        println(courses)
        Assertions.assertEquals(courses.size , expectedSize)
    }

    companion object {

        @JvmStatic
        fun courseAndSize(): Stream<Arguments>{
            return Stream.of(Arguments.arguments("test", 2), Arguments.arguments("wiremark", 1))
        }
    }
}