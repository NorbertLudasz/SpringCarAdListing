package edu.bbte.idde.book.spring.controller;

import edu.bbte.idde.book.spring.dao.CarAdDao;
import edu.bbte.idde.book.spring.dao.CarOwnerDao;
import edu.bbte.idde.book.spring.model.CarAd;
import edu.bbte.idde.book.spring.model.CarAdMapper;
import edu.bbte.idde.book.spring.model.CarOwner;
import edu.bbte.idde.book.spring.model.dto.CarAdInDto;
import edu.bbte.idde.book.spring.model.dto.CarAdOutDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/carAds")
public class CarAdController {
    private static final Logger LOG = LoggerFactory.getLogger(ConnectedController.class);
    @Autowired
    private CarAdDao carAdDao;
    @Autowired
    private CarAdMapper carAdMapper;
    @Autowired
    private CarOwnerDao carOwnerDao;

    @GetMapping("")
    @ResponseBody
    public Collection<CarAdOutDto> findAll(@RequestParam(required = false) Integer yearOfMake) {
        if (yearOfMake == null) {
            LOG.info("findall meghivas");
            return carAdMapper.dtosFromCarAds(carAdDao.findAll());
        }
        return carAdMapper.dtosFromCarAds(carAdDao.findByYearOfMake(yearOfMake));
    }

    @GetMapping("/{id}")
    public CarAdOutDto findById(@PathVariable("id") Long id) {
        Optional<CarAd> optional = carAdDao.findById(id);
        if (optional.isEmpty()) {
            throw new NotFoundException("CarAd with id of " + id + " not found");
        }

        CarAd carAd = optional.get();
        LOG.info("got carad with id of {}", id);
        return carAdMapper.dtoFromCarAd(carAd);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        Optional<CarAd> result = carAdDao.findById(id);
        if (result.isEmpty()) {
            throw new NotFoundException("nincs megadott id-ju");
        }
        //CarAd carAd = result.get();

        ////carAd.setCarOwner(null);
        ////carAdDao.saveAndFlush(carAd);

        ///carAdDao.delete(carAd);*/

        carAdDao.deleteById(id);
        LOG.info("deleted carad with id of {}", id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public CarAdOutDto update(@RequestBody @Valid CarAdInDto carAdDto, @PathVariable("id") Long id) {
        Optional<CarOwner> optional = carOwnerDao.findById(id);
        if (optional.isEmpty()) {
            throw new NotFoundException("CarOwner with id = " + id + " not found.");
        }

        CarOwner carOwner = optional.get();

        CarAd carAd = carAdMapper.carAdFromDto(carAdDto);
        carAd.setCarOwner(carOwner);
        Optional<CarAd> exists = carAdDao.findById(carAd.getId());
        if (exists.isEmpty()) {
            throw new NotFoundException("CarAd with id = " + id + " not found.");
        }
        //carOwner.getCarAdCollection().add(carAd);
        //carOwnerDao.saveAndFlush(carOwner);
        //carAd = carOwner.getCarAdCollection().get(carOwner.getCarAdCollection().size() - 1);
        carAdDao.saveAndFlush(carAd);
        return carAdMapper.dtoFromCarAd(carAd);

    }

}