package com.heroku.soloscrum.repositories;

import com.heroku.soloscrum.model.ProjectTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {
    Iterable<ProjectTask> findByProjectIdentifierOrderByPriority(String id);

    ProjectTask findByProjectSequence(String sequence);
}
