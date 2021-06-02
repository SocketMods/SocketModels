package dev.socketmods.socketmodels.model.bolb;

import dev.socketmods.socketmodels.api.date.Holidays;
import dev.socketmods.socketmodels.api.model.ModelDefinition;
import dev.socketmods.socketmodels.api.rewards.KnownPermissions;
import dev.socketmods.socketmodels.api.rewards.Person;
import dev.socketmods.socketmodels.api.rewards.RewardTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.fml.loading.FMLEnvironment;

public class BolbDefinition extends ModelDefinition {

    private static final boolean isPublicHoliday = Holidays.isAprilFools() || Holidays.isChristmas();

    @Override
    public boolean hasPermission(PlayerEntity player) {
        if (!FMLEnvironment.production || isPublicHoliday) return true;

        Person person = RewardTracker.getPersonOrNull(player);
        return person != null && (person.isContributor() || RewardTracker.hasPermission(person, KnownPermissions.BOLB));
    }

}
