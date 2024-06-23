package com.springkotlin.controller.repository

import com.springkotlin.controller.entity.Course
import org.springframework.data.repository.CrudRepository

interface CourseRepository: CrudRepository<Course, Int> { // Int is auto generated column.

}