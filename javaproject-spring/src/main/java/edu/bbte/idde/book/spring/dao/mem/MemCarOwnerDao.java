package edu.bbte.idde.book.spring.dao.mem;

import edu.bbte.idde.book.spring.dao.CarOwnerDao;
import edu.bbte.idde.book.spring.model.CarOwner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Profile("mem")
public final class MemCarOwnerDao implements CarOwnerDao {
    private final ConcurrentHashMap<Long, CarOwner> carOwners;
    private final AtomicLong id;

    public MemCarOwnerDao() {
        carOwners = new ConcurrentHashMap<>();
        id = new AtomicLong(0L);
    }

    /*@Override
    public void create(CarAd carAd) {
        Long id = this.id.incrementAndGet();
        carAd.setId(id);
        this.carAds.put(id, carAd);
    }*/

    @Override
    public CarOwner saveAndFlush(CarOwner entity) {
        if (entity.getId() == null) {
            //implement here
            //return null;
            Long id = this.id.incrementAndGet();
            entity.setId(id);
            this.carOwners.put(id, entity);
        } else {
            update(entity.getId(), entity);
        }
        return null;
    }

    @Override
    public Optional<CarOwner> findById(Long id) {
        return Optional.ofNullable(this.carOwners.get(id));
    }

    @Override
    public Collection<CarOwner> findAll() {
        return this.carOwners.values();
    }

    @Override
    public void delete(CarOwner entity) {
        Long id = entity.getId();
        this.carOwners.remove(id);
    }

    @Override
    public void deleteById(Long id) {
        this.carOwners.remove(id);
    }

    public void update(Long id, CarOwner carOwner) {
        this.carOwners.put(id, carOwner);
    }

}
