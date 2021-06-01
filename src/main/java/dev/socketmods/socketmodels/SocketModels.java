package dev.socketmods.socketmodels;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.fml.common.Mod;

@Mod(SocketModels.MODID)
public class SocketModels {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "socketmodels";

    public SocketModels() {
        LOGGER.info("Hello World");
    }

}
