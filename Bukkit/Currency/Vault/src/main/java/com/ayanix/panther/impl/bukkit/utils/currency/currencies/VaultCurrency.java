package com.ayanix.panther.impl.bukkit.utils.currency.currencies;

import com.ayanix.panther.utils.bukkit.currency.BukkitCurrency;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.UUID;

/**
 * PantherParent - Developed by Lewes D. B. (Boomclaw).
 * All rights reserved 2020.
 */
public class VaultCurrency extends BukkitCurrency
{

	private Economy economy;

	public VaultCurrency()
	{
		try
		{
			hook();
		} catch (Exception e)
		{
			// Vault is not available.
		}
	}

	/**
	 * Hook into Vault's economy.
	 */
	private void hook()
	{
		RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);

		if (economyProvider != null)
		{
			economy = economyProvider.getProvider();
		}
	}

	@Override
	public String getPluginName()
	{
		return "Vault";
	}

	@Override
	public boolean isEnabled()
	{
		return super.isEnabled() && economy != null;
	}

	@Override
	public boolean withdraw(UUID uuid, double amount)
	{
		return economy.withdrawPlayer(Bukkit.getOfflinePlayer(uuid), amount).transactionSuccess();
	}

	@Override
	public boolean deposit(UUID uuid, double amount)
	{
		return economy.depositPlayer(Bukkit.getOfflinePlayer(uuid), amount).transactionSuccess();
	}

	@Override
	public boolean set(UUID uuid, double amount)
	{
		OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);

		return economy.withdrawPlayer(offlinePlayer, getBalance(uuid)).transactionSuccess() && economy.depositPlayer(offlinePlayer, amount).transactionSuccess();
	}

	@Override
	public double getBalance(UUID uuid)
	{
		return economy.getBalance(Bukkit.getOfflinePlayer(uuid));
	}

}