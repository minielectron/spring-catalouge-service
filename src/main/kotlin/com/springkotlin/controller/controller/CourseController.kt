package com.springkotlin.controller.controller

import com.springkotlin.controller.dto.CourseDTO
import com.springkotlin.controller.services.CourseService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/courses")
@Validated
class CourseController(private val courseService: CourseService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addCourse(@RequestBody @Valid course: CourseDTO): CourseDTO {
        return courseService.addCourse(course)
    }

    @GetMapping
    fun getAllCourses(): List<CourseDTO> {
        return courseService.getAllCourse()
    }

    @PutMapping("/{course_id}")
    fun updateCourseById(@RequestBody course: CourseDTO, @PathVariable("course_id") id: Int): CourseDTO {
        return courseService.updateCourse(id, course)
    }

    @DeleteMapping("/{course_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCourseById(@PathVariable("course_id") id: Int) {
        return courseService.deleteCourse(id)
    }

}