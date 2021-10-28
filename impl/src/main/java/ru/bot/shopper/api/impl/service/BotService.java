package ru.bot.shopper.api.impl.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.bot.shopper.api.impl.service.enums.LoggerLevel;
import ru.bot.shopper.api.impl.service.enums.LoggerValue;

public interface BotService {

    default void log(Class<? extends BotService> service,
                     LoggerLevel level,
                     String method,
                     LoggerValue value) {
        Logger logger = LogManager.getLogger(service);
        switch (level) {
            default: {
                logger.info(method + value.description);
                break;
            }
            case WARN: {
                logger.warn(method + value.description);
                break;
            }
            case DEBUG: {
                logger.debug(method + value.description);
                break;
            }
            case TRACE: {
                logger.trace(method + value.description);
                break;
            }
            case ERROR: {
                logger.error(method + value.description);
                break;
            }
        }
    }
}
