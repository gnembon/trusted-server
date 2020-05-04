package trusted_server.mixins;

import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import trusted_server.Settings;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandler_antiCheatDisabledMixin
{
    @Shadow private int floatingTicks;

    @Shadow private int vehicleFloatingTicks;

    @Shadow protected abstract boolean isServerOwner();

    @Inject(method = "tick", at = @At("HEAD"))
    private void restrictFloatingBits(CallbackInfo ci)
    {
        if (Settings.isTrusted)
        {
            if (floatingTicks > 70) floatingTicks--;
            if (vehicleFloatingTicks > 70) vehicleFloatingTicks--;
        }
    }

    @Redirect(method = "onVehicleMove", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/network/ServerPlayNetworkHandler;isServerOwner()Z"
    ))
    private boolean isServerTrusting(ServerPlayNetworkHandler serverPlayNetworkHandler)
    {
        return isServerOwner() || Settings.isTrusted;
    }

    @Redirect(method = "onPlayerMove", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/network/ServerPlayerEntity;isInTeleportationState()Z"))
    private boolean relaxMoveRestrictions(ServerPlayerEntity serverPlayerEntity)
    {
        return Settings.isTrusted || serverPlayerEntity.isInTeleportationState();
    }

}
