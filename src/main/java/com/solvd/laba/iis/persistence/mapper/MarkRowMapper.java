package com.solvd.laba.iis.persistence.mapper;

import com.solvd.laba.iis.domain.*;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public abstract class MarkRowMapper {

    @SneakyThrows
    public static Mark mapMark(ResultSet rs) {
        Mark mark = new Mark();
        mark.setId(rs.getLong("mark_id"));
        mark.setDate(rs.getDate("mark_date").toLocalDate());
        mark.setValue(rs.getInt("mark_value"));

        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setId(rs.getLong("student_id"));
        studentInfo.setAdmissionYear(rs.getInt("student_year"));
        studentInfo.setFaculty(rs.getString("student_faculty"));
        studentInfo.setSpeciality(rs.getString("student_speciality"));
        UserInfo student = new UserInfo();
        student.setId(rs.getLong("student_user_id"));
        student.setName(rs.getString("student_user_name"));
        student.setSurname(rs.getString("student_user_surname"));
        student.setEmail(rs.getString("student_user_email"));
        student.setPassword(rs.getString("student_user_password"));
        student.setRole(Role.valueOf(rs.getString("student_user_role").toUpperCase()));
        studentInfo.setUser(student);
        Group group = GroupRowMapper.mapGroup(rs);
        studentInfo.setGroup(group);
        mark.setStudent(studentInfo);

        TeacherInfo teacherInfo = new TeacherInfo();
        teacherInfo.setId(rs.getLong("teacher_id"));
        UserInfo teacher = new UserInfo();
        teacher.setId(rs.getLong("teacher_user_id"));
        teacher.setName(rs.getString("teacher_user_name"));
        teacher.setSurname(rs.getString("teacher_user_surname"));
        teacher.setEmail(rs.getString("teacher_user_email"));
        teacher.setPassword(rs.getString("teacher_user_password"));
        teacher.setRole(Role.valueOf(rs.getString("teacher_user_role").toUpperCase()));
        teacherInfo.setUser(teacher);
        mark.setTeacher(teacherInfo);

        Subject subject = SubjectRowMapper.mapSubject(rs);
        mark.setSubject(subject);
        return mark;
    }

    @SneakyThrows
    public static List<Mark> mapMarks(ResultSet rs) {
        List<Mark> marks = new ArrayList<>();
        while (rs.next()) {
            Mark mark = mapMark(rs);
            marks.add(mark);
        }
        return marks;
    }

}
