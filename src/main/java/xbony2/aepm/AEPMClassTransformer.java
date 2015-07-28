package xbony2.aepm;

import org.objectweb.asm.Opcodes;

import org.objectweb.asm.tree.VarInsnNode;
import org.objectweb.asm.tree.InsnList;
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
	
	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass){
		boolean isObfuscated = !name.equals(transformedName);
		return name.equals(TRANSFORMED_CLASS) ? transform(basicClass, isObfuscated) : basicClass;	
	}
	
	/**
	 * Small note: FMLLog.log() (and similar methods) should not be used in this method, since it isn't loaded. No shame in using the old built-in solution of System.out.println() ;)
	 * 
	 * @param classBeingTransformed
	 * @param isObfuscated
	 * @return
	 */
	public byte[] transform(byte[] classBeingTransformed, boolean isObfuscated){
		try {
			ClassNode node = new ClassNode();
			ClassReader reader = new ClassReader(classBeingTransformed);
			reader.accept(node, 0);
			
			//Trasformeration here
			final String CREATE_ENDER_PORTAL = isObfuscated ? TRANSFORMED_METHOD_OBFUSCATED : TRANSFORMED_METHOD_DEOBFUSCATED;
			
			for(MethodNode method : node.methods){
				System.out.println(method.name);
				if(method.name.equals(CREATE_ENDER_PORTAL) && method.desc.equals("(II)V")){
					System.out.println("Transforming!");
					for(AbstractInsnNode instruction : method.instructions.toArray()){
						method.instructions.remove(instruction);// Removes ALL OF THE INSTRUCTIONS
					}
					InsnList newInstructions = new InsnList();
					
					newInstructions.add(new VarInsnNode(Opcodes.RETURN, 0));
					
					method.instructions.insert(newInstructions);
					break;
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
