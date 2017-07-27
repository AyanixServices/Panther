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
package com.ayanix.panther.impl.gui;

import com.ayanix.panther.gui.IGUIItem;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public abstract class GUIItem implements IGUIItem
{

	private ItemStack item;

	/**
	 * @param item ItemStack representing item.
	 */
	public GUIItem(@NonNull ItemStack item)
	{
		if (item == null)
		{
			throw new IllegalArgumentException("Item cannot be null");
		}

		this.item = item;
	}

	@Override
	public ItemStack getItemStack()
	{
		return this.item;
	}

	@Override
	public void setItemStack(@NonNull ItemStack item)
	{
		if (item == null)
		{
			throw new IllegalArgumentException("Item cannot be null");
		}

		this.item = item;
	}

}
