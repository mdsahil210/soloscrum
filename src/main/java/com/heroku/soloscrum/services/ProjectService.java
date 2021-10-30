package com.heroku.soloscrum.services;

import com.heroku.soloscrum.model.Backlog;
import com.heroku.soloscrum.model.Project;
import com.heroku.soloscrum.model.User;
import com.heroku.soloscrum.exceptions.ProjectIdException;
import com.heroku.soloscrum.exceptions.ProjectNotFoundException;
import com.heroku.soloscrum.repositories.BacklogRepository;
import com.heroku.soloscrum.repositories.ProjectRepository;
import com.heroku.soloscrum.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private UserRepository userRepository;

    public Project saveOrUpdateProject(Project project, String username) {

        String projectIdentifier = project.getProjectIdentifier().toUpperCase();
        if(project.getId()!=null){
            findProjectByIdentifier(project.getProjectIdentifier(),username);
        }
        try{
            User user = userRepository.findByUsername(username);
            project.setUser(user);
            project.setProjectLeader(user.getUsername());

            project.setProjectIdentifier(projectIdentifier);
            if(project.getId() == null) {
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(projectIdentifier);
            } else{
                project.setBacklog(backlogRepository.findBacklogByProjectIdentifier(projectIdentifier));
            }

            return projectRepository.save(project);
        }
        catch (Exception ex) {
            throw new ProjectIdException("Project ID '"+projectIdentifier+"' already exists.");
        }


    }

    public Project findProjectByIdentifier(String projectId, String username){
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if(project == null) {
            throw new ProjectIdException("Project ID '"+projectId+"' does not exist.");
        }

        if(!project.getProjectLeader().equals(username)){
            throw new ProjectNotFoundException("Project not found in your account");
        }
        return project;
    }

    public Iterable<Project> findAllProjects(String username) {
        return projectRepository.findAllByProjectLeader(username);
    }

    public void deleteProjectByIdentifier(String projectId, String username) {
        projectRepository.delete(findProjectByIdentifier(projectId,username));
    }
}
