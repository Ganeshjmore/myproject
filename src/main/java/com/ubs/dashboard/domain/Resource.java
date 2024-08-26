package com.ubs.dashboard.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Resource.
 */
@Entity
@Table(name = "resource")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Resource implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "gpin", nullable = false)
    private String gpin;

    @NotNull
    @Column(name = "role", nullable = false)
    private String role;

    @OneToMany(mappedBy = "resource")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "comments", "ratings", "resource" }, allowSetters = true)
    private Set<Objective> objectives = new HashSet<>();

    @OneToMany(mappedBy = "resource")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "resource" }, allowSetters = true)
    private Set<ProductionRelease> productionReleases = new HashSet<>();

    @OneToMany(mappedBy = "resource")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "resource" }, allowSetters = true)
    private Set<KudosPoint> kudosPoints = new HashSet<>();

    @OneToMany(mappedBy = "resource")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "resource" }, allowSetters = true)
    private Set<GitlabContributionMatrix> gitlabContributions = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "resources" }, allowSetters = true)
    private Manager manager;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Resource id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Resource name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public Resource email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGpin() {
        return this.gpin;
    }

    public Resource gpin(String gpin) {
        this.setGpin(gpin);
        return this;
    }

    public void setGpin(String gpin) {
        this.gpin = gpin;
    }

    public String getRole() {
        return this.role;
    }

    public Resource role(String role) {
        this.setRole(role);
        return this;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<Objective> getObjectives() {
        return this.objectives;
    }

    public void setObjectives(Set<Objective> objectives) {
        if (this.objectives != null) {
            this.objectives.forEach(i -> i.setResource(null));
        }
        if (objectives != null) {
            objectives.forEach(i -> i.setResource(this));
        }
        this.objectives = objectives;
    }

    public Resource objectives(Set<Objective> objectives) {
        this.setObjectives(objectives);
        return this;
    }

    public Resource addObjectives(Objective objective) {
        this.objectives.add(objective);
        objective.setResource(this);
        return this;
    }

    public Resource removeObjectives(Objective objective) {
        this.objectives.remove(objective);
        objective.setResource(null);
        return this;
    }

    public Set<ProductionRelease> getProductionReleases() {
        return this.productionReleases;
    }

    public void setProductionReleases(Set<ProductionRelease> productionReleases) {
        if (this.productionReleases != null) {
            this.productionReleases.forEach(i -> i.setResource(null));
        }
        if (productionReleases != null) {
            productionReleases.forEach(i -> i.setResource(this));
        }
        this.productionReleases = productionReleases;
    }

    public Resource productionReleases(Set<ProductionRelease> productionReleases) {
        this.setProductionReleases(productionReleases);
        return this;
    }

    public Resource addProductionReleases(ProductionRelease productionRelease) {
        this.productionReleases.add(productionRelease);
        productionRelease.setResource(this);
        return this;
    }

    public Resource removeProductionReleases(ProductionRelease productionRelease) {
        this.productionReleases.remove(productionRelease);
        productionRelease.setResource(null);
        return this;
    }

    public Set<KudosPoint> getKudosPoints() {
        return this.kudosPoints;
    }

    public void setKudosPoints(Set<KudosPoint> kudosPoints) {
        if (this.kudosPoints != null) {
            this.kudosPoints.forEach(i -> i.setResource(null));
        }
        if (kudosPoints != null) {
            kudosPoints.forEach(i -> i.setResource(this));
        }
        this.kudosPoints = kudosPoints;
    }

    public Resource kudosPoints(Set<KudosPoint> kudosPoints) {
        this.setKudosPoints(kudosPoints);
        return this;
    }

    public Resource addKudosPoints(KudosPoint kudosPoint) {
        this.kudosPoints.add(kudosPoint);
        kudosPoint.setResource(this);
        return this;
    }

    public Resource removeKudosPoints(KudosPoint kudosPoint) {
        this.kudosPoints.remove(kudosPoint);
        kudosPoint.setResource(null);
        return this;
    }

    public Set<GitlabContributionMatrix> getGitlabContributions() {
        return this.gitlabContributions;
    }

    public void setGitlabContributions(Set<GitlabContributionMatrix> gitlabContributionMatrices) {
        if (this.gitlabContributions != null) {
            this.gitlabContributions.forEach(i -> i.setResource(null));
        }
        if (gitlabContributionMatrices != null) {
            gitlabContributionMatrices.forEach(i -> i.setResource(this));
        }
        this.gitlabContributions = gitlabContributionMatrices;
    }

    public Resource gitlabContributions(Set<GitlabContributionMatrix> gitlabContributionMatrices) {
        this.setGitlabContributions(gitlabContributionMatrices);
        return this;
    }

    public Resource addGitlabContributions(GitlabContributionMatrix gitlabContributionMatrix) {
        this.gitlabContributions.add(gitlabContributionMatrix);
        gitlabContributionMatrix.setResource(this);
        return this;
    }

    public Resource removeGitlabContributions(GitlabContributionMatrix gitlabContributionMatrix) {
        this.gitlabContributions.remove(gitlabContributionMatrix);
        gitlabContributionMatrix.setResource(null);
        return this;
    }

    public Manager getManager() {
        return this.manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public Resource manager(Manager manager) {
        this.setManager(manager);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Resource)) {
            return false;
        }
        return id != null && id.equals(((Resource) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Resource{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", gpin='" + getGpin() + "'" +
            ", role='" + getRole() + "'" +
            "}";
    }
}
