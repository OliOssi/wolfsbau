package de.ossi.wolfsbau.modbus.data;

import java.time.LocalDateTime;

public class ModbusResultInt {

	private final ModbusOperation operation;
	private final Integer wert;
	private final LocalDateTime zeitpunkt;

	public ModbusResultInt(ModbusOperation operation, Integer wert) {
		this.operation = operation;
		this.wert = wert;
		this.zeitpunkt = LocalDateTime.now();
	}

	public ModbusOperation getOperation() {
		return operation;
	}

	public Integer getWert() {
		return wert;
	}

	public LocalDateTime getZeitpunkt() {
		return zeitpunkt;
	}

	@Override
	public String toString() {
		StringBuilder socAusgabe = new StringBuilder();
		socAusgabe.append(operation.getDescription());
		socAusgabe.append(" ");
		socAusgabe.append(ermittleWertMitUnitAusgabe());
		return socAusgabe.toString();
	}

	private double berechneSkaliertenWert() {
		return ermittleWertInRange() * operation.getScaleFactor();
	}

	/**
	 * Wenn der Wert außerhalb der Range des Rückgabewert liegt, müssen wir ihn wie
	 * ein Overflow behandeln und um
	 * 
	 * @return
	 */
	public int ermittleWertInRange() {
		if (operation.getWertRange().isBefore(wert)) {
			// groesser als Max Wert der Range
			return wert - werteInRange();
		} else if (operation.getWertRange().isAfter(wert)) {
			// kleiner als Min Wert der Range
			return wert + werteInRange();
		} else {
			// Wert liegt in Range
			return wert;
		}
	}

	/**
	 * Zahlen in der Range berechnen. Die Grenzen können negativ sein, deshalb
	 * Betrag davon rechnen.
	 */
	private Integer werteInRange() {
		return Math.abs(operation.getWertRange().getMinimum()) + Math.abs(operation.getWertRange().getMaximum());
	}

	private String ermittleWertMitUnitAusgabe() {
		switch (operation.getDbusUnit()) {
		case RELAY_STATE:
			return relayStateToString();
		case BATTERY_STATE:
			return batteryStateToString();
		case SOURCE:
			return sourceToString();
		default:
			return new StringBuilder().append(berechneSkaliertenWert()).append(" ").append(operation.getDbusUnit().toString()).toString();
		}
	}

	/** 0=Available;1=Grid;2=Generator;3=Shore Power;240=Not Connected **/
	private String sourceToString() {
		switch (wert) {
		case 0:
			return "Available";
		case 1:
			return "Grid";
		case 2:
			return "Generator";
		case 3:
			return "Shore Power";
		case 240:
			return "Not Connected";

		}
		return "Unknown Unit!";
	}

	/** 0=Idle;1=Charging;2=Discharging **/
	private String batteryStateToString() {
		switch (wert) {
		case 0:
			return "Idle";
		case 1:
			return "Charging";
		case 2:
			return "Discharging";
		}
		return "Unknown Battery State!";
	}

	/** 0=Open;1=Closed **/
	private String relayStateToString() {
		switch (wert) {
		case 0:
			return "Open";
		case 1:
			return "Closed";
		}
		return "Unknown Relay State!";
	}

}
