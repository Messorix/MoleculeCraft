package com.anime.basic;

import com.anime.basic.network.GuiHandler;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class MainModReference {
	
	/**
	 * Mod Identiifier String
	 */
	public static final String MODID = "testmod";
	/**
	 * Mod In Game Name
	 */
	public static final String NAME = "Test Mod";
	/**
	 * Version of the Mod
	 **/
	public static final String VERSION = "1.0";
	/**
	 * What versions of Minecraft this Mod will load on.
	 */
	public static final String APPLICABLE_MINECRAFT_VERSIONS = "[1.10],[1.10.2]";
	/**
	 * The Mods Dependencies(if it has any).
	 */
	public static final String MOD_DEPENDENCIES = "";
	
	
	/**
	 * Server Proxy Path.
	 */
	public static final String SERVER_PROXY_PATH = "com.anime.test.proxy.ServerProxy";
	/**
	 * Client Proxy Path
	 */
	public static final String CLIENT_PROXY_PATH = "com.anime.test.proxy.ClientProxy";
	
	/**
	 * A GuiHandler instance, register to handle GUIs.
	 */
	public static final GuiHandler GUIHANDLER = new GuiHandler();
	
	/**
	 * A SimpleNetworkWrapper instance, register to use packets.
	 */
	public static final SimpleNetworkWrapper WRAPPER = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
	
	/**
	 * Whether or not it should log when items are registered.
	 */
	public static final boolean SHOULD_LOG_ITEM_REGISTRY = true;
	
	/**
	 * Whether or not it should log when Blocks are registered.
	 */
	public static final boolean SHOULD_LOG_BLOCK_REGISTRY = true;
	
	/**
	 * Whether or not it should log when things are registered to the OreDictionary.
	 */
	public static final boolean SHOULD_LOG_OREDICT_REGISTRY = true;
	
}
