package edu.bbte.idde.book.spring.dao.mem;

import edu.bbte.idde.book.spring.dao.CarAdDao;
import edu.bbte.idde.book.spring.model.CarAd;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Profile("mem")
public final class MemCarAdDao implements CarAdDao {
    private final ConcurrentHashMap<Long, CarAd> carAds;
    private final AtomicLong id;

    public MemCarAdDao() {
        carAds = new ConcurrentHashMap<>();
        id = new AtomicLong(0L);
    }

    /*@Override
    public void create(CarAd carAd) {
        Long id = this.id.incrementAndGet();
        carAd.setId(id);
        this.carAds.put(id, carAd);
    }*/

    @Override
    public CarAd saveAndFlush(CarAd entity) {
        if (entity.getId() == null) {
            //implement here
            //return null;
            Long id = this.id.incrementAndGet();
            entity.setId(id);
            this.carAds.put(id, entity);
        } else {
            update(entity.getId(), entity);//csak update(entity) eriknel 533
        }
        return null;
    }

    @Override
    public Optional<CarAd> findById(Long id) {
        return Optional.ofNullable(this.carAds.get(id));
    }

    @Override
    public Collection<CarAd> findAll() {
        return this.carAds.values();
    }

    @Override
    public void delete(CarAd entity) {
        Long id = entity.getId();
        this.carAds.remove(id);
    }

    @Override
    public void deleteById(Long id) {
        this.carAds.remove(id);
    }

    //@Override
    public void update(Long id, CarAd carad) {
        this.carAds.put(id, carad);
    }

    @Override
    public Collection<CarAd> findByYearOfMake(Integer yearOfMake) {
        ConcurrentHashMap<Long, CarAd> carAdsByYear = new ConcurrentHashMap<>();
        for (CarAd carAd : carAds.values()) {
            if (yearOfMake.equals(carAd.getYearOfMake())) {
                carAdsByYear.put(carAd.getId(), carAd);
            }
        }
        return carAdsByYear.values();
    }

}

