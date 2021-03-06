package de.ossi.wolfsbau.modbus;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.ghgande.j2mod.modbus.ModbusException;

import de.ossi.wolfsbau.modbus.data.ModbusDevice;
import de.ossi.wolfsbau.modbus.data.ModbusOperation;

public abstract class ModbusTCPWriter extends ModbusConnection {

	public ModbusTCPWriter(String ip, int port) {
		super(ip, port);
	}

	public void writeOperationToDevice(ModbusOperation operation, int wert, ModbusDevice device) {
		connect();
		try {
			writeOperationToDeviceInternal(operation, wert, device);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	/**
	 * Nur int -> Deshalb SingleRegister
	 * 
	 * @throws IOException
	 * @throws ModbusException
	 * @throws SocketException
	 * @throws UnknownHostException
	 * 
	 */
	private void writeOperationToDeviceInternal(ModbusOperation operation, int wert, ModbusDevice device)
			throws UnknownHostException, SocketException, IOException {
		// modbusMaster.writeSingleRegister(ModbusDevice.operation.getAddress(), wert);
	}
}
