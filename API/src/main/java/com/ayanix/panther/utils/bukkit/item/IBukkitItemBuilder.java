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

import org.bukkit.Color;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public interface IBukkitItemBuilder
{

	/**
	 * Set the amount of the item.
	 *
	 * @param amount The amount to set, cannot be above 64 or below 1.
	 * @return The ItemBuilder object.
	 */
	IBukkitItemBuilder amount(int amount);

	/**
	 * Set the data (durability) of the item.
	 *
	 * @param data The data to set.
	 * @return The ItemBuilder object.
	 */
	IBukkitItemBuilder data(short data);

	/**
	 * Set the name of the item.
	 * This can be unformatted or formatted; Panther will format it.
	 *
	 * @param name The name to set.
	 * @return The ItemBuilder object.
	 */
	IBukkitItemBuilder name(String name);

	/**
	 * Set the lore of the item.
	 * This can be unformatted or formatted; Panther will format it.
	 *
	 * @param lore The lore to set.
	 * @return The ItemBuilder object.
	 */
	IBukkitItemBuilder lore(List<String> lore);

	/**
	 * Apply an enchantment to the item with the specified level.
	 *
	 * @param enchantment An enchantment to apply.
	 * @param level       The level to apply, cannot be 0 or below.
	 * @return The ItemBuilder object.
	 */
	IBukkitItemBuilder enchant(Enchantment enchantment, int level);

	/**
	 * Set the colour of the item.
	 * This only applies to leather armour, will fail silently otherwise.
	 *
	 * @param color The color to set.
	 * @return The ItemBuilder object.
	 */
	IBukkitItemBuilder color(Color color);

	/**
	 * Build the finished version of the item.
	 *
	 * @return The ItemStack.
	 */
	ItemStack build();

}