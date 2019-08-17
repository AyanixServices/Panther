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

import org.bukkit.entity.Player;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2019.
 */
public interface IBukkitPlayerUtils
{

	/**
	 * Gets a string version of a location.
	 *
	 * @param player The player whose inventory will be cleared.
	 * @param armour If true, also clears armour.
	 */
	void clearPlayerInventory(Player player, boolean armour);

	/**
	 * Clears all active potion effects from the player.
	 *
	 * @param player The player whose potion effects will be cleared.
	 */
	void clearPlayerPotionEffects(Player player);

	/**
	 * Heals players to full health, clears all active potion effects and full hunger.
	 *
	 * @param player The layer to heal.
	 */
	void healPlayer(Player player);

}
