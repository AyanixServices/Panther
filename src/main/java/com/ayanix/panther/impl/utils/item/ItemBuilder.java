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
package com.ayanix.panther.impl.utils.item;

import com.ayanix.panther.utils.item.IItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public class ItemBuilder implements IItemBuilder
{

	private Material                      material;
	private int                           amount;
	private short                         data;
	private String                        name;
	private List<String>                  lore;
	private HashMap<Enchantment, Integer> enchants;
	private Color                         color;

	public ItemBuilder(Material material)
	{
		this.material = material;
		this.amount = 1;
		this.data = 0;
		this.name = "";
		this.lore = new ArrayList<>();
		this.enchants = new HashMap<>();
		this.color = null;
	}

	@Override
	public IItemBuilder amount(int amount)
	{
		if (amount < 1 || amount > 64)
		{
			throw new IllegalArgumentException("Amount cannot be less than 1 or greater than 64");
		}

		this.amount = amount;

		return this;
	}

	@Override
	public IItemBuilder data(short data)
	{
		if (data < 0)
		{
			throw new IllegalArgumentException("Data cannot be negative");
		}

		this.data = data;

		return null;
	}

	@Override
	public IItemBuilder name(String name)
	{
		this.name = name;

		return null;
	}

	@Override
	public IItemBuilder lore(List<String> lore)
	{
		this.lore = lore;

		return null;
	}

	@Override
	public IItemBuilder enchant(Enchantment enchantment, int level)
	{
		this.enchants.put(enchantment, level);

		return null;
	}

	@Override
	public IItemBuilder color(Color color)
	{
		this.color = color;

		return this;
	}

	@Override
	public ItemStack build()
	{
		ItemStack item = new ItemStack(material);

		item.setAmount(amount);
		item.setDurability(data);

		ItemMeta meta = Bukkit.getItemFactory().getItemMeta(material);

		if (!name.isEmpty())
		{
			meta.setDisplayName(name);
		}

		meta.setLore(lore);

		item.setItemMeta(meta);

		for (Enchantment enchantment : enchants.keySet())
		{
			item.addUnsafeEnchantment(enchantment, enchants.get(enchantment));
		}

		return item;
	}
}