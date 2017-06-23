package com.michalfudala.histate.testing;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerCertificateException;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.DockerException;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerCreation;
import com.spotify.docker.client.messages.PortBinding;
import com.sun.tools.attach.VirtualMachine;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

public class Main {
    public static void main(String[] args) throws IOException, DockerCertificateException, DockerException, InterruptedException {

        System.setProperty("org.apache.logging.log4j.simplelog.StatusLogger.level", "INFO");

        DockerClient docker = DefaultDockerClient.builder().uri("unix:///var/run/docker.sock").build();

        System.out.println("got client");
        docker.pull("busybox");

        System.out.println("got busy");


        ContainerConfig config = ContainerConfig.builder()
                .image("busybox").exposedPorts("80", "443")
                .cmd("sh", "-c", "while :; do sleep 1; done")
                .build();

        ContainerCreation id = docker.createContainer(config);

        System.out.println(docker.inspectContainer(id.id()));

        System.out.println("");

        String agent = "agent.jar";

        FileOutputStream agentJar = new FileOutputStream(agent);
        JarOutputStream jarOutputStream = new JarOutputStream(agentJar);

        String classPath = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        add(new File(classPath), jarOutputStream);

        loadAgent(agent);
    }

    public static void loadAgent(String agentJar) {


        String nameOfRunningVM = ManagementFactory.getRuntimeMXBean().getName();
        int p = nameOfRunningVM.indexOf('@');
        String pid = nameOfRunningVM.substring(0, p);

        try {
            VirtualMachine vm = VirtualMachine.attach(pid);
            vm.loadAgent(agentJar, "");
            vm.detach();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private static void add(File source, JarOutputStream target) throws IOException
    {
        BufferedInputStream in = null;
        try
        {
            if (source.isDirectory())
            {
                String name = source.getPath().replace("\\", "/");
                if (!name.isEmpty())
                {
                    if (!name.endsWith("/"))
                        name += "/";
                    JarEntry entry = new JarEntry(name);
                    entry.setTime(source.lastModified());
                    target.putNextEntry(entry);
                    target.closeEntry();
                }
                for (File nestedFile: source.listFiles())
                    add(nestedFile, target);
                return;
            }

            JarEntry entry = new JarEntry(source.getPath().replace("\\", "/"));
            entry.setTime(source.lastModified());
            target.putNextEntry(entry);
            in = new BufferedInputStream(new FileInputStream(source));

            byte[] buffer = new byte[1024];
            while (true)
            {
                int count = in.read(buffer);
                if (count == -1)
                    break;
                target.write(buffer, 0, count);
            }
            target.closeEntry();
        }
        finally
        {
            if (in != null)
                in.close();
        }
    }
}
