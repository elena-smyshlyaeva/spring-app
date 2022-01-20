package ru.sumbirsoft.chat.utils;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;
import org.springframework.beans.factory.annotation.Value;
import ru.sumbirsoft.chat.domain.Videos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class YoutubeSearch {

    @Value("${youtube.apikey}")
    private String apikey;
    private YouTube youtube;

    public YoutubeSearch() {
        if (youtube == null) {
            youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
                @Override
                public void initialize(HttpRequest httpRequest) throws IOException {
                }
            }).setApplicationName("youtube-api-chat").build();
        }
    }

    public String getChannelId(String channel) throws Exception {
        String channelId = null;
        YouTube.Search.List search = youtube.search().list(Collections.singletonList("id, snippet"));
        search.setKey(apikey);
        search.setQ(channel);
        search.setType(Collections.singletonList("channel"));
        search.setMaxResults(25L);

        SearchListResponse response = search.execute();
        List<SearchResult> searchResultList = response.getItems();
        if (searchResultList != null) {
            for (SearchResult singleChannel : searchResultList) {
                //skip videos and playlist
                if (singleChannel.getId().getKind().equals("youtube#channel") &
                        singleChannel.getSnippet().getTitle().equals(channel)) {
                    channelId = singleChannel.getId().getChannelId();
                    return channelId;
                }
            }
        }
        return null;
    }

    public List<Videos> getVideoList(String movie, String channelId, Long resultCount, Boolean sort) throws IOException {
        List<Videos> videosList = new ArrayList<>();

        YouTube.Search.List search = youtube.search().list(Collections.singletonList("id, snippet"));
        search.setKey(apikey);

        if (!movie.isEmpty())
            search.setQ(movie);

        search.setType(Collections.singletonList("video"));

        if (sort) {
            search.setOrder("date");
        }

        if (!channelId.isEmpty())
            search.setChannelId(channelId);

        search.setFields("items(id/kind,id/videoId,snippet/title,snippet/publishedAt)");
        if (resultCount > 10)
            search.setMaxResults(resultCount);

        SearchListResponse response = search.execute();
        List<SearchResult> searchResultList = response.getItems();
        if (searchResultList != null) {
            for (SearchResult singleVideo : searchResultList) {
                ResourceId resourceId = singleVideo.getId();

                if (resourceId.getKind().equals("youtube#video")) {
                    Videos videos = new Videos();
                    videos.setId(resourceId.getVideoId());
                    videos.setTitle(singleVideo.getSnippet().getTitle());
                    videos.setPublished(new Date(singleVideo.getSnippet().getPublishedAt().getValue()));

                    YouTube.Videos.List youtubeVideos = youtube
                            .videos()
                            .list(Collections.singletonList("id,snippet,player,contentDetails,statistics"))
                            .setId(Collections.singletonList(resourceId.getVideoId()));
                    youtubeVideos.setKey(apikey);

                    VideoListResponse videoListResponse = youtubeVideos.execute();

                    if (!videoListResponse.getItems().isEmpty()) {
                        Video video = videoListResponse.getItems().get(0);
                        videos.setViewCount(video.getStatistics().getViewCount().longValue());
                        videos.setLikeCount(video.getStatistics().getLikeCount().longValue());
                        videos.setDislikeCount(video.getStatistics().getDislikeCount().longValue());
                    }

                    videosList.add(videos);
                }
            }
        }

        return videosList;
    }
}