package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.dao.exceptions.TeacherDAOException;
import gr.aueb.cf.schoolapp.dto.FiltersTeacherDTO;
import gr.aueb.cf.schoolapp.dto.TeacherInsertDTO;
import gr.aueb.cf.schoolapp.dto.TeacherUpdateDTO;
import gr.aueb.cf.schoolapp.model.Teacher;
import gr.aueb.cf.schoolapp.service.exceptions.TeacherNotFoundException;

import java.util.List;

public interface ITeacherService {
    Teacher insertTeacher(TeacherInsertDTO dto) throws TeacherDAOException;
    Teacher updateTeacher(TeacherUpdateDTO dto) throws TeacherDAOException, TeacherNotFoundException;
    void deleteTeacher(int id) throws TeacherDAOException, TeacherNotFoundException;
    Teacher getTeacherById(int id) throws TeacherDAOException, TeacherNotFoundException;
    List<Teacher> getFilteredTeachers(FiltersTeacherDTO filters) throws TeacherDAOException;
}
