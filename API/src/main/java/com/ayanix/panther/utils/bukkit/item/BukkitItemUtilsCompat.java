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
package com.ayanix.panther.utils.bukkit.item;

import org.bukkit.inventory.meta.ItemMeta;

/**
 * Panther - Developed by Lewes D. B. (Boomclaw).
 * All rights reserved 2020.
 */
public abstract class BukkitItemUtilsCompat
{

	/**
	 * @param itemMeta The item meta to check.
	 * @return Whether or not the item is unbreakable.
	 */
	public abstract boolean isUnbreakable(ItemMeta itemMeta);

	/**
	 * Sets an item meta's unbreakable status.
	 *
	 * @param itemMeta    The item meta to set.
	 * @param unbreakable Whether or not the item should be unbreakable.
	 * @param hidden      If true, HIDE_UNBREAKABLE is also applied to the item meta. If false, nothing will change (it will not remove the flag).
	 */
	public abstract void setUnbreakable(ItemMeta itemMeta, boolean unbreakable, boolean hidden);

}
