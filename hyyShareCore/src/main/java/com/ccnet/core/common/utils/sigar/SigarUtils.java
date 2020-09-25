package com.ccnet.core.common.utils.sigar;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.OperatingSystem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.Swap;

import com.ccnet.core.common.utils.CPSUtil;

/**
 * sigar工具类
 * 
 * @author jackie wang
 * 
 */
public class SigarUtils {
	private static Sigar sigar;

	/**
	 * 获取sigar实体
	 * 
	 * @return
	 */
	public static Sigar getInstance() {
		if (null == sigar) {
			sigar = new Sigar();
		}
		return sigar;
	}
	
	
	/**
	 * 获取监控数据
	 * @return
	 */
	public static Map<String, String> getMonitorInfo() {
		Map<String, String> monitor = new HashMap<String, String>();
		Map<String, SigarInfoEntity> jvmMap = SigarUtils.convertToMap(SigarUtils.getJvmInfos());
		Map<String, SigarInfoEntity> cpuMap = SigarUtils.convertToMap(SigarUtils.getCpuInfos());
		Map<String, SigarInfoEntity> memMap = SigarUtils.convertToMap(SigarUtils.getMemoryInfos());
		
		monitor.put("jvmUsage", ((SigarInfoEntity)jvmMap.get("jvm.combinded")).getValue());
		monitor.put("ramUsage", ((SigarInfoEntity)memMap.get("mem.combinded")).getValue());
		monitor.put("cpuUsage", ((SigarInfoEntity)cpuMap.get("cpu.combinded")).getValue());
		monitor.put("jvmFree", ((SigarInfoEntity)jvmMap.get("freeMemory")).getValue());
		monitor.put("memFree", ((SigarInfoEntity)memMap.get("mem.free2")).getValue());
		
		
		
		//CPSUtil.xprint("monitor="+monitor);
		
		return monitor;
	}
	
	/**
	 * 获取服务器信息
	 * @return
	 */
	public static Map<String, String> getServerInfo() {
		
		Map<String, String> monitor = new HashMap<String, String>();
		Map<String, SigarInfoEntity> jvmMap = SigarUtils.convertToMap(SigarUtils.getJvmInfos());
		Map<String, SigarInfoEntity> memMap = SigarUtils.convertToMap(SigarUtils.getMemoryInfos());
		
		monitor.put("computerName", ((SigarInfoEntity)jvmMap.get("computerName")).getValue());
		monitor.put("hostAddress", ((SigarInfoEntity)jvmMap.get("hostAddress")).getValue());
		monitor.put("hostName", ((SigarInfoEntity)jvmMap.get("hostName")).getValue());
		monitor.put("maxMemory", ((SigarInfoEntity)jvmMap.get("maxMemory")).getValue());
		monitor.put("totalMemory", ((SigarInfoEntity)jvmMap.get("totalMemory")).getValue());
		monitor.put("freeMemory", ((SigarInfoEntity)jvmMap.get("freeMemory")).getValue());
		monitor.put("maxFreeMemory", ((SigarInfoEntity)jvmMap.get("maxFreeMemory")).getValue());
		monitor.put("availableProcessors", ((SigarInfoEntity)jvmMap.get("availableProcessors")).getValue());
		monitor.put("javaVersion", ((SigarInfoEntity)jvmMap.get("java.version")).getValue());
		monitor.put("javaVendor", ((SigarInfoEntity)jvmMap.get("java.vendor")).getValue());
		monitor.put("javaVendorUrl", ((SigarInfoEntity)jvmMap.get("java.vendor.url")).getValue());
		monitor.put("javaHome", ((SigarInfoEntity)jvmMap.get("java.home")).getValue());
		monitor.put("osName", ((SigarInfoEntity)jvmMap.get("os.name")).getValue());
		monitor.put("javaIOTmpdir", ((SigarInfoEntity)jvmMap.get("java.io.tmpdir")).getValue());
		monitor.put("osArch", ((SigarInfoEntity)jvmMap.get("os.arch")).getValue());
		monitor.put("osVersion", ((SigarInfoEntity)jvmMap.get("os.version")).getValue());
		monitor.put("jvmFree", ((SigarInfoEntity)jvmMap.get("freeMemory")).getValue());
		monitor.put("memFree", ((SigarInfoEntity)memMap.get("mem.free2")).getValue());
		CPSUtil.xprint("monitor="+monitor);
		return monitor;
	}
	

