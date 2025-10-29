package com.bookrev.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private Long bookId;
    private String bookName;
    private String bookImgUrl;
    private List<Long> authorIds;
    private List<Long> genreIds;
}
