/*
 *                                                            _...---.._
 *                                                        _.'`       -_ ``.
 *                                                    .-'`                 `.
 *                                                 .-`                     q ;
 *                                              _-`                       __  \
 *                                          .-'`                  . ' .   \ `;/
 *                                      _.-`                    /.      `._`/
 *                              _...--'`                        \_`..._
 *                           .'`                         -         `'--:._
 *                        .-`                           \                  `-.
 *                       .                               `-..__.....----...., `.
 *                      '                   `'''---..-''`'              : :  : :
 *                    .` -                '``                           `'   `'
 *                 .-` .` '             .``
 *             _.-` .-`   '            .
 *         _.-` _.-`    .' '         .`
 * (`''--'' _.-`      .'  '        .'
 *  `'----''        .'  .`       .`
 *                .'  .'     .-'`    _____               _    _
 *              .'   :    .-`       |  __ \             | |  | |
 *              `. .`   ,`          | |__) |__ _  _ __  | |_ | |__    ___  _ __
 *               .'   .'            |  ___// _` || '_ \ | __|| '_ \  / _ \| '__|
 *              '   .`              | |   | (_| || | | || |_ | | | ||  __/| |
 *             '  .`                |_|    \__,_||_| |_| \__||_| |_| \___||_|
 *             `  '.
 *             `.___;
 */
package com.ayanix.panther.impl.common.storage.sql;

import com.ayanix.panther.storage.sql.SQLStorage;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.sql2o.Sql2o;
import org.sql2o.quirks.Quirks;
import org.sql2o.quirks.QuirksDetector;

import javax.sql.DataSource;
import java.util.concurrent.ThreadFactory;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public class HikariMySQLStorage implements SQLStorage
{

	private HikariDataSource dataSource;
	private String           table;
	private Sql2o            sql2o;

	HikariMySQLStorage(String host,
	                   int port,
	                   String username,
	                   String password,
	                   String database,
	                   String table,
	                   Quirks quirks,
	                   ThreadFactory factory,
	                   int maxPoolSize)
	{
		if (host == null ||
				username == null ||
				password == null ||
				table == null ||
				database == null)
		{
			throw new IllegalArgumentException("Parameters cannot be null");
		}

		this.table = table;

		HikariConfig config = new HikariConfig();

		config.setJdbcUrl("jdbc:mysql://%host%:%port%/%database%"
				.replace("%host%", host)
				.replace("%port%", String.valueOf(port))
				.replace("%database%", database));
		config.setUsername(username);
		config.setPassword(password);

		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		config.setMaximumPoolSize(maxPoolSize);

		if (factory != null)
		{
			config.setThreadFactory(factory);
		}

		dataSource = new HikariDataSource(config);

		if (quirks == null) {
			quirks = QuirksDetector.forObject(dataSource);
		}

		sql2o = new Sql2o(dataSource, quirks);
	}

	HikariMySQLStorage(HikariDataSource dataSource, String table)
	{
		this.dataSource = dataSource;
		this.table = table;

		sql2o = new Sql2o(dataSource);
	}

	@Override
	public DataSource getDataSource()
	{
		return this.dataSource;
	}

	@Override
	public Sql2o getSQL()
	{
		return this.sql2o;
	}

	@Override
	public void disconnect()
	{
		dataSource.close();
	}

	@Override
	public String getTable()
	{
		return this.table;
	}

	@SuppressWarnings({"uninitialised", "incompatible"})
	public static class HikariSQLStorageBuilder
	{

		private String        host;
		private int           port;
		private String        username;
		private String        password;
		private String        database;
		private String        table;
		private Quirks        quirks;
		private ThreadFactory factory;
		private int           maxPoolSize;

		public HikariSQLStorageBuilder()
		{
			/* Default parameters for testing */
			this.host = "localhost";
			this.port = 3306;
			this.username = "root";
			this.database = "minecraft";
			this.table = "minecraft";
			this.quirks = null;
			this.factory = null;
			this.maxPoolSize = 10;
		}

		public HikariSQLStorageBuilder host(String host)
		{
			this.host = host;

			return this;
		}

		public HikariSQLStorageBuilder port(int port)
		{
			this.port = port;

			return this;
		}

		public HikariSQLStorageBuilder username(String username)
		{
			this.username = username;

			return this;
		}

		public HikariSQLStorageBuilder password(String password)
		{
			this.password = password;

			return this;
		}

		public HikariSQLStorageBuilder database(String database)
		{
			this.database = database;

			return this;
		}

		public HikariSQLStorageBuilder table(String table)
		{
			this.table = table;

			return this;
		}

		public HikariSQLStorageBuilder factory(ThreadFactory factory)
		{
			this.factory = factory;

			return this;
		}

		public HikariSQLStorageBuilder quirks(Quirks quirks)
		{
			this.quirks = quirks;

			return this;
		}

		public HikariSQLStorageBuilder maxPoolSize(int maxPoolSize)
		{
			this.maxPoolSize = maxPoolSize;

			return this;
		}

		public HikariMySQLStorage build()
		{
			if (password == null)
			{
				throw new IllegalArgumentException("Password is not defined");
			}

			return new HikariMySQLStorage(host, port, username, password, database, table, quirks, factory, maxPoolSize);
		}

	}

}
