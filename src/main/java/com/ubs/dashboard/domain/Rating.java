package com.ubs.dashboard.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ubs.dashboard.domain.enumeration.RatingLevel;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Rating.
 */
@Entity
@Table(name = "rating")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Rating implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Min(value = 1)
    @Max(value = 5)
    @Column(name = "score", nullable = false)
    private Integer score;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "level", nullable = false)
    private RatingLevel level;

    @NotNull
    @Column(name = "rated_at", nullable = false)
    private Instant ratedAt;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "objectives", "productionReleases", "kudosPoints", "gitlabContributions", "manager" },
        allowSetters = true
    )
    private Resource resource;

    @ManyToOne
    @JsonIgnoreProperties(value = { "comments", "ratings", "resource" }, allowSetters = true)
    private Objective objective;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Rating id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getScore() {
        return this.score;
    }

    public Rating score(Integer score) {
        this.setScore(score);
        return this;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public RatingLevel getLevel() {
        return this.level;
    }

    public Rating level(RatingLevel level) {
        this.setLevel(level);
        return this;
    }

    public void setLevel(RatingLevel level) {
        this.level = level;
    }

    public Instant getRatedAt() {
        return this.ratedAt;
    }

    public Rating ratedAt(Instant ratedAt) {
        this.setRatedAt(ratedAt);
        return this;
    }

    public void setRatedAt(Instant ratedAt) {
        this.ratedAt = ratedAt;
    }

    public Resource getResource() {
        return this.resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Rating resource(Resource resource) {
        this.setResource(resource);
        return this;
    }

    public Objective getObjective() {
        return this.objective;
    }

    public void setObjective(Objective objective) {
        this.objective = objective;
    }

    public Rating objective(Objective objective) {
        this.setObjective(objective);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rating)) {
            return false;
        }
        return id != null && id.equals(((Rating) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Rating{" +
            "id=" + getId() +
            ", score=" + getScore() +
            ", level='" + getLevel() + "'" +
            ", ratedAt='" + getRatedAt() + "'" +
            "}";
    }
}
