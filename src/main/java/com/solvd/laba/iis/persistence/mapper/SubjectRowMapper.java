package com.solvd.laba.iis.persistence.mapper;

import com.solvd.laba.iis.domain.Subject;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class SubjectRowMapper {
    @SneakyThrows
    public static Subject mapSubject(ResultSet rs) {
        Subject subject = new Subject();
        subject.setId(rs.getLong("subject_id"));
        subject.setName(rs.getString("subject_name"));
        return subject;
    }

    @SneakyThrows
    public static List<Subject> mapSubjects(ResultSet rs) {
        List<Subject> subjects = new ArrayList<>();
        while (rs.next()) {
            Subject subject = mapSubject(rs);
            subjects.add(subject);
        }
        return subjects;
    }
}
