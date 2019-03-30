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
package com.ayanix.panther.impl.bukkit.utils;

import com.ayanix.panther.utils.bukkit.IBukkitPermissionUtils;
import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public class BukkitPermissionUtils implements IBukkitPermissionUtils
{

	private static BukkitPermissionUtils instance;

	/**
	 * Grab the static version of BukkitPermissionUtils.
	 *
	 * @return BukkitPermissionUtils.
	 */
	public static BukkitPermissionUtils get()
	{
		if (instance == null)
		{
			instance = new BukkitPermissionUtils();
		}

		return instance;
	}

	@Override
	public void registerPermission(String permission, PermissionDefault defaultValue)
	{
		if (permission == null)
		{
			return;
		}

		if (Bukkit.getPluginManager().getPermission(permission) != null)
		{
			Bukkit.getPluginManager().removePermission(permission);
		}

		Permission bukkitPermission = new Permission(permission, defaultValue);

		Bukkit.getPluginManager().addPermission(bukkitPermission);
	}

}