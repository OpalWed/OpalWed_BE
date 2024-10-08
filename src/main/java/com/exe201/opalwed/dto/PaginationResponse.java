package com.exe201.opalwed.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaginationResponse<T> {

    private List<T> content;
    @JsonProperty("page-no")
    private Integer pageNo;
    @JsonProperty("page-size")
    private Integer pageSize;
    @JsonProperty("total-element")
    private Long totalElement;
    @JsonProperty("total-page")
    private Integer totalPage;
    @JsonProperty("is-last-page")
    private Boolean isLastPage;
    @JsonProperty("is-first-page")
    private Boolean isFirstPage;

    public PaginationResponse(Page<T> page){
        this.content = page.getContent();
        this.pageNo = page.getNumber();
        this.pageSize = page.getSize();
        this.totalElement = page.getTotalElements();
        this.totalPage = page.getTotalPages();
        this.isFirstPage = page.isFirst();
        this.isLastPage = page.isLast();
    }

    public PaginationResponse(Collection<T> collection) {
        this.content = collection.stream().toList();
        this.pageNo = 0;
        this.pageSize = collection.size();
        this.totalElement = (long) collection.size();
        this.totalPage = 1;
        this.isFirstPage = true;
        this.isLastPage = true;
    }

}
