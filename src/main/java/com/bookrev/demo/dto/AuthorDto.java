package com.bookrev.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDto {
    private Long authorId;
    private String authorName;
    private String authorImgUrl;
    private String authorBio;
    private String authorDOB;
    private Set<Long> bookIds; // List of Book IDs the author is linked to
}