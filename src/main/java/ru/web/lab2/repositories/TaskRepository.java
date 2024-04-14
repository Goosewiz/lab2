package ru.web.lab2.repositories;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import ru.web.lab2.models.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
    //@Query(value = "select * from public.task where title ilike '%' || :title || '%'", nativeQuery = true)
    //List<Task> selectByName(String title);
    @Query(value = "update public.task set title = :title, description = :description, date_end = :date_end, is_done = :is_done where id = :id", nativeQuery = true)
    void update(String title, String description, LocalDate date_end, boolean is_done, long id);

    Optional<Task> findByIdAndProjectId(Long id, Long projectId);
}
