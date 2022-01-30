package ru.sumbirsoft.chat.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.sumbirsoft.chat.domain.Videos;
import ru.sumbirsoft.chat.utils.YoutubeSearch;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BotService {

    private final YoutubeSearch youtubeSearch;

    public String processCommand(String command, String parameters, Authentication authentication) {
        if (parameters == null) {
            if (!command.equals("help"))
                throw new IllegalArgumentException();
            return "commands:\n" +
                    "find -k -v {channel name}||{video title} (-k for likes count, -v for views count)\n" +
                    "channelinfo {channel name}\n" +
                    "videoCommentRandom {channel name}||{video title}";
        }

        if (parameters.contains("||")) {
            String[] tokens = parameters.split(" ");
            String[] channelMovie = tokens[tokens.length - 1].split("\\|\\|");

            String channelName = channelMovie[0];
            String movieName = channelMovie[1];

            try {
                String channelId = youtubeSearch.getChannelId(channelName);
                List<Videos> videosList = youtubeSearch.getVideoList(movieName, channelId, 5L, false);
                int videosCount = videosList.size();

                StringBuilder[] info = new StringBuilder[videosCount];
                for (int i = 0; i < videosCount; i++) {
                    info[i] = new StringBuilder(videosList.get(i).toString());
                }

                for (String token : tokens) {
                    if (token.equals("-k"))
                        for (int i = 0; i < videosCount; i++) {
                            info[i] = info[i].append("\nLikes: ").append(videosList.get(i).getLikeCount());
                        }
                    if (token.equals("-v")) {
                        for (int i = 0; i < videosCount; i++) {
                            info[i] = info[i].append("\nViews: ").append(videosList.get(i).getViewCount());
                        }
                    }
                }
                StringBuilder response = new StringBuilder();
                Arrays.stream(info).forEach(item -> response.append(item).append("\n\n"));

                return String.valueOf(response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        throw new IllegalArgumentException();
    }
}