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

import com.ayanix.panther.utils.bukkit.IBukkitPlayerUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2019.
 */
public class BukkitPlayerUtils implements IBukkitPlayerUtils
{

	private static BukkitPlayerUtils instance;

	/**
	 * Grab the static version of BukkitPermissionUtils.
	 *
	 * @return BukkitPermissionUtils.
	 */
	public static BukkitPlayerUtils get()
	{
		if (instance == null)
		{
			instance = new BukkitPlayerUtils();
		}

		return instance;
	}

	@Override
	public void clearPlayerInventory(Player player, boolean armour)
	{
		if (player == null)
		{
			throw new IllegalArgumentException("Player cannot be null");
		}

		player.getInventory().setContents(new ItemStack[0]);

		if (armour)
		{
			player.getInventory().setContents(new ItemStack[0]);
		}

		player.updateInventory();
	}

	@Override
	public void clearPlayerPotionEffects(Player player)
	{
		if (player == null)
		{
			throw new IllegalArgumentException("Player cannot be null");
		}

		player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
	}

	@Override
	public void healPlayer(Player player)
	{
		if (player == null)
		{
			throw new IllegalArgumentException("Player cannot be null");
		}

		clearPlayerPotionEffects(player);

		player.setHealth(player.getMaxHealth());
		player.setFoodLevel(20);
		player.setFireTicks(0);
	}
}