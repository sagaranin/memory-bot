package ru.larnerweb.memorybot.config;

import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Configuration
public class Buttons {

    public static InlineKeyboardButton addCardButton = InlineKeyboardButton.builder()
            .callbackData("/ac")
            .text("➕ Добавить карту")
            .build();

    public static InlineKeyboardButton settingsButton = InlineKeyboardButton.builder()
            .callbackData("/settings")
            .text("⚒ Настройки")
            .build();

    public static InlineKeyboardButton helpButton = InlineKeyboardButton.builder()
            .callbackData("/help")
            .text("\uD83D\uDCA1 Помощь")
            .build();

    public static InlineKeyboardButton homeButton = InlineKeyboardButton.builder()
            .callbackData("/start")
            .text("Главный экран")
            .build();
}
