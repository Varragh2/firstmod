package com.darragh2.firstmod.common.block;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class RockBlock  extends Block {

    private static final Property<Direction.Axis> AXIS = BlockStateProperties.AXIS;
    public static final String MESSAGE_ROCK = "message.rock";

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

    /**
     * @param p_49820_ can be queried in the method for information about the blockPlacement
     * @return needs to return a BlockState, get a block state with stateDefinition.any().setValue(Property, Value);
     */
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext p_49820_) {
        BlockState blockState = this.stateDefinition.any();
        if (p_49820_.getNearestLookingDirection().getAxis() == Direction.Axis.Y) {
            blockState = this.stateDefinition.any().setValue(AXIS, Direction.Axis.Y);
        }
        else if (p_49820_.getNearestLookingDirection().getAxis() == Direction.Axis.X) {
            blockState = this.stateDefinition.any().setValue(AXIS, Direction.Axis.X);
        }
        else if (p_49820_.getNearestLookingDirection().getAxis() == Direction.Axis.Z) {
            blockState = this.stateDefinition.any().setValue(AXIS, Direction.Axis.Z);
        }
        return blockState;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter reader, List<Component> list, TooltipFlag flags) {
        list.add(new TranslatableComponent(MESSAGE_ROCK).withStyle(ChatFormatting.AQUA));
    }

    /**
     * @param p_60503_ BlockState
     * @param p_60504_ Level
     * @param p_60505_ BlockPos
     * @param p_60506_ Player
     * @param p_60507_ InteractionHand
     * @param p_60508_ BlockHitResult
     * @deprecated
     */
    @Override
    public InteractionResult use(BlockState p_60503_, Level p_60504_, BlockPos p_60505_, Player p_60506_, InteractionHand p_60507_, BlockHitResult p_60508_) {
        if (p_60504_.isClientSide()) {
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

//    @Override
//    public void playerWillDestroy(Level p_49852_, BlockPos p_49853_, BlockState p_49854_, Player p_49855_) {
//        super.drops
//    }
}
