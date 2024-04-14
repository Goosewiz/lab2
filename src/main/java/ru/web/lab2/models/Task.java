package ru.web.lab2.models;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="task", schema="public")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;  
    private String title;
    private String description;
    private LocalDate date_end;
    private boolean is_done;
    @ManyToOne(fetch = FetchType.EAGER)//?ALL REMOVE
    @JoinColumn(name = "id_project", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Project project; 
}
