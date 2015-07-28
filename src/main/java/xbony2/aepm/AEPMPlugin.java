package xbony2.aepm;

import cpw.mods.fml.relauncher.IFMLCallHook;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

import java.util.Map;

@MCVersion("1.7.10")
@TransformerExclusions("xbony2.aepm")
public class AEPMPlugin implements IFMLLoadingPlugin {

	@Override
	public String[] getASMTransformerClass(){
		return new String[]{"xbony2.aepm.AEPMClassTransformer"};
	}

	@Override
	public String getModContainerClass(){
		return null;
	}

	@Override
	public String getSetupClass(){
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data){}

	@Override
	public String getAccessTransformerClass(){
		return null;
	}
}
