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

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public interface InventoryGUI
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
	void insert(int slot, GUIItem item);

	/**
	 * Inserts an item into the GUI with no action.
	 *
	 * @param slot The corresponding slot, starting from 0.
	 * @param item The ItemStack.
	 */
	void insert(int slot, ItemStack item);

	/**
	 * Remove all items from the GUI.
	 */
	void clear();

	/**
	 * Insert all GUIItems into the Bukkit inventory.
	 */
	void refresh();

	/**
	 * Re-inserts a specific GUIItem in the slot into the Bukkit inventory.
	 */
	void refresh(int slot);

	/**
	 * Open the inventory.
	 */
	void open();

	/**
	 * @return Whether or not the inventory is allowed to be closed.
	 */
	boolean canClose();

	/**
	 * @return Whether or not the inventory has been closed.
	 */
	boolean isClosed();

	/**
	 * Fetch the GUIItem in the specified slot.
	 *
	 * @param slot The slot containing the GUIItem (starting from 0)
	 * @return An optional containing the GUIItem in the slot, or an empty optional.
	 */
	Optional<GUIItem> getItem(int slot);

}