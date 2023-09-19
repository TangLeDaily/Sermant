import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import java.io.IOException;
import java.util.List;

public class VirtualMachineApplication {
    public static void main(String[] args) {
        try {
            List<VirtualMachineDescriptor> list = VirtualMachine.list();
            for (VirtualMachineDescriptor vmd : list) {
                System.out.println(vmd.displayName());
                if (vmd.displayName().endsWith("agentcore-test-application-1.0.0-jar-with-dependencies.jar")) {
                    System.out.println("find it");
                    VirtualMachine virtualMachine = VirtualMachine.attach(vmd.id());
                    if (args.length == 0) {
                        return;
                    }
                    if (args.length == 1) {
                        String agentPath = args[0];
                        virtualMachine.loadAgent(agentPath);
                        return;
                    }
                    String agentPath = args[0];
                    String command = args[1];
                    if (command == null || command.equals("NULL")) {
                        String configParam = "command=" + command;
                        virtualMachine.loadAgent(agentPath, configParam);
                    } else {
                        virtualMachine.loadAgent(agentPath);
                    }
                    virtualMachine.detach();
                }
            }
        } catch (AgentInitializationException | IOException | AgentLoadException | AttachNotSupportedException e) {
            // ignore
        }
    }
}
