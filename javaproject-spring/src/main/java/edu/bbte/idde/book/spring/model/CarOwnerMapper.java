package edu.bbte.idde.book.spring.model;

import edu.bbte.idde.book.spring.model.dto.CarOwnerInDto;
import edu.bbte.idde.book.spring.model.dto.CarOwnerInNoWhenDto;
import edu.bbte.idde.book.spring.model.dto.CarOwnerOutDto;
import edu.bbte.idde.book.spring.model.dto.CarOwnerOutNoWhenDto;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class CarOwnerMapper {
    public abstract CarOwner carOwnerFromDto(CarOwnerInDto carOwnerDto);

    public abstract CarOwnerOutDto dtoFromCarOwner(CarOwner carOwner);

    @IterableMapping(elementTargetType = CarOwnerOutDto.class)
    public abstract Collection<CarOwnerOutDto> dtosFromCarOwners(Collection<CarOwner> carOwners);

    //public abstract CarOwner carOwnerFromDtoNoWhen(CarOwnerInNoWhenDto carOwnerDto);

    //public abstract CarOwnerOutNoWhenDto dtoFromCarOwnerNoWhen(CarOwner carOwner);
    //errort add erre ^ de probalkoztam examples alapjan

}
