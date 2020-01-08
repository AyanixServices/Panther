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
package com.ayanix.panther.storage.configuration;

import com.google.common.collect.Sets;

import java.util.*;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 * <p>
 * This file was taken from SpigotMC#BungeeCord
 */
public final class Configuration
{

	private static final char                SEPARATOR = '.';
	final                Map<String, Object> self;
	private final        Configuration       defaults;

	public Configuration()
	{
		this(null);
	}

	public Configuration(Configuration defaults)
	{
		this(new LinkedHashMap<String, Object>(), defaults);
	}

	public Configuration(Map<?, ?> map, Configuration defaults)
	{
		this.self = new LinkedHashMap<>();
		this.defaults = defaults;

		for (Map.Entry<?, ?> entry : map.entrySet())
		{
			String key = (entry.getKey() == null) ? "null" : entry.getKey().toString();

			if (entry.getValue() instanceof Map)
			{
				this.self.put(key, new Configuration((Map) entry.getValue(), (defaults == null) ? null : defaults.getSection(key)));
			} else
			{
				this.self.put(key, entry.getValue());
			}
		}
	}

	private Configuration getSectionFor(String path)
	{
		int index = path.indexOf(SEPARATOR);
		if (index == -1)
		{
			return this;
		}

		String root    = path.substring(0, index);
		Object section = self.get(root);
		if (section == null)
		{
			section = new Configuration((defaults == null) ? null : defaults.getSection(root));
			self.put(root, section);
		}

		return (Configuration) section;
	}

	private String getChild(String path)
	{
		int index = path.indexOf(SEPARATOR);
		return (index == -1) ? path : path.substring(index + 1);
	}

	/*------------------------------------------------------------------------*/
	@SuppressWarnings("unchecked")
	public <T> T get(String path, T def)
	{
		Configuration section = getSectionFor(path);
		Object        val;
		if (section == this)
		{
			val = self.get(path);
		} else
		{
			val = section.get(getChild(path), def);
		}

		if (val == null && def instanceof Configuration)
		{
			self.put(path, def);
		}

		return (val != null) ? (T) val : def;
	}

	public boolean contains(String path)
	{
		return get(path, null) != null;
	}

	public Object get(String path)
	{
		return get(path, getDefault(path));
	}

	public Object getDefault(String path)
	{
		return (defaults == null) ? null : defaults.get(path);
	}

	public void set(String path, Object value)
	{
		if (value instanceof Map)
		{
			value = new Configuration((Map) value, (defaults == null) ? null : defaults.getSection(path));
		}

		Configuration section = getSectionFor(path);
		if (section == this)
		{
			if (value == null)
			{
				self.remove(path);
			} else
			{
				self.put(path, value);
			}
		} else
		{
			section.set(getChild(path), value);
		}
	}

	/*------------------------------------------------------------------------*/
	public Configuration getSection(String path)
	{
		Object def = getDefault(path);
		return (Configuration) get(path, (def instanceof Configuration) ? def : new Configuration((defaults == null) ? null : defaults.getSection(path)));
	}

	/**
	 * Gets keys, not deep by default.
	 *
	 * @return top level keys for this section
	 */
	public Collection<String> getKeys()
	{
		return new LinkedHashSet<>(self.keySet());
	}

	/*------------------------------------------------------------------------*/
	public byte getByte(String path)
	{
		Object def = getDefault(path);
		return getByte(path, (def instanceof Number) ? ((Number) def).byteValue() : 0);
	}

	public byte getByte(String path, byte def)
	{
		Object val = get(path, def);
		return (val instanceof Number) ? ((Number) val).byteValue() : def;
	}

	public List<Byte> getByteList(String path)
	{
		List<?>    list   = getList(path);
		List<Byte> result = new ArrayList<>();

		for (Object object : list)
		{
			if (object instanceof Number)
			{
				result.add(((Number) object).byteValue());
			}
		}

		return result;
	}

	public short getShort(String path)
	{
		Object def = getDefault(path);
		return getShort(path, (def instanceof Number) ? ((Number) def).shortValue() : 0);
	}

	public short getShort(String path, short def)
	{
		Object val = get(path, def);
		return (val instanceof Number) ? ((Number) val).shortValue() : def;
	}

	public List<Short> getShortList(String path)
	{
		List<?>     list   = getList(path);
		List<Short> result = new ArrayList<>();

		for (Object object : list)
		{
			if (object instanceof Number)
			{
				result.add(((Number) object).shortValue());
			}
		}

		return result;
	}

	public int getInt(String path)
	{
		Object def = getDefault(path);
		return getInt(path, (def instanceof Number) ? ((Number) def).intValue() : 0);
	}

	public int getInt(String path, int def)
	{
		Object val = get(path, def);
		return (val instanceof Number) ? ((Number) val).intValue() : def;
	}

