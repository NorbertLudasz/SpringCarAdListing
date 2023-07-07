package edu.bbte.idde.book.spring.model;

import edu.bbte.idde.book.spring.model.dto.CarAdInDto;
import edu.bbte.idde.book.spring.model.dto.CarAdOutDto;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class CarAdMapper {
    public abstract CarAd carAdFromDto(CarAdInDto carAdDto);

    public abstract CarAdOutDto dtoFromCarAd(CarAd carAd);

    @IterableMapping(elementTargetType = CarAdOutDto.class)
    public abstract Collection<CarAdOutDto> dtosFromCarAds(Collection<CarAd> carAds);
}