	/**
	 * 1.获取系统信息和jvm虚拟机信息
	 * 
	 * @return
	 * @throws UnknownHostException
	 */
	public static List<SigarInfoEntity> getJvmInfos() {
		List<SigarInfoEntity> jvmInfoList = new ArrayList<SigarInfoEntity>();
		Runtime r = Runtime.getRuntime();
		// 系统配置属性
		Properties sysProps = System.getProperties();
		// java对ip封装的对象
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Map<String, String> envInfoMap = System.getenv();
		String userName = envInfoMap.get("USERNAME");// 获取用户名
		String computerName = envInfoMap.get("COMPUTERNAME");// 获取计算机名
		String userDomain = envInfoMap.get("USERDOMAIN");// 获取计算机域名

		jvmInfoList.add(new SigarInfoEntity(userName, "获取用户名","userName"));
		jvmInfoList.add(new SigarInfoEntity(computerName, "获取计算机名","computerName"));
		jvmInfoList.add(new SigarInfoEntity(userDomain, "获取计算机域名","userDomain"));
		if (addr != null) {
			jvmInfoList.add(new SigarInfoEntity(addr.getHostAddress(), "获取Ip","hostAddress"));
			jvmInfoList.add(new SigarInfoEntity(addr.getHostName(), "获取主机名称","hostName"));
		}
		
		
		double maxMemory=new BigDecimal(new Double(Runtime.getRuntime().maxMemory()).doubleValue()/1024/1024).setScale(0,BigDecimal.ROUND_HALF_UP).doubleValue();
		double totalMemory=new BigDecimal(new Double(Runtime.getRuntime().totalMemory()).doubleValue()/1024/1024).setScale(0,BigDecimal.ROUND_HALF_UP).doubleValue();;
		double freeMemory=new BigDecimal(new Double(Runtime.getRuntime().freeMemory()).doubleValue()/1024/1024).setScale(0,BigDecimal.ROUND_HALF_UP).doubleValue();

		jvmInfoList.add(new SigarInfoEntity(String.valueOf(maxMemory),"JVM最大内存","maxMemory"));
		jvmInfoList.add(new SigarInfoEntity(String.valueOf(totalMemory),"JVM总内存","totalMemory"));
		jvmInfoList.add(new SigarInfoEntity(String.valueOf(freeMemory),"JVM剩余内存","freeMemory"));
		jvmInfoList.add(new SigarInfoEntity(String.valueOf(maxMemory-totalMemory+freeMemory),"JVM最大可用内存","maxFreeMemory"));
		double useMemory = new BigDecimal((totalMemory-freeMemory)/totalMemory * 100).setScale(0,BigDecimal.ROUND_HALF_UP).doubleValue();
		jvmInfoList.add(new SigarInfoEntity((int)useMemory+"","JVM内存使用率","jvm.combinded"));
		
		
		jvmInfoList.add(new SigarInfoEntity(String.valueOf(r.availableProcessors()), "jvm处理器个数","availableProcessors"));
		jvmInfoList.add(new SigarInfoEntity(sysProps.getProperty("java.version"), "Java的运行环境版本","java.version"));
		jvmInfoList.add(new SigarInfoEntity(sysProps.getProperty("java.vendor"), "Java的运行环境供应商","java.vendor"));
		jvmInfoList.add(new SigarInfoEntity(sysProps.getProperty("java.vendor.url"), "Java供应商的URL","java.vendor.url"));
		jvmInfoList.add(new SigarInfoEntity(sysProps.getProperty("java.home"),"Java的安装路径","java.home"));
		jvmInfoList.add(new SigarInfoEntity(sysProps.getProperty("java.vm.specification.version"), "Java的虚拟机规范版本","java.vm.specification.version"));
		jvmInfoList.add(new SigarInfoEntity(sysProps.getProperty("java.vm.specification.vendor"), "Java的虚拟机规范供应商","java.vm.specification.vendor"));
		jvmInfoList.add(new SigarInfoEntity(sysProps.getProperty("java.vm.specification.name"), "Java的虚拟机规范名称","java.vm.specification.name"));
		jvmInfoList.add(new SigarInfoEntity(sysProps.getProperty("java.vm.version"), "Java的虚拟机实现版本","java.vm.version"));
		jvmInfoList.add(new SigarInfoEntity(sysProps.getProperty("java.vm.vendor"), "Java的虚拟机实现供应商","java.vm.vendor"));
		jvmInfoList.add(new SigarInfoEntity(sysProps.getProperty("java.vm.name"), "Java的虚拟机实现名称","java.vm.name"));
		jvmInfoList.add(new SigarInfoEntity(sysProps.getProperty("java.specification.version"), "Java运行时环境规范版本","java.specification.version"));
		jvmInfoList.add(new SigarInfoEntity(sysProps.getProperty("java.specification.vendor"), "Java运行时环境规范供应商","java.specification.vendor"));
		jvmInfoList.add(new SigarInfoEntity(sysProps.getProperty("java.specification.name"), "Java的虚拟机规范名称","java.specification.name"));
		jvmInfoList.add(new SigarInfoEntity(sysProps.getProperty("java.class.version"), "Java的类格式版本号","java.class.version"));
		jvmInfoList.add(new SigarInfoEntity(sysProps.getProperty("java.class.path"), "Java的类路径","java.class.path"));
		jvmInfoList.add(new SigarInfoEntity(sysProps.getProperty("java.library.path"), "加载库时搜索的路径列表","java.library.path"));
		jvmInfoList.add(new SigarInfoEntity(sysProps.getProperty("java.io.tmpdir"), "默认的临时文件路径","java.io.tmpdir"));
		jvmInfoList.add(new SigarInfoEntity(sysProps.getProperty("java.ext.dirs"), "一个或多个扩展目录的路径","java.ext.dirs"));
		jvmInfoList.add(new SigarInfoEntity(sysProps.getProperty("os.name"),"操作系统的名称","os.name"));
		jvmInfoList.add(new SigarInfoEntity(sysProps.getProperty("os.arch"),"操作系统的构架","os.arch"));
		jvmInfoList.add(new SigarInfoEntity(sysProps.getProperty("os.version"),"操作系统的版本","os.version"));
		jvmInfoList.add(new SigarInfoEntity(sysProps.getProperty("file.separator"), "文件分隔符","file.separator"));
		jvmInfoList.add(new SigarInfoEntity(sysProps.getProperty("path.separator"), "路径分隔符","path.separator"));
		jvmInfoList.add(new SigarInfoEntity(sysProps.getProperty("line.separator"), "行分隔符","line.separator"));
		jvmInfoList.add(new SigarInfoEntity(sysProps.getProperty("user.name"),"用户的账户名称","user.name"));
		jvmInfoList.add(new SigarInfoEntity(sysProps.getProperty("user.home"),"用户的主目录","user.home"));
		jvmInfoList.add(new SigarInfoEntity(sysProps.getProperty("user.dir"),"用户的当前工作目录","user.dir"));

		return jvmInfoList;
	}
	
	

