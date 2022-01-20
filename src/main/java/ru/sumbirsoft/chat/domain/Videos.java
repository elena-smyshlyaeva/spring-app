package ru.sumbirsoft.chat.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Date;

@Data
@NoArgsConstructor
public class Videos {
    private String id;
    private String title;
    private Date published;

    private long viewCount;
    private long likeCount;
    private long dislikeCount;
}
