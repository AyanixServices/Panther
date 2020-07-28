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

import com.ayanix.panther.utils.bukkit.IBukkitVaultEconomyUtils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2019.
 */
public class BukkitVaultEconomyUtils implements IBukkitVaultEconomyUtils
{

	private static BukkitVaultEconomyUtils instance;
	private        Economy                 economy;

	public BukkitVaultEconomyUtils()
	{
		RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);

		if (economyProvider != null)
		{
			economy = economyProvider.getProvider();
		}
	}

	/**
	 * Grab the static version of BukkitVaultEconomyUtils.
	 *
	 * @return BukkitVaultEconomyUtils.
	 */
	public static BukkitVaultEconomyUtils get()
	{
		if (instance == null)
		{
			instance = new BukkitVaultEconomyUtils();
		}

		return instance;
	}

	@Override
	public boolean isEnabled()
	{
		return economy != null;
	}

	@Override
	public boolean deposit(OfflinePlayer player, double amount)
	{
		return economy.depositPlayer(player, amount).transactionSuccess();
	}

	@Override
	public boolean withdraw(OfflinePlayer player, double amount)
	{
		return economy.withdrawPlayer(player, amount).transactionSuccess();
	}

	@Override
	public boolean set(OfflinePlayer player, double amount)
	{
		return economy.withdrawPlayer(player, getBalance(player)).transactionSuccess() && economy.depositPlayer(player, amount).transactionSuccess();
	}

	@Override
	public double getBalance(OfflinePlayer player)
	{
		return economy.getBalance(player);
	}

}