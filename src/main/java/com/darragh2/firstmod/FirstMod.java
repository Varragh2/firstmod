
package com.darragh2.firstmod;


import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LogEvent;

import java.util.stream.Collectors;

/**
 * 3 steps for deferredRegister:
 * 1. create a private deferredRegistry
 * 2. create public static final RegistryObjects of the same type to add to the registry
 * 3. In constructor set the registry as a listener of the fmlEventBus
 */



// The value here should match an entry in the META-INF/mods.toml file
@Mod("firstmod")
public class FirstMod
{
    //makes it easier to change the mod id
    private static final String MODID = "firstmod";
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final RegistryObject<Block> ROCK_BLOCK = BLOCKS.register("rock", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD).strength(0.5f).color(MaterialColor.COLOR_BLUE).dropsLike(Blocks.STONE)));

    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    //public static final RegistryObject<Item> ROCK_ITEM = ITEMS.register("rock", () -> new Item(new Item.Properties().setNoRepair().durability(2)));
    public static final RegistryObject<BlockItem> ROCK_BLOCKITEM = ITEMS.register("rock", () -> new BlockItem(ROCK_BLOCK.get(), new Item.Properties().stacksTo(64).tab(CreativeModeTab.TAB_MISC)));



    public FirstMod() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        //Register blocks
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
        LOGGER.info(new Block(BlockBehaviour.Properties.of(Material.STONE)));
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("firstmod", "helloworld", () -> {
            LOGGER.info("Hello world from the MDK");
            return "Hello world";
        });
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().map(m->m.messageSupplier().get()).collect(Collectors.toList()));
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
        ROCK_BLOCK.get();
    }


    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {
            // register a new block here
            LOGGER.info("HELLO from Register Block");
            LOGGER.info("This is an info test!");

            LOGGER.info("ROCK_BLOCK registry name: {}", ROCK_BLOCK.get().getRegistryName());

            //pls work
            BlockItem rock_blockItem = new BlockItem(ROCK_BLOCK.get(), new Item.Properties().setNoRepair().durability(2));

            rock_blockItem.setRegistryName(MODID, "rock");

            LOGGER.info("Testing rock_blockItem: " + ROCK_BLOCK.get().asItem());

            //event.getRegistry().register(new Block(BlockBehaviour.Properties.of(Material.STONE)));
            //LOGGER.info("Block registry: " + event.getRegistry().toString());
        }

        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent<Item> event) {
            //LOGGER.info("ROCK_ITEM registry name: {}", ROCK_ITEM.get().getRegistryName());
            LOGGER.info("ROCK_BLOCKITEM registry name: {}", ROCK_BLOCKITEM.get().getRegistryName());
        }
    }
}
