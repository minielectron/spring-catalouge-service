package com.springkotlin.controller.services

import com.springkotlin.controller.dto.CourseDTO
import com.springkotlin.controller.entity.Course
import com.springkotlin.repository.CourseRepository
import com.springkotlin.exception.CourseNotFoundException
import io.github.oshai.kotlinlogging.KLogging
import org.springframework.stereotype.Service

@Service
class CourseService(private val courseRepository: CourseRepository) {

    companion object : KLogging()

    fun addCourse(courseDTO: CourseDTO): CourseDTO {
        val courseEntity = courseDTO.let {
            Course(it.id, it.name, it.category)
        }
        courseRepository.save(courseEntity)
        logger.info("Saved course is: $courseEntity")
        return courseEntity.let {
            CourseDTO(it.id, it.name, it.category)
        }
    }

    fun getAllCourse(): List<CourseDTO> {
        val courses = courseRepository.findAll()
        return courses.map { CourseDTO(it.id, it.name, it.category) }
    }

    fun updateCourse(id: Int, course: CourseDTO): CourseDTO {
        val existingCourse = courseRepository.findById(id)

        return if (existingCourse.isPresent) {
            existingCourse.get().let {
                it.name = course.name
                it.category = course.category
                courseRepository.save(it)
                CourseDTO(it.id, it.name, it.category)
            }
        } else {
            throw CourseNotFoundException("No course found for the id")
        }

    }

    fun deleteCourse(id: Int) {
        val existingCourse = courseRepository.findById(id)
        if (existingCourse.isPresent) {
            existingCourse.get().let {
                courseRepository.delete(it)
            }
        } else {
            throw CourseNotFoundException("No course found for the id")
        }
    }
}

// DB knows only about the entity and user/ui knows about the dto.