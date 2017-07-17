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
package com.ayanix.panther.gui;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public interface IInventoryGUI
{

	/**
	 * An inventory type can be CHEST, HOPPER etc.
	 *
	 * @return The inventory type of the GUI.
	 */
	InventoryType getInventoryType();

	/**
	 * @return The number of rows.
	 */
	int getRows();

	/**
	 * x9 of rows mostly however not at all times - anvils do not have rows.
	 *
	 * @return The number of slots.
	 */
	int getSlots();

	/**
	 * @return The name of the inventory.
	 */
	String getName();

	/**
	 * Inserts an item into the GUI.
	 *
	 * @param slot The corresponding slot, starting from 0.
	 * @param item The GUIItem with action and ItemStack.
	 */
	void insert(int slot, @NonNull IGUIItem item);

	/**
	 * Inserts an item into the GUI with no action.
	 *
	 * @param slot The corresponding slot, starting from 0.
	 * @param item The ItemStack.
	 */
	void insert(int slot, @NonNull ItemStack item);

	/**
	 * Remove all items from the GUI.
	 */
	void clear();

	/**
	 * Insert all GUIItems into the Bukkit inventory.
	 */
	void refresh();

	/**
	 * Open the inventory.
	 */
	void open();

}