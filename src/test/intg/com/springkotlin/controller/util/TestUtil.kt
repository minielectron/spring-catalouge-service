package com.springkotlin.controller.util

import com.springkotlin.controller.entity.Course

object CourseUtil {

    fun getCourseList() : List<Course>{

        return listOf(
            Course(id = null, name = "Artificial Intelligence test", category = "CS"),
            Course(id = null, name = "Electronics test", category = "CS"),
            Course(id = null, name = "JAVA wiremark", category = "IT"),
            Course(id = null, name = "Machine Learning", category = "CS"),
            Course(id = null, name = "Jetpack Compose", category = "IT"),
        )
    }

}