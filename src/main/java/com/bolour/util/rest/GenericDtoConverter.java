/*
 * Copyright 2017 Azad Bolour
 * Licensed under MIT (https://github.com/azadbolour/util/blob/master/LICENSE)
 */

package com.bolour.util.rest;

public class GenericDtoConverter<Entity, Dto> {

    protected final Class<?> entityClass;
    protected final Class<?> dtoClass;

    protected GenericDtoConverter(Class<?> entityClass, Class<?> dtoClass) {
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
    }

    public Dto dto(Entity entity) {
        Object obj = JsonUtil.copy(entity, dtoClass);
        Dto dto = (Dto) obj;
        return dto;
    }

    public Entity entity(Dto dto) {
        Object obj = JsonUtil.copy(dto, entityClass);
        Entity entity = (Entity) obj;
        return entity;
    }
}
