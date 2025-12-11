package ru.rsreu.lab.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rsreu.lab.model.entity.Format;

@Repository("formatRepositoryJpa")
public interface FormatRepository extends JpaRepository<Format, Long> {

}
