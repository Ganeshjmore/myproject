package com.ubs.dashboard.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A KudosPoint.
 */
@Entity
@Table(name = "kudos_point")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class KudosPoint implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "given_by", nullable = false)
    private String givenBy;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "comments")
    private String comments;

    @NotNull
    @Column(name = "points", nullable = false)
    private Integer points;

    @NotNull
    @Column(name = "category", nullable = false)
    private String category;

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

    public KudosPoint id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGivenBy() {
        return this.givenBy;
    }

    public KudosPoint givenBy(String givenBy) {
        this.setGivenBy(givenBy);
        return this;
    }

    public void setGivenBy(String givenBy) {
        this.givenBy = givenBy;
    }

    public String getComments() {
        return this.comments;
    }

    public KudosPoint comments(String comments) {
        this.setComments(comments);
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getPoints() {
        return this.points;
    }

    public KudosPoint points(Integer points) {
        this.setPoints(points);
        return this;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getCategory() {
        return this.category;
    }

    public KudosPoint category(String category) {
        this.setCategory(category);
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Resource getResource() {
        return this.resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public KudosPoint resource(Resource resource) {
        this.setResource(resource);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KudosPoint)) {
            return false;
        }
        return id != null && id.equals(((KudosPoint) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KudosPoint{" +
            "id=" + getId() +
            ", givenBy='" + getGivenBy() + "'" +
            ", comments='" + getComments() + "'" +
            ", points=" + getPoints() +
            ", category='" + getCategory() + "'" +
            "}";
    }
}
