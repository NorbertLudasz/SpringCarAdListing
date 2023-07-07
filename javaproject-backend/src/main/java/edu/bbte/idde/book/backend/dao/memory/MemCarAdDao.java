package edu.bbte.idde.book.backend.dao.memory;

import edu.bbte.idde.book.backend.dao.CarAdDao;
import edu.bbte.idde.book.backend.model.CarAd;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public final class MemCarAdDao implements CarAdDao {
    private final ConcurrentHashMap<Long, CarAd> carAds;
    private final AtomicLong id;

    public MemCarAdDao() {
        carAds = new ConcurrentHashMap<>();
        id = new AtomicLong(0L);
    }

    @Override
    public void create(CarAd carAd) {
        Long id = this.id.incrementAndGet();
        carAd.setId(id);
        this.carAds.put(id, carAd);
    }

    @Override
    public CarAd findById(Long id) {
        return this.carAds.get(id);
    }

    @Override
    public Collection<CarAd> findAll() {
        return this.carAds.values();
    }

    @Override
    public void delete(Long id) {
        this.carAds.remove(id);//idk if its correct
    }

    @Override
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

    @Override
    public Integer getLoggedUpdatesDb() {
        return null;
    }

    @Override
    public Integer getLoggedQueriesDb() {
        return null;
    }
}
