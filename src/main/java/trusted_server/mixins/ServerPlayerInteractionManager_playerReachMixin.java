package trusted_server.mixins;

import net.minecraft.server.network.ServerPlayerInteractionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import trusted_server.Settings;

@Mixin(ServerPlayerInteractionManager.class)
public class ServerPlayerInteractionManager_playerReachMixin
{

    @ModifyConstant(method = "processBlockBreakingAction",
            constant = @Constant(doubleValue = 36D))
    private double addDistance(double original) {
        if (Settings.isTrusted)
            return 1024D; // blocks 32 distance
        return original;
    }
}