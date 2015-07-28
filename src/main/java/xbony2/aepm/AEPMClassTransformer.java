package xbony2.aepm;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

public class AEPMClassTransformer implements IClassTransformer {
	public static final String TRANSFORMED_CLASS = "net.minecraft.entity.boss.EntityDragon";
	public static final String TRANSFORMED_METHOD_DEOBFUSCATED  = "createEnderPortal";
	public static final String TRANSFORMED_METHOD_OBFUSCATED = "b";
	public static final String TRANSFORMED_DESC_OBFUSCATED = "";
	public static final String TRANSFORMED_DESC_DEOBFUSCATED = "";
	
	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass){
		boolean isObfuscated = !name.equals(transformedName);
		return TRANSFORMED_CLASS != transformedName ? transform(basicClass, isObfuscated) : basicClass;
	}
	
	public byte[] transform(byte[] classBeingTransformed, boolean isObfuscated){
		try {
			ClassNode node = new ClassNode();
			ClassReader reader = new ClassReader(classBeingTransformed);
			reader.accept(node, 0);
			
			//Trasformeration here
			final String CREATE_ENDER_PORTAL = isObfuscated ? TRANSFORMED_METHOD_OBFUSCATED : TRANSFORMED_METHOD_DEOBFUSCATED;
			final String CREATE_ENDER_PORTAL_DESC = "(II)V"; //Pretty sure it's the same, I dunno these things.
			
			for(MethodNode method : node.methods){
				if(method.name.equals(CREATE_ENDER_PORTAL) && method.desc.equals(CREATE_ENDER_PORTAL_DESC)){
					System.out.println("Transforming!"); //DO NOT USE FMLLog! That's not loaded yet, mkay?
					AbstractInsnNode targetNode = null;
					for(AbstractInsnNode instruction : method.instructions.toArray()){
						/*if(instruction.getOpcode() == Opcodes.ALOAD){
							if(((VarInsnNode) instruction).var == 5 && instruction.getNext().getOpcode() == Opcodes.GETSTATIC){
								targetNode = instruction;
								break;
							}
						}*/
						method.instructions.remove(instruction);// Removes ALL OF THE INSTRUCTIONS
					}
					
					/*if(targetNode != null){
						
					}else{
						FMLLog.info("Something broke, but who cares? Everyone? Sorry then.");
					}*/
				}
			}
			
			ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
			node.accept(writer);
			return writer.toByteArray();
		}catch(Exception e){
			e.printStackTrace();
		}
		return classBeingTransformed;
	}
}