	public List<Integer> getIntList(String path)
	{
		List<?>       list   = getList(path);
		List<Integer> result = new ArrayList<>();

		for (Object object : list)
		{
			if (object instanceof Number)
			{
				result.add(((Number) object).intValue());
			}
		}

		return result;
	}

	public long getLong(String path)
	{
		Object def = getDefault(path);
		return getLong(path, (def instanceof Number) ? ((Number) def).longValue() : 0);
	}

	public long getLong(String path, long def)
	{
		Object val = get(path, def);
		return (val instanceof Number) ? ((Number) val).longValue() : def;
	}

	public List<Long> getLongList(String path)
	{
		List<?>    list   = getList(path);
		List<Long> result = new ArrayList<>();

		for (Object object : list)
		{
			if (object instanceof Number)
			{
				result.add(((Number) object).longValue());
			}
		}

		return result;
	}

	public float getFloat(String path)
	{
		Object def = getDefault(path);
		return getFloat(path, (def instanceof Number) ? ((Number) def).floatValue() : 0);
	}

	public float getFloat(String path, float def)
	{
		Object val = get(path, def);
		return (val instanceof Number) ? ((Number) val).floatValue() : def;
	}

	public List<Float> getFloatList(String path)
	{
		List<?>     list   = getList(path);
		List<Float> result = new ArrayList<>();

		for (Object object : list)
		{
			if (object instanceof Number)
			{
				result.add(((Number) object).floatValue());
			}
		}

		return result;
	}

	public double getDouble(String path)
	{
		Object def = getDefault(path);
		return getDouble(path, (def instanceof Number) ? ((Number) def).doubleValue() : 0);
	}

	public double getDouble(String path, double def)
	{
		Object val = get(path, def);
		return (val instanceof Number) ? ((Number) val).doubleValue() : def;
	}

	public List<Double> getDoubleList(String path)
	{
		List<?>      list   = getList(path);
		List<Double> result = new ArrayList<>();

		for (Object object : list)
		{
			if (object instanceof Number)
			{
				result.add(((Number) object).doubleValue());
			}
		}

		return result;
	}

	public boolean getBoolean(String path)
	{
		Object def = getDefault(path);
		return getBoolean(path, (def instanceof Boolean) ? (Boolean) def : false);
	}

	public boolean getBoolean(String path, boolean def)
	{
		Object val = get(path, def);
		return (val instanceof Boolean) ? (Boolean) val : def;
	}

	public List<Boolean> getBooleanList(String path)
	{
		List<?>       list   = getList(path);
		List<Boolean> result = new ArrayList<>();

		for (Object object : list)
		{
			if (object instanceof Boolean)
			{
				result.add((Boolean) object);
			}
		}

		return result;
	}

	public char getChar(String path)
	{
		Object def = getDefault(path);
		return getChar(path, (def instanceof Character) ? (Character) def : '\u0000');
	}

	public char getChar(String path, char def)
	{
		Object val = get(path, def);
		return (val instanceof Character) ? (Character) val : def;
	}

	public List<Character> getCharList(String path)
	{
		List<?>         list   = getList(path);
		List<Character> result = new ArrayList<>();

		for (Object object : list)
		{
			if (object instanceof Character)
			{
				result.add((Character) object);
			}
		}

		return result;
	}

	public String getString(String path)
	{
		Object def = getDefault(path);
		return getString(path, (def instanceof String) ? (String) def : "");
	}

	public String getString(String path, String def)
	{
		Object val = get(path, def);
		return (val instanceof String) ? (String) val : def;
	}

	public List<String> getStringList(String path)
	{
		List<?>      list   = getList(path);
		List<String> result = new ArrayList<>();

		for (Object object : list)
		{
			if (object instanceof String)
			{
				result.add((String) object);
			}
		}

		return result;
	}

	/*------------------------------------------------------------------------*/

	/**
	 * @param path  The path of the list in the config.
	 * @param clazz Entries in this list must match this class.
	 * @return A list with matching entries
	 */
	public <T> List<T> getList(String path, Class<T> clazz)
	{
		List<?> list    = getList(path);
		List<T> newList = new LinkedList<>();

		for (Object entry : list)
		{
			if (entry.getClass() == clazz)
			{
				newList.add(clazz.cast(entry));
			}
		}

		return newList;
	}

	public List<?> getList(String path)
	{
		Object def = getDefault(path);
		return getList(path, (def instanceof List<?>) ? (List<?>) def : Collections.emptyList());
	}

	public List<?> getList(String path, List<?> def)
	{
		Object val = get(path, def);
		return (val != null) ? (List<?>) val : def;
	}

	public boolean isList(String path)
	{
		return get(path) instanceof List<?>;
	}

	public boolean isString(String path)
	{
		return get(path) instanceof String;
	}

	public boolean isSection(String path)
	{
		return get(path) instanceof Configuration;
	}

}