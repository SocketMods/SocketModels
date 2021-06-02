package dev.socketmods.socketmodels.api.model;

import net.minecraft.entity.player.PlayerEntity;

public abstract class ModelDefinition {

    public abstract boolean hasPermission(PlayerEntity entity);

    public boolean hasEnabled(PlayerEntity entity) {
        return hasPermission(entity); //TODO: && player.isModelEnabled();
    }

}