	/**
	 * 2.获取cpu信息
	 * 
	 * @return
	 * @throws SigarException
	 */
	public static List<SigarInfoEntity> getCpuInfos() {
		List<SigarInfoEntity> cpuInfoList = new ArrayList<SigarInfoEntity>();
		try {
			CpuInfo[] cpuInfos = getInstance().getCpuInfoList();
			CpuPerc[] cpuPercs = getInstance().getCpuPercList();
			
			cpuInfoList.add(new SigarInfoEntity(cpuInfos.length+"", "CPU总数量","cpu.count"));
			if (ArrayUtils.isNotEmpty(cpuInfos)) {
				cpuInfoList.add(new SigarInfoEntity(String.valueOf(cpuInfos[0].getMhz()) + " * " + cpuInfos.length, "CPU的总量MHz","cpu.mhz"));
				cpuInfoList.add(new SigarInfoEntity(cpuInfos[0].getVendor(),"获得CPU的供应商","cpu.vendor"));
				cpuInfoList.add(new SigarInfoEntity(cpuInfos[0].getModel(), "获得CPU的类别","cpu.model"));
				cpuInfoList.add(new SigarInfoEntity(String.valueOf(cpuInfos[0].getCacheSize()) + " * " + cpuInfos.length, "缓冲存储器数量","cpu.cacheSize"));
				cpuInfoList.add(new SigarInfoEntity(String.valueOf(cpuInfos[0].getTotalCores()) + " * " + cpuInfos.length, "CPU核心数","cpu.totalCores"));
			}

			double user = 0d;
			double sys = 0d;
			double wait = 0d;
			double nice = 0d;
			double idle = 0d;
			double combinded = 0d;
			double size = cpuPercs.length;
			for (int i = 0; i < cpuPercs.length; i++) {
				CpuPerc cpuPerc = cpuPercs[i];
				user += cpuPerc.getUser();
				sys += cpuPerc.getSys();
				wait += cpuPerc.getWait();
				nice += cpuPerc.getNice();
				idle += cpuPerc.getIdle();
				combinded += cpuPerc.getCombined();
			  }
			cpuInfoList.add(new SigarInfoEntity(String.valueOf(user/size), "CPU用户使用率","cpu.user"));
			cpuInfoList.add(new SigarInfoEntity(String.valueOf(sys/size), "CPU系统使用率","cpu.sys"));
			cpuInfoList.add(new SigarInfoEntity(String.valueOf(wait/size), "CPU当前等待率","cpu.wait"));
			cpuInfoList.add(new SigarInfoEntity(String.valueOf(nice/size), "CPU当前错误率","cpu.nice"));
			cpuInfoList.add(new SigarInfoEntity(String.valueOf(idle/size), "CPU当前空闲率","cpu.idle"));
			double useCombinded = (combinded/size * 100);
			cpuInfoList.add(new SigarInfoEntity((int)useCombinded+"", "CPU总的使用率","cpu.combinded"));
			
		} catch (SigarException e) {
			e.printStackTrace();
	    }
		
		return cpuInfoList;
	}

