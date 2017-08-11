package zhudou.portscan;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class Ip {
    public String getLocalIP(){
        String hostIp = null;
        try {
            Enumeration nis = NetworkInterface.getNetworkInterfaces();
            InetAddress ia = null;
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement();
                    if (ia instanceof Inet6Address) {
                        continue;// skip ipv6
                    }
                    String ip = ia.getHostAddress();
                    if (!"127.0.0.1".equals(ip)) {
                        hostIp = ia.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        System.out.print("host:");
        System.out.print(hostIp);
        return hostIp;
    }
    public String[] getAllIp(){
        String ip=getLocalIP();
        String ip_network_segment="";
        String[] sourceIP = ip.split("\\.");//
        for (int i = 0; i < sourceIP.length-1; i++) {
            ip_network_segment+=sourceIP[i]+".";
        }
        String[] ipArray=new String[254];
        for (int i = 0; i < ipArray.length; i++) {
            ipArray[i]=ip_network_segment+i;
        }
        return ipArray;
    }
}
