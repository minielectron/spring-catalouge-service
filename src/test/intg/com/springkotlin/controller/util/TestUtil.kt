package com.springkotlin.controller.util

import com.springkotlin.controller.entity.Course

object CourseUtil {

    fun getCourseList() : List<Course>{

        return listOf(
            Course(id = null, name = "Artificial Intelligence", category = "CS"),
            Course(id = null, name = "Electronics", category = "CS"),
            Course(id = null, name = "JAVA", category = "IT"),
            Course(id = null, name = "Machine Learning", category = "CS"),
            Course(id = null, name = "Jetpack Compose", category = "IT"),
        )
    }

}