package xbony2.aepm;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodNode;

public class AEPMClassTransformer implements IClassTransformer {
	
	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass){
		boolean isObfuscated = !name.equals(transformedName);
		return name.equals("net.minecraft.entity.boss.EntityDragon") ? transform(basicClass, isObfuscated) : basicClass;	
	}
	
	/**
	 * Small note: FMLLog.log() (and similar methods) should not be used in this method, since it isn't loaded yet. No shame in using the old built-in solution of System.out.println() ;)
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
			
			final String CREATE_ENDER_PORTAL = isObfuscated ? "b" : "createEnderPortal";
			
			for(MethodNode method : node.methods){
				if(method.name.equals(CREATE_ENDER_PORTAL) && method.desc.equals("(II)V")){
					System.out.println("[AEPM] Transforming EntityDragon class.");
					for(AbstractInsnNode instruction : method.instructions.toArray())
						method.instructions.remove(instruction); //Removes each instruction
					
					InsnList newInstructions = new InsnList();
					
					newInstructions.add(new InsnNode(Opcodes.RETURN));
					
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
