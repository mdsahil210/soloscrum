package com.heroku.soloscrum.services;

import com.heroku.soloscrum.model.Backlog;
import com.heroku.soloscrum.model.ProjectTask;
import com.heroku.soloscrum.exceptions.ProjectNotFoundException;
import com.heroku.soloscrum.repositories.BacklogRepository;
import com.heroku.soloscrum.repositories.ProjectRepository;
import com.heroku.soloscrum.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username) {
        Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier,username).getBacklog();
        projectTask.setBacklog(backlog);

        Integer backlogSequence = backlog.getPTSequence();
        backlogSequence++;

        backlog.setPTSequence(backlogSequence);

        projectTask.setProjectSequence(projectIdentifier + "-" + backlogSequence);
        projectTask.setProjectIdentifier(projectIdentifier);

        if( projectTask.getPriority()==null || projectTask.getPriority()==0 ) {
            projectTask.setPriority(3);
        }
        if( projectTask.getStatus() == null || projectTask.getStatus().equals("") ) {
            projectTask.setStatus("TO_DO");
        }
        return projectTaskRepository.save(projectTask);
    }

    public Iterable<ProjectTask> findBacklogById(String id,String username) {
        projectService.findProjectByIdentifier(id,username);
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }

    public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id, String username) {
        projectService.findProjectByIdentifier(backlog_id,username);
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
        if(projectTask == null) {
            throw new ProjectNotFoundException("Project task: '"+pt_id+"' not found");
        }
        if(!projectTask.getProjectIdentifier().equals(backlog_id)){
            throw new ProjectNotFoundException("Project Task '"+pt_id+"' does not exist in project: "+backlog_id);
        }
        return projectTask;
    }

    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id,
                                               String username) {
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id, username);
        projectTask = updatedTask;
        return projectTaskRepository.save(projectTask);
    }

    public void deletePTByProjectSequence(String backlog_id, String pt_id, String username) {
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id, username);
        projectTaskRepository.delete(projectTask);
    }
}
