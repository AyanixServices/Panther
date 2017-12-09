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
package com.ayanix.panther.utils.bukkit;

import org.bukkit.permissions.PermissionDefault;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public interface IBukkitPermissionUtils
{

	/**
	 * Register a permission, even if it already exists.
	 * By default, OPs have this permission.
	 * <p>
	 * If the permission already exists, it will be removed and re-registered.
	 *
	 * @param permission The permission to register.
	 */
	default void registerPermission(String permission)
	{
		registerPermission(permission, PermissionDefault.OP);
	}

	/**
	 * Register a permission, even if it already exists, with a specific default value.
	 * <p>
	 * If the permission already exists, it will be removed and re-registered.
	 *
	 * @param permission   The permission to register.
	 * @param defaultValue The default value of the permission.
	 */
	void registerPermission(String permission, PermissionDefault defaultValue);

}
