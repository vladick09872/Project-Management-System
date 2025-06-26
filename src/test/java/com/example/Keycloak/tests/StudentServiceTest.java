package com.example.Keycloak.tests;

import com.example.Keycloak.DTO.StudentDTO;
import com.example.Keycloak.mapper.StudentMapper;
import com.example.Keycloak.model.Student;
import com.example.Keycloak.repository.StudentRepository;
import com.example.Keycloak.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    private Student student;
    private StudentDTO studentDTO;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentMapper studentMapper;

    @InjectMocks
    private StudentServiceImpl studentService;

    @BeforeEach
    void setUp() {
        student = new Student();

        student.setId(1L);
        student.setName("John Doe");
        student.setEmail("Email123");

        studentDTO = new StudentDTO(1L, "John Doe", "Email123", null);
    }

    @Test
    public void testCreateStudent() {
        Mockito.when(studentMapper.toModel(studentDTO)).thenReturn(student);
        Mockito.when(studentRepository.save(student)).thenReturn(student);
        Mockito.when(studentMapper.toDTO(student)).thenReturn(studentDTO);

        StudentDTO res = studentService.createStudent(studentDTO);

        Assertions.assertNotNull(res);
        Assertions.assertEquals(studentDTO, res);

        Mockito.verify(studentMapper, Mockito.times(1)).toModel(studentDTO);
        Mockito.verify(studentRepository, Mockito.times(1)).save(student);
        Mockito.verify(studentMapper, Mockito.times(1)).toDTO(student);
    }

    @Test
    public void testGetAllStudents() {
        Mockito.when(studentRepository.findAll()).thenReturn(List.of(student));
        Mockito.when(studentMapper.toDTO(student)).thenReturn(studentDTO);

        List<StudentDTO> res = studentService.getAllStudents();

        Assertions.assertNotNull(res);
        Assertions.assertEquals(List.of(studentDTO), res);

        Mockito.verify(studentRepository, Mockito.times(1)).findAll();
        Mockito.verify(studentMapper, Mockito.times(1)).toDTO(student);
    }

    @Test
    public void testGetStudentBydId() {
        Mockito.when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        Mockito.when(studentMapper.toDTO(student)).thenReturn(studentDTO);

        StudentDTO res = studentService.getStudentById(1L);

        Assertions.assertNotNull(res);
        Assertions.assertEquals(studentDTO, res);

        Mockito.verify(studentRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(studentMapper, Mockito.times(1)).toDTO(student);
    }

    @Test
    public void testDeleteStudent() {
        Mockito.when(studentRepository.existsById(1L)).thenReturn(true);
        Mockito.doNothing().when(studentRepository).deleteById(1L);

        studentRepository.deleteById(1L);

        Assertions.assertNotNull(student);
        Assertions.assertTrue(true);

        Mockito.verify(studentRepository, Mockito.times(1)).existsById(1L);
        Mockito.verify(studentRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void testUpdateStudent() {
        Mockito.when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        Mockito.when(studentRepository.save(student)).thenReturn(student);
        Mockito.when(studentMapper.toDTO(student)).thenReturn(studentDTO);

        StudentDTO res = studentService.updateStudent(1L, studentDTO);

        Assertions.assertNotNull(res);
        Assertions.assertEquals(studentDTO, res);

        Mockito.verify(studentRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(studentRepository, Mockito.times(1)).save(student);
        Mockito.verify(studentMapper, Mockito.times(1)).toDTO(student);
    }
}
