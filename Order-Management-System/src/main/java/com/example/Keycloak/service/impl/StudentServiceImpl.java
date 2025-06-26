package com.example.Keycloak.service.impl;

import com.example.Keycloak.DTO.StudentDTO;
import com.example.Keycloak.mapper.StudentMapper;
import com.example.Keycloak.model.Student;
import com.example.Keycloak.repository.StudentRepository;
import com.example.Keycloak.service.StudentService;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentMapper studentMapper;
    private final StudentRepository studentRepository;

    @Override
    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(studentMapper::toDTO)
                .toList();
    }

    @Override
    public StudentDTO getStudentById(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("Student not found with id: " + studentId));

        return studentMapper.toDTO(student);
    }

    @Override
    public StudentDTO createStudent(StudentDTO studentDTO) {
        Student student = studentMapper.toModel(studentDTO);

        Student saved = studentRepository.save(student);

        return studentMapper.toDTO(saved);
    }

    @Override
    public StudentDTO updateStudent(Long studentId, StudentDTO studentDTO) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("Student not found with id: " + studentId));

        if (student == null) {
            throw new NotFoundException("Student not found with id: " + studentId);
        }
        student.setId(studentDTO.id());
        student.setName(studentDTO.name());
        student.setEmail(studentDTO.email());

        Student saved = studentRepository.save(student);

        return studentMapper.toDTO(saved);

    }

    @Override
    public void deleteStudent(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new NotFoundException("Student not found with id: " + studentId);
        }
        studentRepository.deleteById(studentId);
    }
}
