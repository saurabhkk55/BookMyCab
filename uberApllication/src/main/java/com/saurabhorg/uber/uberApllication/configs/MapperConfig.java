package com.saurabhorg.uber.uberApllication.configs;

import com.saurabhorg.uber.uberApllication.dto.PointDTO;
import com.saurabhorg.uber.uberApllication.utils.GeometryUtil;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // It will convert PointDTO into Point
        modelMapper.typeMap(PointDTO.class, Point.class).setConverter(context -> {
            // Retrieve the source object (PointDTO)
            PointDTO pointDTO = context.getSource();
            // Convert PointDTO to Point using a utility method
            return GeometryUtil.convertToPointFromPointDTO(pointDTO);
        });

        // It will convert Point into PointDTO
        modelMapper.typeMap(Point.class, PointDTO.class).setConverter(context -> {
            // Retrieve the source object (Point)
            Point point = context.getSource();
            // Convert Point to PointDTO by extracting coordinates
            double[] coordinates = {
                    point.getX(),
                    point.getY()
            };
            // Create and return a new PointDTO instance with the extracted coordinates
            return new PointDTO(coordinates);
        });
        return modelMapper;
    }
}
