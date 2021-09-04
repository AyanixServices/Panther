package com.ayanix.panther.impl.bukkit.utils.currency.currencies;

import com.ayanix.panther.utils.bukkit.currency.BukkitCurrency;
import com.vk2gpz.tokenenchant.api.TokenEnchantAPI;
import org.bukkit.Bukkit;

import java.util.UUID;

/**
 * Panther - Developed by Lewes D. B. (Boomclaw).
 * All rights reserved 2020.
 */
public class TokenEnchantCurrency extends BukkitCurrency
{

	@Override
	public String getPluginName()
	{
		return "TokenEnchant";
	}

	@Override
	public boolean withdraw(UUID uuid, double amount)
	{
		if (!Double.isFinite(amount)) {
			return false;
		}

		TokenEnchantAPI.getInstance().removeTokens(Bukkit.getOfflinePlayer(uuid), amount);

		return true;
	}

	@Override
	public boolean deposit(UUID uuid, double amount)
	{
		if (!Double.isFinite(amount)) {
			return false;
		}

		TokenEnchantAPI.getInstance().addTokens(Bukkit.getOfflinePlayer(uuid), amount);

		return true;
	}

	@Override
	public boolean set(UUID uuid, double amount)
	{
		if (!Double.isFinite(amount)) {
			return false;
		}

		TokenEnchantAPI.getInstance().setTokens(Bukkit.getOfflinePlayer(uuid), amount);

		return true;
	}

	@Override
	public double getBalance(UUID uuid)
	{
		return TokenEnchantAPI.getInstance().getTokens(Bukkit.getOfflinePlayer(uuid));
	}

}