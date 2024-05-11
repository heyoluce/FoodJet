package com.project.foodjet.service;

import com.project.foodjet.entity.Courier;
import com.project.foodjet.repository.CourierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourierService implements CrudListener<Courier> {

    private final CourierRepository courierRepository;

    public Courier getByEmail(String email) {
        return courierRepository.getByEmail(email);
    }

    @Override
    public List<Courier> findAll() {
        return courierRepository.findAll();
    }

    @Override
    public Courier add(Courier courier) {
        return courierRepository.save(courier);
    }

    @Override
    public Courier update(Courier courier) {
        return courierRepository.save(courier);
    }

    @Override
    public void delete(Courier courier) {
        courierRepository.delete(courier);
    }
}
