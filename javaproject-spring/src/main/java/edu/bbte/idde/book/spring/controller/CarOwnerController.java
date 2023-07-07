package edu.bbte.idde.book.spring.controller;

import edu.bbte.idde.book.spring.dao.CarOwnerDao;
import edu.bbte.idde.book.spring.model.CarOwner;
import edu.bbte.idde.book.spring.model.CarOwnerMapper;
import edu.bbte.idde.book.spring.model.dto.CarOwnerInDto;
import edu.bbte.idde.book.spring.model.dto.CarOwnerOutDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/carOwners")
public class CarOwnerController {
    private static final Logger LOG = LoggerFactory.getLogger(CarOwnerController.class);
    @Autowired
    private CarOwnerDao carOwnerDao;
    @Autowired
    private CarOwnerMapper carOwnerMapper;

    @GetMapping
    public Collection<CarOwnerOutDto> findAll() {
        return carOwnerMapper.dtosFromCarOwners(carOwnerDao.findAll());
    }

    @PostMapping("/{id}")
    public void create(@RequestBody @Valid CarOwnerInDto carOwnerDto, @PathVariable("id") Long id) {
        carOwnerDto.setId(Math.toIntExact(id));
        carOwnerDao.saveAndFlush(carOwnerMapper.carOwnerFromDto(carOwnerDto));
    }

    @GetMapping("/{id}")
    public CarOwnerOutDto findById(@PathVariable("id") Long id, @RequestParam(required = false) String when) {
        Optional<CarOwner> optional = carOwnerDao.findById(id);
        if (optional.isEmpty()) {
            throw new NotFoundException("CarOwner with id of " + id + " not found");
        }
        if(when == null){
            throw new NoWhenException("When not given as parameter");
        }
        else if(!when.equals("on") && !when.equals("off")){
            String throwstring = "Value not supported: ";
            throwstring = throwstring + when.toString();
            LOG.info(throwstring);
            throw new WrongWhenException(throwstring);
        }
        CarOwner carOwner = optional.get();
        if(when.equals("on")){
            carOwner.setRequestDate(Instant.now());
        }
        return carOwnerMapper.dtoFromCarOwner(carOwner);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id") Long id, @RequestBody @Valid CarOwnerInDto carOwnerDto) {
        Optional<CarOwner> optional = carOwnerDao.findById(id);
        if (optional.isEmpty()) {
            throw new NotFoundException("nincs megadott id-ju");
        }
        carOwnerDto.setId(Math.toIntExact(id));
        carOwnerDao.saveAndFlush(carOwnerMapper.carOwnerFromDto(carOwnerDto));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        Optional<CarOwner> optional = carOwnerDao.findById(id);
        if (optional.isEmpty()) {
            throw new NotFoundException("nincs megadott id-ju");
        }
        CarOwner carOwner = optional.get();
        carOwnerDao.delete(carOwner);
    }

}
