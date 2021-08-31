package ru.larnerweb.memorybot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j2
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card implements Serializable {

    public static final Pattern CARD_FULL_PATTERN = Pattern.compile("(/\\w+) (.+?):(.+)");
    public static final Pattern CARD_FRONT_ONLY_PATTERN = Pattern.compile("(/\\w+) (.+)");

    private int owner;
    private long creationTime;
    private String front;
    private String frontHash;
    private String back;
    private int level;

    public Card(int owner, String taskText) {
        this.creationTime = System.currentTimeMillis();
        this.owner = owner;
        level = 0;

        Matcher cardFullMatcher = CARD_FULL_PATTERN.matcher(taskText);
        Matcher cardFrontOnlyMatcher = CARD_FRONT_ONLY_PATTERN.matcher(taskText);

        if (cardFullMatcher.find()) {
            log.info("parsing back and front from: '{}'", taskText);
            front = cardFullMatcher.group(2).trim();
            frontHash = DigestUtils.md5Hex(front);
            back = cardFullMatcher.group(3).trim();
        } else if (cardFrontOnlyMatcher.find()) {
            log.info("parsing front from: '{}'", taskText);
            front = cardFrontOnlyMatcher.group(2).trim();
            frontHash = DigestUtils.md5Hex(front);
        } else {
            log.error("Ошибка обработки запроса: {}", taskText);
            throw new RuntimeException("Ошибка обработки запроса: '" + taskText + "'");
        }

    }

    @Override
    public String toString() {
        return "Front: '" + front + "'\nBack: '"+back+"'";
    }
}
