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
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.RegisteredServiceProvider;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public class BukkitPermissionUtils implements IBukkitPermissionUtils
{

	private static BukkitPermissionUtils instance;
	private BukkitVaultPermissionUtils vaultPermissionUtils = null;

	public BukkitPermissionUtils() {
		// not outputting to logger therefore null plugin is acceptable.
		if(new BukkitDependencyChecks(null).isEnabled("Vault")) {
			vaultPermissionUtils = new BukkitVaultPermissionUtils();

			if(!vaultPermissionUtils.isEnabled()) {
				// remove support, permission is not setup even though Vault is enabled.
				vaultPermissionUtils = null;
			}
		}
	}

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

	@Override
	public boolean hasPermission(OfflinePlayer player, String permission)
	{
		if(vaultPermissionUtils == null) {
			return false;
		}

		return vaultPermissionUtils.getPermission().playerHas(null, player, permission);
	}

	private class BukkitVaultPermissionUtils {

		private net.milkbowl.vault.permission.Permission permission;

		BukkitVaultPermissionUtils() {
			RegisteredServiceProvider<net.milkbowl.vault.permission.Permission> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);

			if (economyProvider != null)
			{
				permission = economyProvider.getProvider();
			}
		}

		public boolean isEnabled() {
			return permission != null;
		}

		public net.milkbowl.vault.permission.Permission getPermission() {
			return permission;
		}

	}

}