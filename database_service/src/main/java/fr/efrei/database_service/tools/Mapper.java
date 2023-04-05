package fr.efrei.database_service.tools;

import org.modelmapper.ModelMapper;

public class Mapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static <S, D> D convert(S entity, Class<D> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }

}