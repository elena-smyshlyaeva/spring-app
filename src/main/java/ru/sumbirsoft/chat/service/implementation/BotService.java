package ru.sumbirsoft.chat.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.sumbirsoft.chat.domain.Videos;
import ru.sumbirsoft.chat.utils.YoutubeSearch;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                    "channelInfo {channel name}\n" +
                    "videoCommentRandom {channel name}||{video title}";
        }

        String regex = "\\{[\\w\\sА-Яа-я]+}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(parameters);

        //find or videoCommentRandom
        if (parameters.contains("||")) {
            String[] tokens = parameters.split(" ");

            String[] channelMovie = new String[2];
            int j = 0;
            while(matcher.find())
                channelMovie[j++] = matcher.group();

            String channelName = channelMovie[0].substring(1, channelMovie[0].length() - 1);
            String movieName = channelMovie[1].substring(1, channelMovie[1].length() - 1);

            if(command.equals("find")) {
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

            if (command.equals("videoCommentRandom")) {
                try {
                    return youtubeSearch.getComment("ujOVmKIrHqQ");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if (command.equals("channelInfo")) {
            if (!matcher.find())
                throw new IllegalArgumentException();

            String channelName = matcher.group();
            channelName = channelName.substring(1, channelName.length() - 1);
            String channelId = null;

            try {

                channelId = youtubeSearch.getChannelId(channelName);
                List<Videos> videosList = youtubeSearch.getVideoList("", channelId, 5L, true);

                StringBuilder response = new StringBuilder(channelName);
                response.append("\n");
                for (int i = 0; i < 5; i++) {
                    Videos video = videosList.get(i);
                    response.append(video.getTitle())
                            .append(": https://www.youtube.com/watch?v=")
                            .append(video.getId()).append("\n");
                }

                return response.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        throw new IllegalArgumentException();
    }
}