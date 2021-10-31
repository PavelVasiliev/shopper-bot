package ru.bot.shopper.api.impl.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.bot.shopper.api.impl.service.enums.LoggerLevel;

public interface BotService {

    default void log(Class<? extends BotService> service,
                     LoggerLevel level,
                     String method,
                     String value) {
        Logger logger = LogManager.getLogger(service);
        switch (level) {
            default: {
                logger.info(method + value);
                break;
            }
            case WARN: {
                logger.warn(method + value);
                break;
            }
            case DEBUG: {
                logger.debug(method + value);
                break;
            }
            case TRACE: {
                logger.trace(method + value);
                break;
            }
            case ERROR: {
                logger.error(method + value);
                break;
            }
        }
    }


}
