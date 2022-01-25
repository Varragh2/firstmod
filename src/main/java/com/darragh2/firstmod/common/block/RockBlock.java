package com.darragh2.firstmod.common.block;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.Nullable;

public class RockBlock  extends Block {

    private static final Property<Direction.Axis> AXIS = BlockStateProperties.AXIS;

    public RockBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(AXIS, Direction.Axis.Y)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        p_49915_.add(AXIS);
        super.createBlockStateDefinition(p_49915_);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext p_49820_) {
        if (p_49820_.getNearestLookingDirection().getAxis() == Direction.Axis.Y) {
            return this.stateDefinition.any().setValue(AXIS, Direction.Axis.Y);
        }
        else if (p_49820_.getNearestLookingDirection().getAxis() == Direction.Axis.X) {
            return this.stateDefinition.any().setValue(AXIS, Direction.Axis.X);
        }
        else if (p_49820_.getNearestLookingDirection().getAxis() == Direction.Axis.Z) {
            return this.stateDefinition.any().setValue(AXIS, Direction.Axis.Z);
        }
        return super.getStateForPlacement(p_49820_);
    }
}
