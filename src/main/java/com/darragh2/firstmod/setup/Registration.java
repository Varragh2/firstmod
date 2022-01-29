package com.darragh2.firstmod.setup;

import com.darragh2.firstmod.FirstMod;
import com.darragh2.firstmod.common.block.PowergenBlock;
import com.darragh2.firstmod.common.block.RockBlock;
import com.darragh2.firstmod.common.block.entity.ChestBlockEntity;
import com.darragh2.firstmod.common.containers.ChestContainer;
import com.darragh2.firstmod.common.containers.PowergenContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class Registration {

    /**
     * 3 steps for deferredRegister:
     * 1. create a private deferredRegistry
     * 2. create public static final RegistryObjects of the same type to add to the registry
     * 3. In constructor set the registry as a listener of the fmlEventBus
     */

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, FirstMod.MODID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FirstMod.MODID);
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, FirstMod.MODID);
    private static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, FirstMod.MODID);

    public static void init () {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(bus);
        ITEMS.register(bus);
        BLOCK_ENTITIES.register(bus);
        CONTAINERS.register(bus);
    }

    public static final BlockBehaviour.Properties BLOCK_PROPERTIES = BlockBehaviour.Properties.of(Material.STONE).strength(0.5f).sound(SoundType.STONE);
    public static final Item.Properties ITEM_PROPERTIES = new Item.Properties().tab(ModSetup.ITEM_GROUP);

    public static final RegistryObject<Block> ROCK = BLOCKS.register("rock", () -> new RockBlock(BLOCK_PROPERTIES));
    public static final RegistryObject<Item> ROCK_ITEM = fromBlock(ROCK);

    public static final RegistryObject<PowergenBlock> POWERGEN = BLOCKS.register("powergen", PowergenBlock::new);
    public static final RegistryObject<Item> POWERGEN_ITEM = fromBlock(POWERGEN);
    public static final RegistryObject<BlockEntityType<ChestBlockEntity>> POWERGEN_BLOCKENTITY = BLOCK_ENTITIES.register("powergen", () -> BlockEntityType.Builder.of(ChestBlockEntity::new, POWERGEN.get()).build(null));
    public static final RegistryObject<MenuType<PowergenContainer>> POWERGEN_CONTAINER = CONTAINERS.register("powergen",
            () -> IForgeMenuType.create(((windowId, inv, data) -> new PowergenContainer(windowId, data.readBlockPos(), inv, inv.player))));

    public static final RegistryObject<com.darragh2.firstmod.common.block.ChestBlock> CHEST = BLOCKS.register("chest", com.darragh2.firstmod.common.block.ChestBlock::new);
    public static final RegistryObject<Item> CHEST_ITEM = fromBlock(CHEST);
    public static final RegistryObject<BlockEntityType<ChestBlockEntity>> CHEST_BLOCKENTITY = BLOCK_ENTITIES.register("chest", () -> BlockEntityType.Builder.of(ChestBlockEntity::new, CHEST.get()).build(null));
    public static final RegistryObject<MenuType<ChestContainer>> CHEST_CONTAINER = CONTAINERS.register("chest",
            () -> IForgeMenuType.create(((windowId, inv, data) -> new ChestContainer(Registration.CHEST_CONTAINER.get(), windowId, inv, inv, inv.getContainerSize()))));


    public static <B extends Block> RegistryObject<Item> fromBlock(RegistryObject<B> block) {
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), ITEM_PROPERTIES));
    }
}
