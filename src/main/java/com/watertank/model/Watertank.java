package com.watertank.model;

/**
 * Main application model/entity
 * @author Hoffman
 *
 */
public  class Watertank {

		private Integer id;
		private double maxCapacity;
		private double currentCapacity;
		private int waterLeakRate;
		private double literOfLeak;

		public Watertank() {

		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public double getMaxCapacity() {
			return maxCapacity;
		}

		public void setMaxCapacity(double maxCapacity) {
			this.maxCapacity = maxCapacity;
		}

		public double getCurrentCapacity() {
			return currentCapacity;
		}

		public void setCurrentCapacity(double currentCapacity) {
			this.currentCapacity = currentCapacity;
		}

		public int getWaterLeakRate() {
			return waterLeakRate;
		}

		public void setWaterLeakRate(int waterLeakRate) {
			this.waterLeakRate = waterLeakRate;
		}

		public double getLiterOfLeak() {
			return literOfLeak;
		}

		public void setLiterOfLeak(double literOfLeak) {
			this.literOfLeak = literOfLeak;
		}

		@Override
		public String toString() {
			return "Watertank [id=" + id + ", maxCapacity=" + maxCapacity + ", currentCapacity=" + currentCapacity + ", waterLeakRate=" + waterLeakRate + ", literOfLeak=" + literOfLeak + "]";
		}

	}