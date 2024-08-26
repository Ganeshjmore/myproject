package com.ubs.dashboard.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ubs.dashboard.domain.enumeration.ObjectiveStatus;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Objective.
 */
@Entity
@Table(name = "objective")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Objective implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ObjectiveStatus status;

    @OneToMany(mappedBy = "objective")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "resource", "objective" }, allowSetters = true)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "objective")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "resource", "objective" }, allowSetters = true)
    private Set<Rating> ratings = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "objectives", "productionReleases", "kudosPoints", "gitlabContributions", "manager" },
        allowSetters = true
    )
    private Resource resource;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Objective id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Objective title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public Objective description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Objective createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public ObjectiveStatus getStatus() {
        return this.status;
    }

    public Objective status(ObjectiveStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(ObjectiveStatus status) {
        this.status = status;
    }

    public Set<Comment> getComments() {
        return this.comments;
    }

    public void setComments(Set<Comment> comments) {
        if (this.comments != null) {
            this.comments.forEach(i -> i.setObjective(null));
        }
        if (comments != null) {
            comments.forEach(i -> i.setObjective(this));
        }
        this.comments = comments;
    }

    public Objective comments(Set<Comment> comments) {
        this.setComments(comments);
        return this;
    }

    public Objective addComments(Comment comment) {
        this.comments.add(comment);
        comment.setObjective(this);
        return this;
    }

    public Objective removeComments(Comment comment) {
        this.comments.remove(comment);
        comment.setObjective(null);
        return this;
    }

    public Set<Rating> getRatings() {
        return this.ratings;
    }

    public void setRatings(Set<Rating> ratings) {
        if (this.ratings != null) {
            this.ratings.forEach(i -> i.setObjective(null));
        }
        if (ratings != null) {
            ratings.forEach(i -> i.setObjective(this));
        }
        this.ratings = ratings;
    }

    public Objective ratings(Set<Rating> ratings) {
        this.setRatings(ratings);
        return this;
    }

    public Objective addRatings(Rating rating) {
        this.ratings.add(rating);
        rating.setObjective(this);
        return this;
    }

    public Objective removeRatings(Rating rating) {
        this.ratings.remove(rating);
        rating.setObjective(null);
        return this;
    }

    public Resource getResource() {
        return this.resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Objective resource(Resource resource) {
        this.setResource(resource);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Objective)) {
            return false;
        }
        return id != null && id.equals(((Objective) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Objective{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
