package ro.util;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.generic.*;

import java.io.IOException;

import static org.apache.bcel.Const.ACC_PUBLIC;
import static org.apache.bcel.Const.ACC_STATIC;

/**
 * This class uses <b>BCEL</b> in order to inject a new method in a ".class" file specified by an absolute path.
 * <p>
 * The method injected is a static method called 'printSecretMessage' and it simply prints the following message on the screen:
 * 'If you see this message, the injection succeeded'
 */
public class MockInjector {
    public static void injectMockMethodInClass(String absolutePath) throws IOException {
        JavaClass cls = new ClassParser(absolutePath).parse();
        InstructionList instructionList = new InstructionList();

        ClassGen classGen = new ClassGen(cls);

        InstructionFactory instructionFactory = new InstructionFactory(classGen.getConstantPool());
        instructionList.append(instructionFactory.createPrintln("If you see this message, the injection succeeded"));
        instructionList.append(InstructionFactory.createReturn(Type.VOID));

        MethodGen method = new MethodGen(
                ACC_STATIC | ACC_PUBLIC,
                Type.VOID,
                new Type[0],
                new String[0],
                "printSecretMessage",
                cls.getClassName(),
                instructionList,
                classGen.getConstantPool()
        );

        method.setMaxStack();
        method.setMaxLocals();

        classGen.addMethod(method.getMethod());

        JavaClass modifiedClass = classGen.getJavaClass();
        modifiedClass.dump(absolutePath);
    }
}
