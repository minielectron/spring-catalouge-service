package com.springkotlin.repository

import com.springkotlin.controller.entity.Course
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface CourseRepository: CrudRepository<Course, Int> { // Int is auto generated column.

    //https://docs.spring.io/spring-data/jpa/reference/#jpa.query-methods.query-creation
    // There are patterns like findBy<>Containing which autmatically generate equivalent code
    fun findByNameContaining(courseName: String): List<Course>

    @Query(value = "Select * from course where name like %?1%", nativeQuery = true)
    fun findCoursesByName(courseName: String): List<Course>
}