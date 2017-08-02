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
package com.ayanix.panther.utils.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public interface IItemUtils
{

	/**
	 * Return whether or not the given string is a material.
	 * This is case-sensitive.
	 *
	 * @param materialName Material name.
	 * @return If true, its a material.
	 */
	boolean isMaterial(@Nullable String materialName);

	/**
	 * Converts an ItemStack into a string for saving.
	 *
	 * @param item The item to generate as String.
	 * @return A string compressed version of item.
	 */
	String itemToString(@Nullable ItemStack item);

	/**
	 * Converts a compressed item string into an ItemStack.
	 *
	 * @param item The string version of the item.
	 * @return An ItemStack.
	 */
	ItemStack stringToItem(@Nullable String item);

	/**
	 * Checks whether or not the items match.
	 * This only applies to names, types, data and lores.
	 *
	 * @param itemA The first item to check.
	 * @param itemB The second item to compare with.
	 */
	boolean areItemsEqual(@Nullable ItemStack itemA, @Nullable ItemStack itemB);

	/**
	 * Get materials which contain the keywords, not case-sensitive.
	 *
	 * @param str The strings which identify the materials.
	 * @return A list of materials which match.
	 */
	List<Material> getMaterialsContaining(String... str);

}