package ma.fstg.tp8.service;

import ma.fstg.tp8.dto.StudentRequestDTO;
import ma.fstg.tp8.dto.StudentResponseDTO;
import ma.fstg.tp8.entity.Student;
import ma.fstg.tp8.exception.ResourceNotFoundException;
import ma.fstg.tp8.mapper.StudentMapper;
import ma.fstg.tp8.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public StudentService(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    public StudentResponseDTO addStudent(StudentRequestDTO dto) {
        Student student = studentMapper.toEntity(dto);
        Student savedStudent = studentRepository.save(student);
        return studentMapper.toResponseDTO(savedStudent);
    }

    public List<StudentResponseDTO> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(studentMapper::toResponseDTO)
                .toList();
    }

    public StudentResponseDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Étudiant introuvable avec l'id : " + id));

        return studentMapper.toResponseDTO(student);
    }

    public StudentResponseDTO updateStudent(Long id, StudentRequestDTO dto) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Étudiant introuvable avec l'id : " + id));

        studentMapper.updateEntityFromDTO(dto, student);
        Student updatedStudent = studentRepository.save(student);

        return studentMapper.toResponseDTO(updatedStudent);
    }

    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Étudiant introuvable avec l'id : " + id));

        studentRepository.delete(student);
    }
}