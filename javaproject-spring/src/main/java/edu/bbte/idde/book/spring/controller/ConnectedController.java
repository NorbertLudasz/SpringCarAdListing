package edu.bbte.idde.book.spring.controller;

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
@RequestMapping("/carOwners")
public class ConnectedController {
    private static final Logger LOG = LoggerFactory.getLogger(ConnectedController.class);
    @Autowired
    private CarAdMapper carAdMapper;
    @Autowired
    private CarOwnerDao carOwnerDao;

    @GetMapping("/{id}/carAds")
    @ResponseBody
    public Collection<CarAdOutDto> findByOwner(@PathVariable("id") Long id) {
        Optional<CarOwner> optional = carOwnerDao.findById(id);
        if (optional.isEmpty()) {
            throw new NotFoundException("CarOwner with id = " + id + " not found.");
        }

        CarOwner carOwner = optional.get();
        LOG.info("Get carAds of carOwner with id = {}", id);
        return carAdMapper.dtosFromCarAds(carOwner.getCarAdCollection());
    }

    @DeleteMapping("/{id}/carAds") // A törlés egy konkrét elemre -> CarAdController delete-je
    public void delete(@PathVariable("id") Long id) {
        Optional<CarOwner> optional = carOwnerDao.findById(id);
        if (optional.isEmpty()) {
            throw new NotFoundException("nincs megadott id-ju");
        }
        CarOwner carOwner = optional.get();
        carOwner.getCarAdCollection().clear();
        carOwnerDao.saveAndFlush(carOwner);
        LOG.info("Deleted carads of carowner with id = {}", id);
    }

    @PostMapping("/{id}/carAd")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public CarAdOutDto create(@RequestBody @Valid CarAdInDto carAdDto, @PathVariable("id") Long id) {
        Optional<CarOwner> optional = carOwnerDao.findById(id);
        if (optional.isEmpty()) {
            throw new NotFoundException("CarOwner with id = " + id + " not found.");
        }

        CarOwner carOwner = optional.get();

        CarAd carAd = carAdMapper.carAdFromDto(carAdDto);
        carAd.setCarOwner(carOwner);
        carOwner.getCarAdCollection().add(carAd);
        carOwnerDao.saveAndFlush(carOwner);
        carAd = carOwner.getCarAdCollection().get(carOwner.getCarAdCollection().size() - 1);
        return carAdMapper.dtoFromCarAd(carAd);
    }
}
