package xbony2.aepm;

import com.google.common.eventbus.EventBus;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;

import java.util.Arrays;

public class AEPMContainer extends DummyModContainer {
	public AEPMContainer(){
		super(new ModMetadata());
		ModMetadata meta = getMetadata();
		meta.modId = "aepm";
		meta.name = "AEPM";
		meta.description = "Disables the End Portal spawning in when the dragon is killed.";
		meta.version = "1.7.10-@VERSION@";
		meta.authorList = Arrays.asList("xbony2");
	}
	
	@Override
	public boolean registerBus(EventBus bus, LoadController controller){
		bus.register(this);
		return true;
	}
}
