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
import com.ayanix.panther.utils.item.IPotionBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public class PotionBuilder extends ItemBuilder implements IPotionBuilder
{

	private PotionEffectType type;
	private int              amplifier;
	private boolean          splash;
	private boolean          extended;

	public PotionBuilder(PotionEffectType type)
	{
		super(Material.POTION);

		this.type = type;
		this.amplifier = 0;
		this.splash = false;
		this.extended = false;
	}

	@Override
	public IPotionBuilder amount(int amount)
	{
		super.amount(amount);

		return this;
	}

	@Override
	public IItemBuilder data(short data)
	{
		super.data(data);

		return this;
	}

	@Override
	public IItemBuilder name(String name)
	{
		super.name(name);

		return this;
	}

	@Override
	public IItemBuilder lore(List<String> lore)
	{
		super.lore(lore);

		return this;
	}

	@Override
	public IItemBuilder enchant(Enchantment enchantment, int level)
	{
		super.enchant(enchantment, level);

		return this;
	}

	@Override
	public IItemBuilder color(Color color)
	{
		super.color(color);

		return this;
	}

	@Override
	public ItemStack build()
	{
		ItemStack item = new ItemStack(super.material, 1, (short) 24576);

		PotionMeta meta = (PotionMeta) Bukkit.getItemFactory().getItemMeta(super.material);

		meta.setMainEffect(type);

		if (!super.name.isEmpty())
		{
			meta.setDisplayName(super.name);
		}

		meta.setLore(super.lore);

		item.setItemMeta(meta);

		Potion potion = Potion.fromItemStack(item);

		potion.setSplash(splash);
		potion.setHasExtendedDuration(extended);

		return potion.toItemStack(amount);
	}

	@Override
	public IPotionBuilder amplifier(int amplifier)
	{
		if (amplifier < 0)
		{
			throw new IllegalArgumentException("Amplifier cannot be negative");
		}

		this.amplifier = amplifier;

		return this;
	}

	@Override
	public IPotionBuilder splash(boolean splash)
	{
		this.splash = splash;

		return this;
	}

	@Override
	public IPotionBuilder extended(boolean extended)
	{
		this.extended = extended;

		return this;
	}

}