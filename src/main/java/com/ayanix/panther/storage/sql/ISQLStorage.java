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
package com.ayanix.panther.storage.sql;

import org.sql2o.Sql2o;

import javax.sql.DataSource;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public interface ISQLStorage
{

	/**
	 * Get the data source corresponding with the storage.
	 *
	 * @return The data source.
	 */
	DataSource getDataSource();

	/**
	 * Get the Sql2o object associated with the storage.
	 *
	 * @return The sql2o object.
	 */
	Sql2o getSQL();

	/**
	 * Disconnect from data source and initiate shutdown.
	 */
	void disconnect();

	/**
	 * Get the table prefix or table name associated with storage.
	 *
	 * @return The table prefix or name.
	 */
	String getTable();

}
