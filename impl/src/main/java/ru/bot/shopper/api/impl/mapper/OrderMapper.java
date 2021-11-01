package ru.bot.shopper.api.impl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.bot.shopper.api.model.OrderDto;
import ru.bot.shopper.model.Order;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    Order toEntity(OrderDto dto);
    OrderDto toDTO(Order order);
}
