package com.hirepro.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PaginationUtil {
    public static Pageable getPageable(int page, int size, String[] sort) {
        if (sort.length >= 2) {
            String property = sort[0];
            String direction = sort[1];
            return PageRequest.of(page, size,
                    Sort.by(Sort.Direction.fromString(direction), property));
        }
        return PageRequest.of(page, size);
    }
}