package edu.bbte.idde.book.backend.dao.memory;

import edu.bbte.idde.book.backend.dao.CarOwnerDao;
import edu.bbte.idde.book.backend.model.CarOwner;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MemCarOwnerDao implements CarOwnerDao {
    private final ConcurrentHashMap<Long, CarOwner> carOwners;
    private final AtomicLong id;

    public MemCarOwnerDao() {
        carOwners = new ConcurrentHashMap<>();
        id = new AtomicLong(0L);
    }

    @Override
    public void create(CarOwner carOwner) {
        Long id = this.id.incrementAndGet();
        carOwner.setId(id);
        this.carOwners.put(id, carOwner);
    }

    @Override
    public CarOwner findById(Long id) {
        return this.carOwners.get(id);
    }

    @Override
    public Collection<CarOwner> findAll() {
        return this.carOwners.values();
    }

    @Override
    public void delete(Long id) {
        this.carOwners.remove(id);//idk if its correct
    }

    @Override
    public void update(Long id, CarOwner carad) {
        this.carOwners.put(id, carad);
    }

    @Override
    public Collection<CarOwner> findByName(String name) {
        ConcurrentHashMap<Long, CarOwner> carOwnersByName = new ConcurrentHashMap<>();
        for (CarOwner carOwner : carOwners.values()) {
            if (name.equals(carOwner.getName())) {
                carOwnersByName.put(carOwner.getId(), carOwner);
            }
        }
        return carOwnersByName.values();

    }
}
