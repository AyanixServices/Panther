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
package com.ayanix.panther.gui.bukkit;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public interface GUIDragEvent
{

	/**
	 * Method ran when item is dragged into slot.
	 *
	 * @param player    Player who clicked the item.
	 * @param itemStack The item dragged in.
	 * @return If drag should be allowed to go ahead.
	 */
	boolean onInsert(Player player, ItemStack itemStack);

	/**
	 * Method ran when item is dragged out of the slot.
	 *
	 * @param player    Player who clicked on the item.
	 * @param itemStack The item being dragged out.
	 * @return
	 */
	boolean onRemove(Player player, ItemStack itemStack);

	/**
	 * Method ran when item's amount is updated.
	 *
	 * @param player    Player who clicked on the item.
	 * @param itemStack The item with the new amount.
	 * @return
	 */
	boolean onUpdate(Player player, ItemStack itemStack);

}