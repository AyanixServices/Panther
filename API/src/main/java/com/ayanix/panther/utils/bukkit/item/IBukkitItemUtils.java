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

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.function.Consumer;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public interface IBukkitItemUtils
{

	/**
	 * Return whether or not the given string is a material.
	 * This is case-sensitive.
	 *
	 * @param materialName Material name.
	 * @return If true, its a material.
	 */
	boolean isMaterial(String materialName);

	/**
	 * Converts an ItemStack into a string for saving.
	 *
	 * @param item The item to generate as String.
	 * @return A string compressed version of item.
	 */
	String itemToString(ItemStack item);

	/**
	 * Converts a list of ItemStacks into a list of converted strings in order.
	 * If an item cannot be converted, it will be replaced by air.
	 *
	 * @param list The list of items to convert to strings.
	 * @return A list of converted string compressed versions.
	 */
	List<String> itemsToStrings(List<ItemStack> list);

	/**
	 * Converts a compressed item string into an ItemStack.
	 *
	 * @param item The string version of the item.
	 * @return An ItemStack.
	 */
	ItemStack stringToItem(String item);

	/**
	 * Converts a compressed item string into an ItemStack.
	 * Additionally, it will convert another compressed item string on an async thread
	 * and return the ItemStack version on the primary thread once converted.
	 * <p>
	 * This is useful for skulls which, if not loaded, have to be called on the main thread and can
	 * cause server lag spikes.
	 *
	 * @param item      The string version of the item.
	 * @param asyncItem The string version of the item to be returned in the consumer.
	 * @param consumer  The consumer which will deal with the converted async item.
	 * @return An ItemStack.
	 */
	ItemStack stringToItem(String item, String asyncItem, Consumer<ItemStack> consumer);

	/**
	 * Converts a list of compressed item strings into a list of ItemStacks in order.
	 * If an item cannot be converted, air is returned.
	 *
	 * @param list The list of string versions to convert to items.
	 * @return A list of ItemStacks.
	 */
	List<ItemStack> stringsToItems(List<String> list);

	/**
	 * Checks whether or not the items match.
	 * This only applies to names, types, data and lores.
	 * <p>
	 * If the item is a player skull, the owners are compared.
	 *
	 * @param itemA The first item to check.
	 * @param itemB The second item to compare with.
	 * @return Whether or not the items are equal.
	 */
	default boolean areItemsEqual(ItemStack itemA, ItemStack itemB)
	{
		return areItemsEqual(itemA, itemB, null, null);
	}

	/**
	 * Checks whether or not the items match.
	 * This only applies to names, types, data and lores.
	 * <p>
	 * If the item is a player skull, the owners are compared.
	 * <p>
	 * metaA and metaB are passed for performance reasons as fetching an item's ItemMeta may have performance impacts on some Minecraft versions.
	 *
	 * @param itemA The first item to check.
	 * @param itemB The second item to compare with.
	 * @param metaA The first item's meta, if already fetched. If null, Panther will fetch the meta itself.
	 * @param metaB The second item's meta, if already fetched. If null, Panther will fetch the meta itself.
	 * @return Whether or not the items are equal.
	 */
	boolean areItemsEqual(ItemStack itemA, ItemStack itemB, ItemMeta metaA, ItemMeta metaB);

	/**
	 * Get materials which contain the keywords, not case-sensitive.
	 *
	 * @param str The strings which identify the materials.
	 * @return A list of materials which match.
	 */
	List<Material> getMaterialsContaining(String... str);

	/**
	 * @param itemMeta The item meta to check.
	 * @return Whether or not the item is unbreakable.
	 */
	boolean isUnbreakable(ItemMeta itemMeta);

	/**
	 * Sets an item meta's unbreakable status.
	 *
	 * @param itemMeta    The item meta to set.
	 * @param unbreakable Whether or not the item should be unbreakable.
	 */
	default void setUnbreakable(ItemMeta itemMeta, boolean unbreakable)
	{
		setUnbreakable(itemMeta, unbreakable, false);
	}

	/**
	 * Sets an item meta's unbreakable status.
	 *
	 * @param itemMeta    The item meta to set.
	 * @param unbreakable Whether or not the item should be unbreakable.
	 * @param hidden      If true, HIDE_UNBREAKABLE is also applied to the item meta. If false, nothing will change (it will not remove the flag).
	 */
	void setUnbreakable(ItemMeta itemMeta, boolean unbreakable, boolean hidden);

	/**
	 * Shutdown the executor service.
 	 */
	void shutdown();

	boolean isSkullCached(String playerName);

}