package com.pet.adoption.events;

import com.pet.adoption.po.Animal;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Getter
public class DataLoaderEvent extends ApplicationEvent {
    private final List<Animal> animalList;

    public DataLoaderEvent(Object source, List<Animal> animalList) {
        super(source);
        this.animalList = animalList;
    }
}
