package com.darragh2.firstmod.common.block;

import com.darragh2.firstmod.common.containers.ChestContainer;
import com.darragh2.firstmod.setup.Registration;
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
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class ChestBlock extends Block implements EntityBlock {

    public static final String CHEST_SCREEN = "screen.chest";
    public static final String CHEST_MESSAGE = "message.chest";


    public ChestBlock() {
        super(Properties.of(Material.WOOD)
                .strength(1f)
                .sound(SoundType.WOOD)
                //.requiresCorrectToolForDrops()
        );
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter reader, List<Component> list, TooltipFlag flags) {
        list.add(new TranslatableComponent(CHEST_MESSAGE).withStyle(ChatFormatting.BOLD));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new com.darragh2.firstmod.common.block.entity.ChestBlockEntity(pos, state);
    }


    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult trace) {
        if(!level.isClientSide) {
            BlockEntity be = level.getBlockEntity(blockPos);
            if (be instanceof com.darragh2.firstmod.common.block.entity.ChestBlockEntity) {
                MenuProvider container = new MenuProvider() {
                    @Override
                    public Component getDisplayName() {
                        return new TranslatableComponent(CHEST_SCREEN);
                    }

                    @Nullable
                    @Override
                    public AbstractContainerMenu createMenu(int windowId, Inventory inv, Player playerEntity) {
                        return new ChestContainer(Registration.CHEST_CONTAINER.get(), windowId, inv, inv, inv.getContainerSize());
                    }
                };
                NetworkHooks.openGui((ServerPlayer) player, container, be.getBlockPos());
            } else {
                throw new IllegalStateException("Our named container provider is missing!");
            }
        }
        return InteractionResult.SUCCESS;
    }
}
