package com.github.tartaricacid.touhoulittlemaid.api.util;

import com.github.tartaricacid.touhoulittlemaid.api.AbstractEntityMaid;
import com.github.tartaricacid.touhoulittlemaid.api.IMaidTask;
import com.github.tartaricacid.touhoulittlemaid.init.MaidSoundEvent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.Random;

/**
 * @author Snownee
 * @date 2019/7/24 02:31
 */
public final class Util {
    private Util() {
    }

    /**
     * 用来播放基于环境的音效，比如气温，天气，时间
     *
     * @param fallback    这些都没有播放情况下的默认音效
     * @param probability 播放环境音效的概率
     * @return 应当触发的音效
     */
    public static SoundEvent environmentSound(AbstractEntityMaid maid, SoundEvent fallback, float probability) {
        World world = maid.world;
        Random rand = maid.getRNG();
        // 差不多早上 6:30 - 7:30
        if (500 < world.getWorldTime() && world.getWorldTime() < 1500 && rand.nextFloat() < probability) {
            return MaidSoundEvent.MAID_MORNING;
        }
        // 差不多下午 6:30 - 7:30
        if (12500 < world.getWorldTime() && world.getWorldTime() < 13500 && rand.nextFloat() < probability) {
            return MaidSoundEvent.MAID_NIGHT;
        }
        Biome biome = world.getBiome(maid.getPosition());
        if (world.isRaining() && biome.canRain() && rand.nextFloat() < probability) {
            return MaidSoundEvent.MAID_RAIN;
        }
        if (world.isRaining() && biome.isSnowyBiome() && rand.nextFloat() < probability) {
            return MaidSoundEvent.MAID_SNOW;
        }
        if (biome.getTempCategory() == Biome.TempCategory.COLD && rand.nextFloat() < probability) {
            return MaidSoundEvent.MAID_COLD;
        }
        if (biome.getTempCategory() == Biome.TempCategory.WARM && rand.nextFloat() < probability) {
            return MaidSoundEvent.MAID_HOT;
        }
        return fallback;
    }

    /**
     * 通过枚举类型 IMaidTask 构建对应的本地化词条
     */
    public static String getTaskTranslationKey(IMaidTask task) {
        ResourceLocation rl = task.getUid();
        return "task." + rl.getNamespace() + "." + rl.getPath();
    }
}