	/**
	 * 3.获取内存信息
	 * 
	 * @return
	 * @throws SigarException
	 */
	public static List<SigarInfoEntity> getMemoryInfos(){
		List<SigarInfoEntity> memoryInfoList = new ArrayList<SigarInfoEntity>();
		try {
			Mem mem = getInstance().getMem();
			double totalMemory=new BigDecimal(new Double(mem.getTotal()).doubleValue()/1024/1024).setScale(0,BigDecimal.ROUND_HALF_UP).doubleValue();
			double usedMemory=new BigDecimal(new Double(mem.getUsed()).doubleValue()/1024/1024).setScale(0,BigDecimal.ROUND_HALF_UP).doubleValue();
			double freeMemory=new BigDecimal(new Double(mem.getFree()).doubleValue()/1024/1024).setScale(0,BigDecimal.ROUND_HALF_UP).doubleValue();
			memoryInfoList.add(new SigarInfoEntity(String.valueOf(totalMemory / 1024L),"内存总量","mem.total"));
			memoryInfoList.add(new SigarInfoEntity(String.valueOf(usedMemory / 1024L), "当前内存使用量","mem.used"));
			memoryInfoList.add(new SigarInfoEntity(String.valueOf(freeMemory / 1024L), "当前内存剩余量","mem.free"));
			double freeMem = new BigDecimal(freeMemory/1024L).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			memoryInfoList.add(new SigarInfoEntity(String.valueOf(freeMem), "当前内存剩余量","mem.free2"));
			double useCombinded = new BigDecimal((usedMemory)/totalMemory * 100).setScale(0,BigDecimal.ROUND_HALF_UP).doubleValue();
			memoryInfoList.add(new SigarInfoEntity((int)useCombinded + "", "当前内存使用率","mem.combinded"));
			Swap swap = getInstance().getSwap();
			memoryInfoList.add(new SigarInfoEntity(String.valueOf(swap.getTotal() / 1024L), "交换区总量","swap.total"));
			memoryInfoList.add(new SigarInfoEntity(String.valueOf(swap.getUsed() / 1024L), "当前交换区使用量","swap.used"));
			memoryInfoList.add(new SigarInfoEntity(String.valueOf(swap.getFree() / 1024L), "当前交换区剩余量","swap.free"));
		} catch (SigarException e) {
			e.printStackTrace();
		}
		return memoryInfoList;
	}

