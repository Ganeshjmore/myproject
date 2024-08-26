package com.ubs.dashboard.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ubs.dashboard.domain.KudosPoint} entity.
 */
public class KudosPointDTO implements Serializable {

    private Long id;

    @NotNull
    private String givenBy;

    @Lob
    private String comments;

    @NotNull
    private Integer points;

    @NotNull
    private String category;

    private ResourceDTO resource;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGivenBy() {
        return givenBy;
    }

    public void setGivenBy(String givenBy) {
        this.givenBy = givenBy;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ResourceDTO getResource() {
        return resource;
    }

    public void setResource(ResourceDTO resource) {
        this.resource = resource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KudosPointDTO)) {
            return false;
        }

        KudosPointDTO kudosPointDTO = (KudosPointDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, kudosPointDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KudosPointDTO{" +
            "id=" + getId() +
            ", givenBy='" + getGivenBy() + "'" +
            ", comments='" + getComments() + "'" +
            ", points=" + getPoints() +
            ", category='" + getCategory() + "'" +
            ", resource=" + getResource() +
            "}";
    }
}
