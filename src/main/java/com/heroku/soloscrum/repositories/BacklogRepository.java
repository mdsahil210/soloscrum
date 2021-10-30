package com.heroku.soloscrum.repositories;

import com.heroku.soloscrum.model.Backlog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BacklogRepository extends CrudRepository<Backlog, Long> {

    Backlog findBacklogByProjectIdentifier(String Identifier);
}
