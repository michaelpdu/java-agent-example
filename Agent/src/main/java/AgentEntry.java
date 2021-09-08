import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

public class AgentEntry {
    public static void agentmain(String agentArgs, Instrumentation inst)
            throws ClassNotFoundException, UnmodifiableClassException,
            InterruptedException {
        inst.addTransformer(new MyTransformer(), true);
        Class[] loadedClasses = inst.getAllLoadedClasses();
        for (Class c : loadedClasses) {
            if (c.getName().equals("Bird")) {
                try {
                    inst.retransformClasses(c);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Class changed!");
    }
}