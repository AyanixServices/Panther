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
package com.ayanix.panther.impl.bukkit.utils.item;

import com.ayanix.panther.impl.bukkit.compat.BukkitVersion;
import com.ayanix.panther.impl.bukkit.enchantment.compat.v1_12_BukkitGlowEnchantment;
import com.ayanix.panther.impl.bukkit.enchantment.compat.v1_13_BukkitGlowEnchantment;
import com.ayanix.panther.impl.bukkit.enchantment.compat.v1_8_BukkitGlowEnchantment;
import com.ayanix.panther.impl.common.utils.RandomUtils;
import com.ayanix.panther.utils.bukkit.item.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public class BukkitItemUtils implements ItemUtils
{

	private static Enchantment            glowEnchantment = null;
	private static BukkitItemUtils        instance;
	private static Material               SKULL_ITEM_MATERIAL;
	private        JavaPlugin             plugin;
	private        Map<String, ItemStack> cache           = new HashMap<>();

	/**
	 * Initiate a BukkitItemUtils instance.
	 * <p>
	 * This has no support for glow tags.
	 */
	@Deprecated
	public BukkitItemUtils()
	{
		this(null);
	}

	/**
	 * Initiate a BukkitItemUtils instance.
	 *
	 * @param plugin Plugin.
	 */
	public BukkitItemUtils(JavaPlugin plugin)
	{
		this(plugin, new RandomUtils().getInteger(70, 200));
	}

	/**
	 * Initiate a BukkitItemUtils instance.
	 *
	 * @param plugin Plugin.
	 * @param glowId ID to use as glow, must be unique per plugin.
	 */
	public BukkitItemUtils(JavaPlugin plugin, int glowId)
	{
		this.plugin = plugin;

		if (plugin != null && glowEnchantment == null)
		{
			if (BukkitVersion.isRunningMinimumVersion(BukkitVersion.v1_13))
			{
				glowEnchantment = new v1_13_BukkitGlowEnchantment(plugin);
			} else if (BukkitVersion.isRunningMinimumVersion(BukkitVersion.v1_12))
			{
				glowEnchantment = new v1_12_BukkitGlowEnchantment(glowId);
			} else
			{
				glowEnchantment = new v1_8_BukkitGlowEnchantment(glowId);
			}

			registerGlow();
		}

		if (BukkitVersion.isRunningMinimumVersion(BukkitVersion.v1_13))
		{
			SKULL_ITEM_MATERIAL = Material.valueOf("PLAYER_HEAD");
		} else
		{
			SKULL_ITEM_MATERIAL = Material.SKULL_ITEM;
		}
	}

	/**
	 * Registers the glow enchantment to Bukkit so it can be used.
	 */
	private void registerGlow()
	{
		// reflection to allow new enchantments
		try
		{
			Field f = Enchantment.class.getDeclaredField("acceptingNew");
			f.setAccessible(true);
			f.set(null, true);
			f.setAccessible(false);
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		// register glow enchantment
		try
		{
			Enchantment.registerEnchantment(glowEnchantment);
		} catch (Exception e)
		{
			// already registered
		}
	}

	/**
	 * Declare the static version of BukkitItemUtils.
	 *
	 * @param plugin Plugin using BukkitItemUtils.
	 * @param glowId The unique ID to identify glow enchantment.
	 */
	public static void init(JavaPlugin plugin, int glowId)
	{
		if (instance != null)
		{
			return;
		}

		instance = new BukkitItemUtils(plugin, glowId);
	}

	/**
	 * Grab the static version of BukkitItemUtils after being declared in BukkitItemUtils#init.
	 *
	 * @return BukkitItemUtils.
	 * @throws RuntimeException If BukkitItemUtils#init has not been called.
	 */
	public static BukkitItemUtils get()
	{
		if (instance == null)
		{
			throw new RuntimeException("BukkitItemUtils has not been initialised for static usage");
		}

		return instance;
	}

	@Override
	public List<String> itemsToStrings(List<ItemStack> list)
	{
		return new LinkedList<>(list).stream().map(this::itemToString).collect(Collectors.toList());
	}

	@Override
	public String itemToString(ItemStack item)
	{
		if (item == null)
		{
			return "";
		}

		String                    mat                   = item.getType().toString();
		short                     durability            = item.getDurability();
		String                    amount                = Integer.toString(item.getAmount());
		Map<Enchantment, Integer> enchants              = item.hasItemMeta() ? item.getItemMeta().getEnchants() : item.getEnchantments();
		StringBuilder             fullEnchantmentString = new StringBuilder();
		StringBuilder             itemString            = new StringBuilder(mat).append(' ').append(amount);

		if (durability != 0)
		{
			itemString = new StringBuilder(mat + ":" + durability + " " + amount);
		}

		if (item.hasItemMeta())
		{
			try
			{
				String displayName = item.getItemMeta().getDisplayName();
				displayName = displayName.replace(" ", "_");

				itemString.append(' ').append(displayName);
			} catch (NullPointerException ignored)
			{
			}

			try
			{
				List<String> lore = item.getItemMeta().getLore();

				for (String message : lore)
				{
					itemString.append(" lore:").append(message);
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
			String      enchName = ench.getName();

			if(!enchName.equalsIgnoreCase("glow")) {
				fullEnchantmentString.append(' ').append(enchName).append(':').append(level);
			} else {
				fullEnchantmentString.append(' ').append(enchName);
			}
		}

		if (fullEnchantmentString.length() != 0)
		{
			itemString.append(fullEnchantmentString);
		}

		return itemString.toString();
	}

	@Override
	public List<ItemStack> stringsToItems(List<String> list)
	{
		return new LinkedList<>(list).stream().map(this::stringToItem).collect(Collectors.toList());
	}

	@Override
	public ItemStack stringToItem(String item)
	{
		if (item == null ||
				item.isEmpty())
		{
			return new ItemStack(Material.AIR, 1);
		}

		if (cache.containsKey(item))
		{
			return cache.get(item).clone();
		}

		String[]     itemSplit    = item.split(" ");
		List<String> itemWordList = Arrays.asList(itemSplit);

		String materialName = itemWordList.get(0);
		int    durability   = 0;

		Material mat;

		String skullName = "";

		if (!materialName.toUpperCase().startsWith("PLAYER_HEAD"))
		{
			if (materialName.contains(":"))
			{
				String[] materialData = materialName.split(":");
				materialName = materialData[0];
				durability = Integer.parseInt(materialData[1]);
			} else if ("POTION".equalsIgnoreCase(materialName))
			{
				durability = (short) 24576;
			}

			if (!isMaterial(materialName))
			{
				return new ItemStack(Material.AIR, 1);
			}

			mat = Material.matchMaterial(materialName);
		} else
		{
			mat = SKULL_ITEM_MATERIAL;
			durability = 3;

			if (materialName.contains(":"))
			{
				skullName = materialName.split(":")[1];
			}
		}

		int amount = 1;

		try
		{
			amount = Integer.parseInt(itemWordList.get(1));
		} catch (Exception ignored)
		{
		}

		String       name = null;
		List<String> lore = new ArrayList<>();

		// Potion
		PotionEffectType type      = null;
		boolean          splash    = false;
		boolean          extended  = false;
		int              duration  = 10;
		int              amplifier = 0;

		boolean hideEnchants = false;

		Map<Enchantment, Integer> enchantments = new HashMap<>();

		for (int x = 1; x < itemWordList.size(); x++)
		{
			String[] parts = itemWordList.get(x).split(":", 2);

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

					// Potion
				case "effect":
					for (PotionEffectType pType : PotionEffectType.values())
					{
						if (pType.getName().equalsIgnoreCase(parts[1]))
						{
							type = pType;
							break;
						}
					}

					continue;

				case "power":
					try
					{
						amplifier = Integer.parseInt(parts[1]) - 1;
					} catch (Exception ignored)
					{
					}

					continue;

				case "duration":
					try
					{
						duration = Integer.parseInt(parts[1]);
					} catch (Exception ignored)
					{
					}

					continue;

				case "splash":
					try
					{
						splash = Boolean.valueOf(parts[1]);
					} catch (Exception ignored)
					{
					}

					continue;

				case "extended":
					try
					{
						extended = Boolean.valueOf(parts[1]);
					} catch (Exception ignored)
					{
					}

					continue;

				case "glow":
					if (glowEnchantment != null)
					{
						enchantments.put(glowEnchantment, 1);
					}

					continue;

				case "hideenchants":
				case "hide_enchants":
					hideEnchants = true;
					continue;

				default:
					Enchantment enchantment = Enchantment.getByName(parts[0].toUpperCase(Locale.US));

					if (enchantment == null)
					{
						continue;
					}

					int level = Integer.parseInt(parts[1]);

					enchantments.put(enchantment, level);
			}
		}

		ItemStack itemStack = new ItemStack(mat, amount, (short) durability);
		ItemMeta  itemMeta  = itemStack.hasItemMeta() ? itemStack.getItemMeta() : Bukkit.getItemFactory().getItemMeta(mat);

		if (hideEnchants)
		{
			itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}

		if (name != null)
		{
			itemMeta.setDisplayName(name);
		}

		if (!lore.isEmpty())
		{
			itemMeta.setLore(lore);
		}

		itemStack.setItemMeta(itemMeta);

		if (mat == Material.POTION && type != null)
		{
			PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();

			potionMeta.addCustomEffect(new PotionEffect(type, duration * 20, amplifier), extended);

			itemStack.setItemMeta(potionMeta);

			Potion potion = Potion.fromItemStack(itemStack);
			potion.setSplash(splash);
			potion.apply(itemStack);
		}

		if (!skullName.isEmpty())
		{
			SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();

			skullMeta.setOwner(skullName);

			itemStack.setItemMeta(skullMeta);
		}

		itemStack.addUnsafeEnchantments(enchantments);

		cache.put(item, itemStack);

		return itemStack;
	}

	@Override
	public List<ItemStack> stringsToItems(List<String> list)
	{
		return list.stream().map(this::stringToItem).collect(Collectors.toList());
	}

	@Override
	public boolean isMaterial(String materialName)
	{
		return materialName != null && Material.matchMaterial(materialName) != null;
	}

	@Override
	public boolean areItemsEqual(ItemStack itemA, ItemStack itemB)
	{
		if (itemA == null ||
				itemB == null)
		{
			return false;
		}

		if (itemA.getType() != itemB.getType())
		{
			return false;
		}

		if (itemA.getDurability() != itemB.getDurability())
		{
			return false;
		}

		ItemMeta metaA = itemA.hasItemMeta() ? itemA.getItemMeta() : Bukkit.getItemFactory().getItemMeta(itemA.getType());
		ItemMeta metaB = itemB.hasItemMeta() ? itemB.getItemMeta() : Bukkit.getItemFactory().getItemMeta(itemB.getType());

		if (metaA.hasLore() && !metaB.hasLore() ||
				!metaA.hasLore() && metaB.hasLore())
		{
			return false;
		}

		if (metaA.hasLore() && metaB.hasLore())
		{
			if (metaA.getLore().size() != metaB.getLore().size())
			{
				return false;
			}

			for (int x = 0; x < Math.max(metaA.getLore().size(), metaB.getLore().size()); x++)
			{
				if (!metaA.getLore().get(x).equals(metaB.getLore().get(x)))
				{
					return false;
				}
			}
		}

		if (metaA.hasDisplayName() && metaB.hasDisplayName())
		{
			return metaA.getDisplayName().equalsIgnoreCase(metaB.getDisplayName());
		}

		if (itemA.getType() == SKULL_ITEM_MATERIAL && itemA.getDurability() == 3)
		{
			SkullMeta skullMetaA = (SkullMeta) itemA.getItemMeta();
			SkullMeta skullMetaB = (SkullMeta) itemB.getItemMeta();

			if (!skullMetaA.hasOwner() && skullMetaB.hasOwner())
			{
				return false;
			} else if (skullMetaA.hasOwner() && !skullMetaB.hasOwner())
			{
				return false;
			}

			if (!skullMetaA.getOwner().equals(skullMetaB.getOwner()))
			{
				return false;
			}
		}

		return !metaA.hasDisplayName() && !metaB.hasDisplayName();
	}

	@Override
	public List<Material> getMaterialsContaining(String... str)
	{
		List<Material> matching = new ArrayList<>();

		for (String string : str)
		{
			for (Material material : Material.values())
			{
				if (material.name().toLowerCase(Locale.US).contains(string.toLowerCase()))
				{
					matching.add(material);
				}
			}
		}

		return matching;
	}

}