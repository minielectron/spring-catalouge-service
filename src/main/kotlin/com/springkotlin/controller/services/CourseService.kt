package com.springkotlin.controller.services

import com.springkotlin.controller.dto.CourseDTO
import com.springkotlin.controller.entity.Course
import com.springkotlin.controller.repository.CourseRepository
import io.github.oshai.kotlinlogging.KLogging
import org.springframework.stereotype.Service

@Service
class CourseService(private val courseRepository: CourseRepository) {

    companion object : KLogging()
    fun addCourse(courseDTO: CourseDTO) : CourseDTO{
        val courseEntity = courseDTO.let {
            Course(it.id, it.name, it.category)
        }
        courseRepository.save(courseEntity)
        logger.info ("Saved course is: $courseEntity")
        return courseEntity.let {
            CourseDTO(it.id, it.name, it.category)
        }
    }
}

// DB knows only about the entity and user/ui knows about the dto.