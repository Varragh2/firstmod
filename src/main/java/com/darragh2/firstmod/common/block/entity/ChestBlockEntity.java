package com.darragh2.firstmod.common.block.entity;

import com.darragh2.firstmod.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ChestBlockEntity extends BlockEntity {

    public ChestBlockEntity(BlockPos pos, BlockState state) {
        super(Registration.CHEST_BLOCKENTITY.get(), pos, state);
    }
}
