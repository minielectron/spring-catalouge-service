package com.springkotlin.controller.controller

import com.springkotlin.controller.dto.CourseDTO
import com.springkotlin.controller.services.CourseService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/courses")
class CourseController(private val courseService: CourseService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addCourse(@RequestBody course: CourseDTO): CourseDTO {
        return courseService.addCourse(course)
    }
}