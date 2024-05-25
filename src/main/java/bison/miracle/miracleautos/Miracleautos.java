package bison.miracle.miracleautos;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

@Environment(EnvType.CLIENT)
public class Miracleautos implements ClientModInitializer {

    private final Random random = new Random();

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.options.attackKey.isPressed() && client.player != null) {
                if (client.crosshairTarget != null && client.crosshairTarget.getType() == HitResult.Type.ENTITY) {
                    Entity entity = ((EntityHitResult) client.crosshairTarget).getEntity();
                    if (entity.isAlive() && entity.isAttackable() && client.player.getAttackCooldownProgress(0) >= 1.0f) {
                        int delay = 75 + random.nextInt(76);
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                if (client.player != null && client.crosshairTarget != null && client.crosshairTarget.getType() == HitResult.Type.ENTITY) {
                                    Entity delayedEntity = ((EntityHitResult) client.crosshairTarget).getEntity();
                                    if (delayedEntity == entity && delayedEntity.isAlive() && delayedEntity.isAttackable()) {
                                        client.interactionManager.attackEntity(client.player, entity);
                                        client.player.swingHand(Hand.MAIN_HAND);
                                    }
                                }
                            }
                        }, delay);
                    }
                }
            }
        });
    }
}