	/**
	 * 4.获取操作系统信息
	 * 
	 * @return
	 */
	public static List<SigarInfoEntity> getOsInfos() {
		List<SigarInfoEntity> osInfoList = new ArrayList<SigarInfoEntity>();
		OperatingSystem os = OperatingSystem.getInstance();
		osInfoList.add(new SigarInfoEntity(os.getArch(), "操作系统","os.arch"));
		osInfoList.add(new SigarInfoEntity(os.getCpuEndian(), "操作系统CpuEndian()","os.cpuEndian"));
		osInfoList.add(new SigarInfoEntity(os.getDataModel(), "操作系统DataModel()","os.dataModel"));
		osInfoList.add(new SigarInfoEntity(os.getDescription(), "操作系统的描述","os.description"));
		osInfoList.add(new SigarInfoEntity(os.getVendor(), "操作系统的供应商","os.vendor"));
		osInfoList.add(new SigarInfoEntity(os.getVendorCodeName(), "操作系统的供应商编号","os.vendorCodeName"));
		osInfoList.add(new SigarInfoEntity(os.getVendorName(), "操作系统的供应商名称","os.vendorName"));
		osInfoList.add(new SigarInfoEntity(os.getVendorVersion(), "操作系统供应商类型","os.vendorVersion"));
		osInfoList.add(new SigarInfoEntity(os.getVersion(), "操作系统的版本号","os.version"));
		return osInfoList;
	}

