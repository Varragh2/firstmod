package com.darragh2.firstmod.common.block;

import com.darragh2.firstmod.common.block.entity.PowergenBlockEntity;
import com.darragh2.firstmod.common.containers.PowergenContainer;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;

//Blocks that use BlockEntities need to implement EntityBlock
public class PowergenBlock extends Block implements EntityBlock {

    public static final String MESSAGE_POWERGEN = "message.powergen";
    public static final String SCREEN_TUTORIAL_POWERGEN = "screen.tutorial.powergen";

    //Only necessary to change the rendering of the box
    private static final VoxelShape RENDER_SHAPE = Shapes.box(0.1, 0.1, 0.1, 0.9, 0.9, 0.9);

    public PowergenBlock() {
        //needs no parameters because it controls the BlockProperties that are used
        super(Properties.of(Material.METAL)
                //.sound() sets the sound
                .sound(SoundType.METAL)
                //0.5f is strength of dirt
                .strength(2.0f)
                .lightLevel(state -> state.getValue(BlockStateProperties.POWERED) ? 14 : 0)
                .requiresCorrectToolForDrops()
        );
    }

    //It is ok to override deprecated methods
    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter reader, BlockPos pos) {
        return RENDER_SHAPE;
    }


    /**
     * Adds a tooltip to the block and sets the text to blue
     * @param stack ItemStack
     * @param reader nullable BlockGetter
     * @param list List<Component>
     * @param flags TooltipFlag
     */
    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter reader, List<Component> list, TooltipFlag flags) {
        list.add(new TranslatableComponent(MESSAGE_POWERGEN, Integer.toString(PowergenBlockEntity.POWERGEN_GENERATE))
                .withStyle(ChatFormatting.BLUE));
    }

    /**
     * provides a constructor for PowergenBlockEntity
     * @param blockPos BlockPosition
     * @param blockState BlockState
     * @return a new BlockEntity, with the given pos and state
     */
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new PowergenBlockEntity(blockPos, blockState);
    }

    /**
     * A ticker for the BlockEntity. Needed to handle events every tick
     * @param level Level
     * @param state BlockState
     * @param type BlockEntityType
     * @param <T> T extends BlockEntity
     * @return A BlockEntityTicker
     */
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide()) {
            return null;
        }
        return (lvl, pos, blockState, t) -> {
            if (t instanceof PowergenBlockEntity tile) {
                tile.tickServer();
            }
        };
    }

    /**
     * If you want to add your own BlockStates need to override this method and add BlockStates to builder
     * @param builder StateDefinition.Builder<Block, BlockState>
     */
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BlockStateProperties.POWERED);
    }

    /**
     * Called right before the block is placed
     * @param context the context of the placed Block
     * @return the BlockState of the now placed Block
     */
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(BlockStateProperties.POWERED, false);
    }

    /**
     * Deprecated method is overriden. Override use() to implement what happens when a Player right clicks(uses) the block
     * @param state BlockState of the clicked Block
     * @param level Level
     * @param pos Position of the clicked Block
     * @param player Player that clicked
     * @param hand Hand that the player clicked with (left/right)
     * @param trace A BlockHitResult
     * @return a successful InteractionResult
     */
    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult trace) {
        if (!level.isClientSide) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof PowergenBlockEntity) {
                MenuProvider containerProvider = new MenuProvider() {
                    //creates a object then overrides methods of the parent class
                    @Override
                    public Component getDisplayName() {
                        return new TranslatableComponent(SCREEN_TUTORIAL_POWERGEN);
                    }

                    @Override
                    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player playerEntity) {
                        return new PowergenContainer(windowId, pos, playerInventory, playerEntity);
                    }
                };
                //Uses NetworkHooks to send packages from the server to the client
                //Opens a gui
                NetworkHooks.openGui((ServerPlayer) player, containerProvider, be.getBlockPos());
            } else {
                throw new IllegalStateException("Our named container provider is missing!");
            }
        }
        return InteractionResult.SUCCESS;
    }
}