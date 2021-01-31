package cn.linkpower.controller;

import org.hyperic.sigar.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class TestController {

	@RequestMapping("/test")
	public void test() {
		try {
			log.info("-----------------开始-----------------");
			log.info("-----------------cpu信息-----------------");
			// cpu信息
			cpu();
			log.info("-----------------内存信息-----------------");

			// 内存信息
			memory();
			log.info("-----------------操作系统信息-----------------");

			// 操作系统信息
			os();
			log.info("-----------------用户信息-----------------");

			// 用户信息
			who();
			log.info("-----------------文件系统信息-----------------");

			// 文件系统信息
			file();
			log.info("-----------------网络信息-----------------");

			// 网络信息
			net();
			log.info("-----------------以太网信息-----------------");
			// 以太网信息
			ethernet();
			log.info("-----------------结束-----------------");

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private static void memory() throws SigarException {
		Sigar sigar = new Sigar();
		Mem mem = sigar.getMem();
		// 内存总量
		log.info("内存总量:    " + mem.getTotal() / 1024L + "K av");
		// 当前内存使用量
		log.info("当前内存使用量:    " + mem.getUsed() / 1024L + "K used");
		// 当前内存剩余量
		log.info("当前内存剩余量:    " + mem.getFree() / 1024L + "K free");
		Swap swap = sigar.getSwap();
		// 交换区总量
		log.info("交换区总量:    " + swap.getTotal() / 1024L + "K av");
		// 当前交换区使用量
		log.info("当前交换区使用量:    " + swap.getUsed() / 1024L + "K used");
		// 当前交换区剩余量
		log.info("当前交换区剩余量:    " + swap.getFree() / 1024L + "K free");
	}

	private static void cpu() throws SigarException {
		Sigar sigar = new Sigar();
		CpuInfo infos[] = sigar.getCpuInfoList();
		CpuPerc cpuList[] = null;
		cpuList = sigar.getCpuPercList();
		for (int i = 0; i < infos.length; i++) {// 不管是单块CPU还是多CPU都适用
			CpuInfo info = infos[i];
			log.info("第" + (i + 1) + "块CPU信息");
			log.info("CPU的总量MHz:    " + info.getMhz());// CPU的总量MHz
			log.info("CPU生产商:    " + info.getVendor());// 获得CPU的卖主，如：Intel
			log.info("CPU类别:    " + info.getModel());// 获得CPU的类别，如：Celeron
			log.info("CPU缓存数量:    " + info.getCacheSize());// 缓冲存储器数量
			printCpuPerc(cpuList[i]);
		}
	}

	private static void printCpuPerc(CpuPerc cpu) {
		log.info("CPU用户使用率:    " + CpuPerc.format(cpu.getUser()));// 用户使用率
		log.info("CPU系统使用率:    " + CpuPerc.format(cpu.getSys()));// 系统使用率
		log.info("CPU当前等待率:    " + CpuPerc.format(cpu.getWait()));// 当前等待率
		log.info("CPU当前错误率:    " + CpuPerc.format(cpu.getNice()));//
		log.info("CPU当前空闲率:    " + CpuPerc.format(cpu.getIdle()));// 当前空闲率
		log.info("CPU总的使用率:    " + CpuPerc.format(cpu.getCombined()));// 总的使用率
	}

	private static void os() {
		OperatingSystem OS = OperatingSystem.getInstance();
		// 操作系统内核类型如： 386、486、586等x86
		log.info("操作系统:    " + OS.getArch());
		log.info("操作系统CpuEndian():    " + OS.getCpuEndian());//
		log.info("操作系统DataModel():    " + OS.getDataModel());//
		// 系统描述
		log.info("操作系统的描述:    " + OS.getDescription());
		// 操作系统类型
		// log.info("OS.getName(): " + OS.getName());
		// log.info("OS.getPatchLevel(): " + OS.getPatchLevel());//
		// 操作系统的卖主
		log.info("操作系统的卖主:    " + OS.getVendor());
		// 卖主名称
		log.info("操作系统的卖主名:    " + OS.getVendorCodeName());
		// 操作系统名称
		log.info("操作系统名称:    " + OS.getVendorName());
		// 操作系统卖主类型
		log.info("操作系统卖主类型:    " + OS.getVendorVersion());
		// 操作系统的版本号
		log.info("操作系统的版本号:    " + OS.getVersion());
	}

	private static void who() throws SigarException {
		Sigar sigar = new Sigar();
		Who who[] = sigar.getWhoList();
		if (who != null && who.length > 0) {
			for (int i = 0; i < who.length; i++) {
				// log.info("当前系统进程表中的用户名" + String.valueOf(i));
				Who _who = who[i];
				log.info("用户控制台:    " + _who.getDevice());
				log.info("用户host:    " + _who.getHost());
				// log.info("getTime(): " + _who.getTime());
				// 当前系统进程表中的用户名
				log.info("当前系统进程表中的用户名:    " + _who.getUser());
			}
		}
	}

	private static void file() throws Exception {
		Sigar sigar = new Sigar();
		FileSystem fslist[] = sigar.getFileSystemList();
		for (int i = 0; i < fslist.length; i++) {
			log.info("分区的盘符名称" + i);
			FileSystem fs = fslist[i];
			// 分区的盘符名称
			log.info("盘符名称:    " + fs.getDevName());
			// 分区的盘符名称
			log.info("盘符路径:    " + fs.getDirName());
			log.info("盘符标志:    " + fs.getFlags());//
			// 文件系统类型，比如 FAT32、NTFS
			log.info("盘符类型:    " + fs.getSysTypeName());
			// 文件系统类型名，比如本地硬盘、光驱、网络文件系统等
			log.info("盘符类型名:    " + fs.getTypeName());
			// 文件系统类型
			log.info("盘符文件系统类型:    " + fs.getType());
			FileSystemUsage usage = null;
			usage = sigar.getFileSystemUsage(fs.getDirName());
			switch (fs.getType()) {
			case 0: // TYPE_UNKNOWN ：未知
				break;
			case 1: // TYPE_NONE
				break;
			case 2: // TYPE_LOCAL_DISK : 本地硬盘
				// 文件系统总大小
				log.info(fs.getDevName() + "总大小:    " + usage.getTotal() + "KB");
				// 文件系统剩余大小
				log.info(fs.getDevName() + "剩余大小:    " + usage.getFree() + "KB");
				// 文件系统可用大小
				log.info(fs.getDevName() + "可用大小:    " + usage.getAvail() + "KB");
				// 文件系统已经使用量
				log.info(fs.getDevName() + "已经使用量:    " + usage.getUsed() + "KB");
				double usePercent = usage.getUsePercent() * 100D;
				// 文件系统资源的利用率
				log.info(fs.getDevName() + "资源的利用率:    " + usePercent + "%");
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
			log.info(fs.getDevName() + "读出：    " + usage.getDiskReads());
			log.info(fs.getDevName() + "写入：    " + usage.getDiskWrites());
		}
		return;
	}

	private static void net() throws Exception {
		Sigar sigar = new Sigar();
		String ifNames[] = sigar.getNetInterfaceList();
		for (int i = 0; i < ifNames.length; i++) {
			String name = ifNames[i];
			NetInterfaceConfig ifconfig = sigar.getNetInterfaceConfig(name);
			log.info("网络设备名:    " + name);// 网络设备名
			log.info("IP地址:    " + ifconfig.getAddress());// IP地址
			log.info("子网掩码:    " + ifconfig.getNetmask());// 子网掩码
			if ((ifconfig.getFlags() & 1L) <= 0L) {
				log.info("!IFF_UP...skipping getNetInterfaceStat");
				continue;
			}
			NetInterfaceStat ifstat = sigar.getNetInterfaceStat(name);
			log.info(name + "接收的总包裹数:" + ifstat.getRxPackets());// 接收的总包裹数
			log.info(name + "发送的总包裹数:" + ifstat.getTxPackets());// 发送的总包裹数
			log.info(name + "接收到的总字节数:" + ifstat.getRxBytes());// 接收到的总字节数
			log.info(name + "发送的总字节数:" + ifstat.getTxBytes());// 发送的总字节数
			log.info(name + "接收到的错误包数:" + ifstat.getRxErrors());// 接收到的错误包数
			log.info(name + "发送数据包时的错误数:" + ifstat.getTxErrors());// 发送数据包时的错误数
			log.info(name + "接收时丢弃的包数:" + ifstat.getRxDropped());// 接收时丢弃的包数
			log.info(name + "发送时丢弃的包数:" + ifstat.getTxDropped());// 发送时丢弃的包数
		}
	}

	private static void ethernet() throws SigarException {
		Sigar sigar = null;
		sigar = new Sigar();
		String[] ifaces = sigar.getNetInterfaceList();
		for (int i = 0; i < ifaces.length; i++) {
			NetInterfaceConfig cfg = sigar.getNetInterfaceConfig(ifaces[i]);
			if (NetFlags.LOOPBACK_ADDRESS.equals(cfg.getAddress()) || (cfg.getFlags() & NetFlags.IFF_LOOPBACK) != 0
					|| NetFlags.NULL_HWADDR.equals(cfg.getHwaddr())) {
				continue;
			}
			log.info(cfg.getName() + "IP地址:" + cfg.getAddress());// IP地址
			log.info(cfg.getName() + "网关广播地址:" + cfg.getBroadcast());// 网关广播地址
			log.info(cfg.getName() + "网卡MAC地址:" + cfg.getHwaddr());// 网卡MAC地址
			log.info(cfg.getName() + "子网掩码:" + cfg.getNetmask());// 子网掩码
			log.info(cfg.getName() + "网卡描述信息:" + cfg.getDescription());// 网卡描述信息
			log.info(cfg.getName() + "网卡类型" + cfg.getType());//
		}
	}
}
