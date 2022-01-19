package ru.sumbirsoft.chat.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.sumbirsoft.chat.repository.UserRepository;
import ru.sumbirsoft.chat.service.RoomService;
import ru.sumbirsoft.chat.service.UserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class CommandServiceImpl {

    private final RoomService roomService;
    private final UserService userService;

    UserRepository userRepository;

    public String parse(String message, Authentication authentication) {
        //check the pattern of beginning
        String regex = "//[a-z]{4}\\s[a-z]+\\s|[a-z]{4}\\s[a-z]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            //split message by spaces to extract commands
            String[] tokens = message.split(" ", 3);
            String header = (String) tokens[0].subSequence(2, tokens[0].length()); //room, user, yBot
            String command = tokens[1];

            String parameters = tokens.length >=3 ? tokens[2] : null; //additional parameters

            switch(header) {
                case "room": {
                    //room commands have required parameters
                    if (parameters == null)
                        throw new IllegalArgumentException();

                    return roomService.processCommand(command, parameters, authentication);
                }
                case "user": {
                    //user commands have required parameters
                    if (parameters == null)
                        throw new IllegalArgumentException();

                    return userService.processCommand(command, parameters, authentication);
                }
            }
            return null;
        }
        throw new RuntimeException();
    }
}
