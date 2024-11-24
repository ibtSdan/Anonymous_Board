package com.example.anonymous_board.post.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiPagination<T> {
    private T body;
    private Pagination pagination;
}
