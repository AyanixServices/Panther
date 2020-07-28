package com.ayanix.panther.impl.bukkit.utils.currency;

import com.ayanix.panther.impl.bukkit.utils.currency.currencies.PlayerPointsCurrency;
import com.ayanix.panther.impl.bukkit.utils.currency.currencies.TokenEnchantCurrency;
import com.ayanix.panther.impl.bukkit.utils.currency.currencies.VaultCurrency;
import com.ayanix.panther.utils.bukkit.currency.BukkitCurrency;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * PantherParent - Developed by Lewes D. B. (Boomclaw).
 * All rights reserved 2020.
 */
public class BukkitCurrencyUtils
{

	private static BukkitCurrencyUtils instance;
	private final  Set<BukkitCurrency> currencies;

	public BukkitCurrencyUtils()
	{
		this.currencies = new HashSet<>();

		loadCurrencies();
	}

	/**
	 * Loads all currencies into the utility.
	 */
	public void loadCurrencies()
	{
		this.currencies.add(new VaultCurrency());
		this.currencies.add(new TokenEnchantCurrency());
		this.currencies.add(new PlayerPointsCurrency());
	}

	/**
	 * Grab the static version of BukkitCurrencyUtils.
	 *
	 * @return BukkitDependencyChecks.
	 */
	public static BukkitCurrencyUtils get()
	{
		if (instance == null)
		{
			instance = new BukkitCurrencyUtils();
		}

		return instance;
	}

	/**
	 * @param name The name of the currency, it is case-insensitive.
	 * @return An optional of an enabled currency. If the currency does not exist or is not enabled, an empty Optional is returned.
	 */
	public Optional<BukkitCurrency> getCurrency(String name)
	{
		return currencies.stream()
				.filter(com.ayanix.panther.utils.bukkit.currency.BukkitCurrency::isEnabled)
				.filter(currency -> currency.getName().equalsIgnoreCase(name))
				.findFirst();
	}

}