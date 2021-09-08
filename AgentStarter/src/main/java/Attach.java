
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import java.util.List;

public class Attach {
    public static void main(String[] args) throws Exception {
        VirtualMachine vm = null;
        List<VirtualMachineDescriptor> listAfter = null;
        List<VirtualMachineDescriptor> listBefore = null;
        listBefore = VirtualMachine.list();
        while (true) {
            try {
                listAfter = VirtualMachine.list();
                if (listAfter.size() <= 0)
                    continue;
                for (VirtualMachineDescriptor vmd : listAfter) {
                    vm = VirtualMachine.attach(vmd);
                    listBefore.add(vmd);
                    System.out.println("i find a vm,agent.jar was injected.");
                    Thread.sleep(1000);
                    if (null != vm) {
                        vm.loadAgent("/Users/dupei/workspace/idea/Example/target/artifacts/Agent.jar");
                        vm.detach();
                    }
                }
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}