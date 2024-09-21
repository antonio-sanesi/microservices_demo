package it.asanesi.order_service.config;

import it.asanesi.order_service.client.ProductServiceClient;
import it.asanesi.order_service.dto.OrderDTO;
import it.asanesi.order_service.entity.OrderEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
@RequiredArgsConstructor
public class MapperConfig {

    private final ProductServiceClient productServiceClient;

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        // OrderDTO -> OrderEntity
        modelMapper.createTypeMap(OrderDTO.class, OrderEntity.class).setConverter(mappingContext -> {
            var input = mappingContext.getSource();
            var output = new OrderEntity();

            var prodottoReale = productServiceClient.getProductById(input.getProduct().getId());

            var calculatedPrice = prodottoReale.getPrice().multiply(BigDecimal.valueOf(input.getQuantity()));

            output.setProductId(input.getProduct().getId());
            output.setQuantity(input.getQuantity());
            output.setPrice(calculatedPrice);

            return output;
        });

        // OrderEntity -> OrderDTO
        modelMapper.createTypeMap(OrderEntity.class, OrderDTO.class).setConverter(mappingContext -> {
            var input = mappingContext.getSource();
            var output = new OrderDTO();

            var prodottoReale = productServiceClient.getProductById(input.getProductId());

            output.setId(input.getId());
            output.setProduct(prodottoReale);
            output.setQuantity(input.getQuantity());
            output.setPrice(input.getPrice());

            return output;
        });

        return modelMapper;
    }
}