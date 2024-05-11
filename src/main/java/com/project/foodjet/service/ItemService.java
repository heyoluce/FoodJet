package com.project.foodjet.service;

import com.project.foodjet.entity.Item;
import com.project.foodjet.entity.Item;
import com.project.foodjet.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService implements CrudListener<Item> {

    private final ItemRepository ItemRepository;

    @Override
    public List<Item> findAll() {
        return ItemRepository.findAll();
    }

    @Override
    public Item add(Item Item) {
        return ItemRepository.save(Item);
    }

    @Override
    public Item update(Item Item) {
        return ItemRepository.save(Item);
    }

    @Override
    public void delete(Item Item) {
        ItemRepository.delete(Item);
    }
}
