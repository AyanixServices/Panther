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
package com.ayanix.panther.impl.storage.sql;

import com.ayanix.panther.storage.sql.ISQLStorage;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.sql2o.Sql2o;

import javax.sql.DataSource;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public class HikariMySQLStorage implements ISQLStorage
{

	private HikariDataSource dataSource;
	private String           table;
	private Sql2o            sql2o;

	HikariMySQLStorage(String host,
	                   int port,
	                   String username,
	                   String password,
	                   String database,
	                   String table)
	{
		this.table = table;

		HikariConfig config = new HikariDataSource();

		config.setJdbcUrl("jdbc:mysql://%host%:%port%/%database%"
				.replace("%host%", host)
				.replace("%port%", String.valueOf(port))
				.replace("%database%", database));
		config.setUsername(username);
		config.setPassword(password);

		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

		dataSource = new HikariDataSource(config);
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

	public static class HikariSQLStorageBuilder
	{

		private String host;
		private int    port;
		private String username;
		private String password;
		private String database;
		private String table;

		public HikariSQLStorageBuilder()
		{

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

		public HikariMySQLStorage build()
		{
			return new HikariMySQLStorage(host, port, username, password, database, table);
		}

	}

}
