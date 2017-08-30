package com.iot.bean;

import java.sql.Timestamp;

import com.iot.base.BaseEntity;

/**
 * BeaconFramesId entity. @author MyEclipse Persistence Tools
 */

public class BeaconFrames extends BaseEntity {

	// Fields
	private String sourceMacAddress;
	private String frameId;
	private String timeReceived;
	private String rssiAtReceiving;
	private String payload;
	private String apMacAddress;
	private String uniqueIndicator;
	private int isProcessed;
	private Timestamp cloudReceivedTime;
	private long uniqueIndicatorPerSourceAp;
	private long uniqueIndicatorPerSource;
	private long time_received_db;

	// Constructors

	/** default constructor */
	public BeaconFrames() {
	}

	/** full constructor */
	public BeaconFrames(String sourceMacAddress, String frameId,
			String timeReceived, String rssiAtReceiving, String payload,
			String apMacAddress, String uniqueIndicator, int isProcessed,
			Timestamp cloudReceivedTime) {
		this.sourceMacAddress = sourceMacAddress;
		this.frameId = frameId;
		this.timeReceived = timeReceived;
		this.rssiAtReceiving = rssiAtReceiving;
		this.payload = payload;
		this.apMacAddress = apMacAddress;
		this.uniqueIndicator = uniqueIndicator;
		this.isProcessed = isProcessed;
		this.cloudReceivedTime = cloudReceivedTime;
	}

	// Property accessors

	public String getSourceMacAddress() {
		return this.sourceMacAddress;
	}

	public void setSourceMacAddress(String sourceMacAddress) {
		this.sourceMacAddress = sourceMacAddress;
	}

	public String getFrameId() {
		return this.frameId;
	}

	public void setFrameId(String frameId) {
		this.frameId = frameId;
	}

	public String getTimeReceived() {
		return this.timeReceived;
	}

	public void setTimeReceived(String timeReceived) {
		this.timeReceived = timeReceived;
	}

	public String getRssiAtReceiving() {
		return this.rssiAtReceiving;
	}

	public void setRssiAtReceiving(String rssiAtReceiving) {
		this.rssiAtReceiving = rssiAtReceiving;
	}

	public String getPayload() {
		return this.payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public String getApMacAddress() {
		return this.apMacAddress;
	}

	public void setApMacAddress(String apMacAddress) {
		this.apMacAddress = apMacAddress;
	}

	public String getUniqueIndicator() {
		return this.uniqueIndicator;
	}

	public void setUniqueIndicator(String uniqueIndicator) {
		this.uniqueIndicator = uniqueIndicator;
	}

	public int getIsProcessed() {
		return this.isProcessed;
	}

	public void setIsProcessed(int isProcessed) {
		this.isProcessed = isProcessed;
	}

	public Timestamp getCloudReceivedTime() {
		return this.cloudReceivedTime;
	}

	public void setCloudReceivedTime(Timestamp cloudReceivedTime) {
		this.cloudReceivedTime = cloudReceivedTime;
	}

	public long getUniqueIndicatorPerSourceAp() {
		return uniqueIndicatorPerSourceAp;
	}

	public void setUniqueIndicatorPerSourceAp(long uniqueIndicatorPerSourceAp) {
		this.uniqueIndicatorPerSourceAp = uniqueIndicatorPerSourceAp;
	}

	public long getUniqueIndicatorPerSource() {
		return uniqueIndicatorPerSource;
	}

	public void setUniqueIndicatorPerSource(long uniqueIndicatorPerSource) {
		this.uniqueIndicatorPerSource = uniqueIndicatorPerSource;
	}

	public long getTime_received_db() {
		return time_received_db;
	}

	public void setTime_received_db(long time_received_db) {
		this.time_received_db = time_received_db;
	}

}