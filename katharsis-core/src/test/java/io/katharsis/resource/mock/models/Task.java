package io.katharsis.resource.mock.models;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.katharsis.resource.annotations.JsonApiId;
import io.katharsis.resource.annotations.JsonApiIncludeByDefault;
import io.katharsis.resource.annotations.JsonApiLinksInformation;
import io.katharsis.resource.annotations.JsonApiLookupIncludeAutomatically;
import io.katharsis.resource.annotations.JsonApiMetaInformation;
import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToMany;
import io.katharsis.resource.annotations.JsonApiToOne;
import io.katharsis.response.LinksInformation;
import io.katharsis.response.MetaInformation;

import java.util.ArrayList;
import java.util.List;

@JsonApiResource(type = "tasks")
@JsonPropertyOrder(alphabetic = true)
public class Task {

    @JsonApiId
    private Long id;

    private String name;

    @JsonApiToOne(opposite = "tasks")
    @JsonApiIncludeByDefault
    private Project project;

    @JsonApiToMany
    private List<Project> projectsInit = new ArrayList<>();


    @JsonApiToMany(lazy = false)
    private List<Project> projects;

    @JsonApiToOne
    @JsonApiLookupIncludeAutomatically
    private Project includedProject;

    @JsonApiToMany
    @JsonApiLookupIncludeAutomatically
    private List<Project> includedProjects;

    @JsonApiMetaInformation
    private MetaInformation metaInformation;

    @JsonApiLinksInformation
    private LinksInformation linksInformation;

    private List<Task> otherTasks;

    public List<Task> getOtherTasks() {
        return otherTasks;
    }

    public Task setOtherTasks(List<Task> otherTasks) {
        this.otherTasks = otherTasks;
        return this;
    }

    public Long getId() {
        return id;
    }

    public Task setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(@SuppressWarnings("SameParameterValue") String name) {
        this.name = name;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public Project getIncludedProject() {
        return includedProject;
    }

    public void setIncludedProject(Project includedProject) {
        this.includedProject = includedProject;
    }

    public List<Project> getIncludedProjects() {
        return includedProjects;
    }

    public void setIncludedProjects(List<Project> includedProjects) {
        this.includedProjects = includedProjects;
    }

    public MetaInformation getMetaInformation() {
        return metaInformation;
    }

    public Task setMetaInformation(MetaInformation metaInformation) {
        this.metaInformation = metaInformation;
        return this;
    }

    public LinksInformation getLinksInformation() {
        return linksInformation;
    }

    public Task setLinksInformation(LinksInformation linksInformation) {
        this.linksInformation = linksInformation;
        return this;
    }

    public List<Project> getProjectsInit() {
        return projectsInit;
    }

    public void setProjectsInit(List<Project> projectsInit) {
        this.projectsInit = projectsInit;
    }
}
