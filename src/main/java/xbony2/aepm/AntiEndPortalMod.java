package xbony2.aepm;

import net.minecraft.item.ItemStack;

import net.minecraft.item.Item;
import net.minecraft.init.Blocks;
import net.minecraft.entity.boss.EntityDragon;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

@Mod(modid = "aepm", name = "AEPM", version = "@VERSION@")
public class AntiEndPortalMod {
	
	@EventHandler
	public void load(FMLInitializationEvent event){
		MinecraftForge.EVENT_BUS.register(new EggDropEvent());
	}
	
	public class EggDropEvent {
		@SubscribeEvent
		public void onDeath(LivingDeathEvent event){
			if(event.entity instanceof EntityDragon){
				event.entity.entityDropItem(new ItemStack(Blocks.dragon_egg), 0.00F); //TODO: not sure what this int does...
			}
		}
	}
}
