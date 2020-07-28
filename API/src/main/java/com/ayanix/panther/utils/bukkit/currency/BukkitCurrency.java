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
package com.ayanix.panther.utils.bukkit.currency;

import org.bukkit.Bukkit;

import java.util.UUID;

/**
 * Panther - Developed by Lewes D. B. (Boomclaw).
 * All rights reserved 2020.
 */
public abstract class BukkitCurrency
{

	/**
	 * @return The name of the currency.
	 */
	public String getName()
	{
		return getPluginName();
	}

	/**
	 * @return The name of the plugin which needs to be enabled for the currency to function.
	 */
	public abstract String getPluginName();

	/**
	 * @return Whether or not the currency is enabled.
	 */
	public boolean isEnabled()
	{
		return Bukkit.getPluginManager().isPluginEnabled(getPluginName());
	}

	/**
	 * Withdraw an amount from the player's balance.
	 *
	 * @param uuid   The UUID of the player.
	 * @param amount The amount to withdraw.
	 * @return Whether or not the transaction was successful. For some plugins, this will always be true as transaction result is not returned.
	 */
	public abstract boolean withdraw(UUID uuid, double amount);

	/**
	 * Deposit an amount into the player's balance.
	 *
	 * @param uuid   The UUID of the player.
	 * @param amount The amount to deposit.
	 * @return Whether or not the transaction was successful. For some plugins, this will always be true as transaction result is not returned.
	 */
	public abstract boolean deposit(UUID uuid, double amount);

	/**
	 * Set the player's balance to a specified amount.
	 *
	 * @param uuid   The UUID of the player.
	 * @param amount The new balance.
	 * @return If transaction was successful.
	 */
	public abstract boolean set(UUID uuid, double amount);

	/**
	 * @param uuid The UUID of the player.
	 * @return The balance of the player. It will return 0 if they do not have an account if the currency plugin supports it.
	 */
	public abstract double getBalance(UUID uuid);

	/**
	 * @param uuid   The UUID of the player.
	 * @param amount The minimum balance the player must have.
	 * @return Whether or not the player has enough balance to cover this amount.
	 */
	public boolean canAfford(UUID uuid, double amount)
	{
		return getBalance(uuid) >= amount;
	}

}
