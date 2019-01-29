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
package com.ayanix.panther.enchantment.bukkit;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public interface PantherEnchantment
{

	/**
	 * @return The display name of the enchantment.
	 */
	String getDisplayName();

	/**
	 * @return The minimum level of the enchantment, usually 1.
	 */
	int getStartLevel();

	/**
	 * @return The maximum level of the enchantment.
	 */
	int getMaxLevel();

	/**
	 * @return The enchantable materials.
	 */
	List<Material> getEnchantable();

	/**
	 * @param item The item which wishes to be enchanted.
	 * @return Whether or not the item can be enchanted.
	 */
	boolean canEnchantItem(ItemStack item);

	/**
	 * @param item  The item to be enchanted.
	 * @param level The level of enchantment to apply.
	 */
	void apply(ItemStack item, int level);

	/**
	 * @param item The item to check.
	 * @return The level of the enchantment applied to item, can be 0 if no enchantment.
	 */
	int getLevel(ItemStack item);

	/**
	 * @param item The item to check.
	 * @return Whether or not the item has the specific enchantment.
	 */
	boolean isEnchanted(ItemStack item);

	boolean conflictsWith(Enchantment enchantment);

	String getName();

	EnchantmentTarget getItemTarget();

	/**
	 * @return The Bukkit Enchantment related to the PantherEnchantment.
	 */
	Enchantment getBukkitEnchantment();

}