	/**
	 * 5.获取文件信息
	 * 
	 * @return
	 * @throws SigarException
	 */
	public static List<SigarInfoEntity> getFileInfos(){
		List<SigarInfoEntity> fileInfoList = new ArrayList<SigarInfoEntity>();

		try {
			FileSystem fslist[] = getInstance().getFileSystemList();
			long total = 0l,free = 0l,avail = 0l,used = 0l; 
			for (int i = 0; i < fslist.length; i++) {
				FileSystem fs = fslist[i];
				fileInfoList.add(new SigarInfoEntity(i + "", "分区的盘符索引号" + fs.getDevName(),"file.index"));
				fileInfoList.add(new SigarInfoEntity(fs.getDevName(), "盘符名称" + fs.getDevName(),"file.devName"));
				fileInfoList.add(new SigarInfoEntity(fs.getDirName(), "盘符路径" + fs.getDevName(),"file.dirName"));
				fileInfoList.add(new SigarInfoEntity(fs.getFlags() + "", "盘符标志" + fs.getDevName(),"file.flags"));
				fileInfoList.add(new SigarInfoEntity(fs.getSysTypeName(),"盘符类型(FAT32,NTFS)" + fs.getDevName(),"file.sysTypeName"));
				fileInfoList.add(new SigarInfoEntity(fs.getTypeName(), "盘符类型名" + fs.getDevName(),"file.typeName"));
				fileInfoList.add(new SigarInfoEntity(fs.getType() + "", "盘符文件系统类型"+ fs.getDevName(),"file.type"));
				
				FileSystemUsage usage = null;
				usage = getInstance().getFileSystemUsage(fs.getDirName());
				switch (fs.getType()) {
				case 0: // TYPE_UNKNOWN ：未知
					break;
				case 1: // TYPE_NONE
					break;
				case 2: // TYPE_LOCAL_DISK : 本地硬盘

					fileInfoList.add(new SigarInfoEntity(String.valueOf(usage.getTotal()),"文件系统总大小(KB)" + fs.getDevName(),"file.total"));
					fileInfoList.add(new SigarInfoEntity(String.valueOf(usage.getFree()),"文件系统剩余大小(KB)" + fs.getDevName(),"file.free"));
					fileInfoList.add(new SigarInfoEntity(String.valueOf(usage.getAvail()),"文件系统可用大小(KB)" + fs.getDevName(),"file.avail"));
					fileInfoList.add(new SigarInfoEntity(String.valueOf(usage.getUsed()),"文件系统已经使用量(KB)" + fs.getDevName(),"file.used"));
					fileInfoList.add(new SigarInfoEntity(usage.getUsePercent()* 100D + "%", "文件系统资源的利用率" + fs.getDevName(),"file.usePercent"));
					total += usage.getTotal();  free += usage.getFree(); avail += usage.getAvail(); used += usage.getUsed();
					break;
				case 3:// TYPE_NETWORK ：网络
					break;
				case 4:// TYPE_RAM_DISK ：闪存
					break;
				case 5:// TYPE_CDROM ：光驱
					break;
				case 6:// TYPE_SWAP ：页面交换
					break;
				}

				fileInfoList.add(new SigarInfoEntity(String.valueOf(usage.getDiskReads()), fs.getDevName() + "读出","file.diskReads"));
				fileInfoList.add(new SigarInfoEntity(String.valueOf(usage.getDiskWrites()), fs.getDevName() + "写入","file.diskWrites"));
			}
			fileInfoList.add(new SigarInfoEntity(String.valueOf(total),"硬盘总大小(KB)","all.file.total"));
			fileInfoList.add(new SigarInfoEntity(String.valueOf(free),"硬盘剩余大小(KB)","all.file.free"));
			fileInfoList.add(new SigarInfoEntity(String.valueOf(avail),"硬盘可用大小(KB)","all.file.avail"));
			fileInfoList.add(new SigarInfoEntity(String.valueOf(used),"硬盘已经使用量(KB)","all.file.used"));
			fileInfoList.add(new SigarInfoEntity(String.valueOf(((double)used / (double)total) * 100D), "硬盘使用率%","all.file.usePercent"));
			fileInfoList.add(new SigarInfoEntity(String.valueOf(fslist.length), "分区数","all.partition.count"));
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileInfoList;
	}

	/**
	 * 6.获取网络信息
	 * 
	 * @return
	 * @throws SigarException
	 */
	public static List<SigarInfoEntity> getNetInfos() {
		List<SigarInfoEntity> netInfoList = new ArrayList<SigarInfoEntity>();
		try {
			String nIfNames[] = getInstance().getNetInterfaceList();
			for (int i = 0; i < nIfNames.length; i++) {
				String name = nIfNames[i];
				NetInterfaceConfig nIfConfig = getInstance().getNetInterfaceConfig(name);
				netInfoList.add(new SigarInfoEntity(name, "网络设备名" + i,"net.name"));
				netInfoList.add(new SigarInfoEntity(nIfConfig.getAddress(), "IP地址"+ i,"net.address"));
				netInfoList.add(new SigarInfoEntity(nIfConfig.getNetmask(), "子网掩码"+ i,"net.netmask"));

				if ((nIfConfig.getFlags() & 1L) <= 0L) {
					System.out.println("getNetInterfaceStat not exist");
					continue;
				}
				NetInterfaceStat nIfStat = getInstance().getNetInterfaceStat(name);
				netInfoList.add(new SigarInfoEntity(nIfStat.getRxPackets() + "","接收的总包裹数" + i,"net.rxPackets"));
				netInfoList.add(new SigarInfoEntity(nIfStat.getTxPackets() + "","发送的总包裹数" + i,"net.txPackets"));
				netInfoList.add(new SigarInfoEntity(nIfStat.getRxBytes() + "","接收到的总字节数" + i,"net.rxBytes"));
				netInfoList.add(new SigarInfoEntity(nIfStat.getTxBytes() + "","发送的总字节数" + i,"net.txBytes"));
				netInfoList.add(new SigarInfoEntity(nIfStat.getRxErrors() + "","接收到的错误包数" + i,"net.rxError"));
				netInfoList.add(new SigarInfoEntity(nIfStat.getTxErrors() + "","发送数据包时的错误数" + i,"net.txError"));
				netInfoList.add(new SigarInfoEntity(nIfStat.getRxDropped() + "","接收时丢弃的包数" + i,"net.rxDropped"));
				netInfoList.add(new SigarInfoEntity(nIfStat.getTxDropped() + "","发送时丢弃的包数" + i,"net.txDropped"));
			}
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return netInfoList;
	}
	
	public static Map<String, SigarInfoEntity> convertToMap(List<SigarInfoEntity> list) {
		Map<String, SigarInfoEntity> map = new HashMap<String, SigarInfoEntity>();
		if (CollectionUtils.isNotEmpty(list)) {
			for (SigarInfoEntity sigarInfoEntity : list) {
				map.put(sigarInfoEntity.getName(), sigarInfoEntity);
			}
		}
		return map;
	}
	
	
}
