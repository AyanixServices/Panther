package com.ayanix.panther.impl.bukkit.utils.currency.currencies;

import com.ayanix.panther.utils.bukkit.currency.BukkitCurrency;
import org.black_ixx.playerpoints.PlayerPoints;
import org.bukkit.Bukkit;

import java.util.UUID;

/**
 * Panther - Developed by Lewes D. B. (Boomclaw).
 * All rights reserved 2020.
 */
public class PlayerPointsCurrency extends BukkitCurrency
{

	@Override
	public boolean withdraw(UUID uuid, double amount)
	{
		if (!Double.isFinite(amount)) {
			return false;
		}

		return getPlayerPoints().getAPI().take(uuid, (int) amount);
	}

	private PlayerPoints getPlayerPoints()
	{
		return (PlayerPoints) Bukkit.getPluginManager().getPlugin(getPluginName());
	}

	@Override
	public String getPluginName()
	{
		return "PlayerPoints";
	}

	@Override
	public boolean deposit(UUID uuid, double amount)
	{
		if (!Double.isFinite(amount)) {
			return false;
		}

		return getPlayerPoints().getAPI().give(uuid, (int) amount);
	}

	@Override
	public boolean set(UUID uuid, double amount)
	{
		if (!Double.isFinite(amount)) {
			return false;
		}

		return getPlayerPoints().getAPI().set(uuid, (int) amount);
	}

	@Override
	public double getBalance(UUID uuid)
	{
		return getPlayerPoints().getAPI().look(uuid);
	}

}
