package com.telenav.lol.dao;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.log4j.Logger;
import org.mortbay.log.Log;

/**
 * @author pingwang@telenav.cn
 *
 * $LastChangedDate$
 * $LastChangedRevision$
 * $LastChangedBy$
 */
public class HbaseAdmin {

	private static Logger logger = Logger.getLogger(HbaseAdmin.class);

	private Configuration configuration;
	private HBaseAdmin admin;
	private final Set<String> existingTableNames = new HashSet<String>();

	public synchronized void close() throws IOException {
		if (admin != null) {
			admin.close();
		}
		admin = null;
	}

	public HbaseAdmin(Configuration configuration) {
		this.configuration = configuration;
	}

	public void createTable(String tableName, String[] cfs) throws IOException {
		this.createTable(tableName, cfs, false, null, true);

	}

	public void createTable(String tableName, String[] cfs, Integer maxVersionNumber, boolean force) throws IOException {
		createTable(tableName, cfs, maxVersionNumber, force, null, true);
	}

	public void createTable(String tableName, String[] cfs, boolean force) throws IOException {
		this.createTable(tableName, cfs, force, null, true);
	}

	public void createTable(String tableName, String[] cfs, boolean force, Long maxFileSize) throws IOException {
		createTable(tableName, cfs, force, maxFileSize, true);

	}

	public void createTable(String tableName, String[] cfs, boolean force, Long maxFileSize, boolean close) throws IOException {
		createTable(tableName, cfs, null, force, maxFileSize, close);
	}

	public void deleteTable(String tableName, boolean close) throws IOException {
		try {
			getAdmin();
			admin.disableTable(tableName);
			admin.deleteTable(tableName);
			existingTableNames.remove(tableName);
		}
		catch (IOException e) {
			this.close();
			throw e;
		}
		finally {
			if (close) {
				this.close();
			}
		}
	}

	public void flush(String tableNameOrRegionName, boolean close) throws IOException, InterruptedException {
		try {
			getAdmin();
			admin.flush(tableNameOrRegionName);
		}
		catch (IOException e) {
			this.close();
			throw e;
		}
		finally {
			if (close) {
				this.close();
			}
		}

	}

	public void flush(String tableNameOrRegionName) throws IOException, InterruptedException {
		flush(tableNameOrRegionName, false);
	}

	private synchronized HBaseAdmin getAdmin() throws MasterNotRunningException, ZooKeeperConnectionException {
		if (admin == null) {
			synchronized (this) {
				if (admin == null) {
					logger.info("Opening HBase Connection");
					admin = new HBaseAdmin(configuration);
				}
			}
		}
		logger.info("HBase Connection opened");
		return admin;
	}

	/**
	 * @return the configuration
	 */
	public Configuration getConfiguration() {
		return configuration;
	}

	/**
	 * @param configuration the configuration to set
	 */
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public boolean tableExists(String tableName) throws IOException {
		return tableExists(tableName, true);
	}

	public boolean tableExists(String tableName, boolean close) throws IOException {
		if (existingTableNames.contains(tableName)) {
			return true;
		}

		try {
			getAdmin();
			if (admin.tableExists(tableName)) {
				existingTableNames.add(tableName);
				return true;
			}
			return false;
		}
		catch (IOException e) {
			this.close();
			throw e;
		}
		finally {
			if (close) {
				this.close();
			}
		}
	}

	private void createTable(String tableName, String[] cfs, Integer maxVersionNumber, boolean force, Long maxFileSize, boolean close) throws IOException {
		if (maxVersionNumber != null && maxVersionNumber < 1) {
			throw new IllegalArgumentException("Invalid maxVersion Number: " + maxVersionNumber);
		}
		try {
			getAdmin();

			Log.info("Creating table. tableName=" + tableName);
			if (tableExists(tableName, false)) {
				if (!force) {
					return;
				}
				deleteTable(tableName, false);
			}
			HTableDescriptor tableDesc = new HTableDescriptor(tableName);

			for (int i = 0; i < cfs.length; i++) {
				HColumnDescriptor hcDes = new HColumnDescriptor(cfs[i]);
				if (maxVersionNumber != null) {
					hcDes.setMaxVersions(maxVersionNumber);
				}
				tableDesc.addFamily(hcDes);
			}

			if (maxFileSize != null) {
				tableDesc.setMaxFileSize(maxFileSize);
			}
			admin.createTable(tableDesc);
			existingTableNames.add(tableName);

			Log.info("Table created. tableName=" + tableName);
		}
		catch (IOException e) {
			this.close();
			throw e;
		}
		finally {
			if (close) {
				this.close();
			}
		}
	}

	public void createTable(String tableName, String familyName, boolean needForce, byte[][] splits, boolean needClose) throws IOException {
		try {
			getAdmin();
			logger.info("Creating table. tableName=" + tableName);
			if (tableExists(tableName, false)) {
				if (!needForce) {
					return;
				}
				deleteTable(tableName, false);
			}
			HTableDescriptor tableDesc = new HTableDescriptor(tableName);
			HColumnDescriptor family = new HColumnDescriptor(familyName);
			tableDesc.addFamily(family);
			admin.createTable(tableDesc, splits);
		}
		finally {
			if (needClose) {
				this.close();
			}
		}

	}
}
