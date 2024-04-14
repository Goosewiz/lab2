package ru.web.lab2.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import ru.web.lab2.models.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAllByDescriptionContainingIgnoreCaseOrTitleContainingIgnoreCase(String title, String sameTitle);
    @Query(value = "update public.project set title = :title, description = :description, date_start = :date_start, date_end = :date_end where id = :id", nativeQuery = true)
    void update(String title, String description, LocalDate date_start, LocalDate date_end, long id);
    Optional<Project> findById(Long id);
}