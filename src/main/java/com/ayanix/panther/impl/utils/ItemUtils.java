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
package com.ayanix.panther.impl.utils;

import com.ayanix.panther.utils.IItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.*;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public class ItemUtils implements IItemUtils
{

	@Override
	public String itemToString(@Nullable ItemStack item)
	{
		if (item == null)
		{
			return "";
		}

		String                    itemString            = "";
		String                    mat                   = item.getType().toString();
		String                    amount                = ((Integer) item.getAmount()).toString();
		Map<Enchantment, Integer> enchants              = item.getEnchantments();
		String                    fullEnchantmentString = "";

		itemString = mat + " " + amount;

		if (item.hasItemMeta())
		{
			try
			{
				String displayName = item.getItemMeta().getDisplayName();
				displayName = displayName.replace(" ", "_");

				itemString += " " + displayName;
			} catch (NullPointerException ignored)
			{
			}

			try
			{
				List<String> lore = item.getItemMeta().getLore();

				for (String message : lore)
				{
					itemString += " lore:" + message;
				}
			} catch (NullPointerException ignored)
			{
			}
		}

		Set<Map.Entry<Enchantment, Integer>> enchantSet = enchants.entrySet();

		for (Map.Entry<Enchantment, Integer> e : enchantSet)
		{
			Enchantment ench     = e.getKey();
			int         level    = e.getValue();
			String      enchName = e.getKey().getName();

			fullEnchantmentString += " " + enchName + ":" + level;
		}

		if (!fullEnchantmentString.equals(""))
			itemString = itemString + fullEnchantmentString;

		return itemString;
	}

	@Override
	public ItemStack stringToItem(@Nullable String item)
	{
		if (item == null ||
				item.isEmpty())
		{
			return new ItemStack(Material.AIR, 1);
		}

		String[]     itemSplit    = item.split(" ");
		List<String> itemWordList = Arrays.asList(itemSplit);

		String materialName = itemWordList.get(0);

		if (!isMaterial(materialName))
		{
			return new ItemStack(Material.AIR, 1);
		}

		Material mat = Material.valueOf(materialName.toUpperCase());

		int amount = 1;

		try
		{
			amount = Integer.valueOf(itemWordList.get(1));
		} catch (Exception ignored)
		{
		}

		String       name = null;
		List<String> lore = new ArrayList<>();

		Map<Enchantment, Integer> enchantments = new HashMap<Enchantment, Integer>();

		for (String word : itemWordList)
		{
			String[] parts = word.split(":");

			switch (parts[0])
			{
				case "name":
					name = ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', parts[1]);
					name = name.replaceAll("_", " ");

					continue;

				case "lore":
					String loreString = ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', parts[1]);
					loreString = loreString.replaceAll("_", " ");
					lore.add(loreString);

					continue;
			}

			try
			{
				Enchantment enchantment = Enchantment.getByName(parts[0]);
				int         level       = Integer.valueOf(parts[1]);

				enchantments.put(enchantment, level);
			} catch (Exception ignored)
			{
			}
		}

		ItemStack itemStack = new ItemStack(mat, amount);
		ItemMeta  itemMeta  = Bukkit.getItemFactory().getItemMeta(mat);

		if (name != null)
		{
			itemMeta.setDisplayName(name);
		}

		if (!lore.isEmpty())
		{
			itemMeta.setLore(lore);
		}

		itemStack.setItemMeta(itemMeta);
		itemStack.addUnsafeEnchantments(enchantments);

		return itemStack;
	}

	@Override
	public boolean isMaterial(@Nullable String materialName)
	{
		if (materialName == null)
		{
			return false;
		}

		try
		{
			Material.valueOf(materialName);
		} catch (IllegalArgumentException | NullPointerException exception)
		{
			return false;
		}

		return true;
	}

}