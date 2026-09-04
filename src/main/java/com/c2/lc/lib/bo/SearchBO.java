package com.c2.lc.lib.bo;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchBO extends PageBO {

    @Size(min = 3, message = "Minimum of {min} characters required!")
    @SerializedName("c_search_term")
    private String searchTerm;

    public SearchBO(String searchString, int page, int limit) {
        this.searchTerm = searchString;
        this.setPage(page);
        this.setLimit(limit);
    }

    @Size(min = 3, message = "Minimum of {min} characters required!")
    @SerializedName("c_in_search_term")
    private String inSearchTerm;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SearchBO searchBO = (SearchBO) o;
        return Objects.equals(searchTerm, searchBO.searchTerm) && Objects.equals(inSearchTerm, searchBO.inSearchTerm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), searchTerm, inSearchTerm);
    }
}
