package ru.sumbirsoft.chat.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Videos {
    private String id;
    private String title;
    private Date published;

    private long viewCount;
    private long likeCount;

    @Override
    public String toString() {
        return "Video's title: " + title + "\nPublished: " + published;
    }
}
