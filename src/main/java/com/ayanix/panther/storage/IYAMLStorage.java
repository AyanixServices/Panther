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
package com.ayanix.panther.storage;

import org.bukkit.configuration.file.YamlConfiguration;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.File;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public interface IYAMLStorage
{

	/**
	 * Save configuration to file.
	 */
	void save();

	/**
	 * Get the corresponding file.
	 *
	 * @return The file the storage is saved and loaded from.
	 */
	File getFile();

	/**
	 * The YamlConfiguration config.
	 *
	 * @return The configuration.
	 */
	YamlConfiguration getConfig();

	/**
	 * Insert default configuration to replace missing values.
	 *
	 * @param storage Default configuration.
	 */
	void insertDefault(@NonNull IDefaultStorage defaultStorage);

}
