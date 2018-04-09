package com.sg.webservice.util;

import java.util.ArrayList;
import java.util.List;

public class PagingUtils {

    public static List<Integer> getPageNumbers(int startPage, int numberOfPages) {
        List<Integer> pages = new ArrayList<>();

        for (int i = startPage; i < numberOfPages + startPage; i++ ) {
            pages.add(i);
        }
        return pages;
    }

    public static Integer calculatePageNumber(int limit, int offset) {
        return offset/limit + 1;
    }

}
