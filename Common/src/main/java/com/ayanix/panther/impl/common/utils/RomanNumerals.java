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
package com.ayanix.panther.impl.common.utils;

import com.ayanix.panther.utils.common.IRomanNumerals;

import java.util.Map;
import java.util.TreeMap;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public class RomanNumerals implements IRomanNumerals
{

	private static TreeMap<Integer, String> map = new TreeMap<>();

	public RomanNumerals()
	{
		map.put(1000, "M");
		map.put(900, "CM");
		map.put(500, "D");
		map.put(400, "CD");
		map.put(100, "C");
		map.put(90, "XC");
		map.put(50, "L");
		map.put(40, "XL");
		map.put(10, "X");
		map.put(9, "IX");
		map.put(5, "V");
		map.put(4, "IV");
		map.put(1, "I");
	}

	@Override
	public String toRoman(final int num)
	{
		if (num <= 0)
		{
			throw new IllegalArgumentException("Roman numerals cannot be negative");
		}

		final int l = map.floorKey(num);

		if (num == l)
		{
			return map.get(num);
		}

		return map.get(l) + toRoman(num - l);
	}

	@Override
	public int toInt(final String roman)
	{
		if (roman.isEmpty())
		{
			return 0;
		}

		String key = roman.substring(0, 1);

		if (roman.length() - 2 >= 0 &&
				map.containsValue(roman.substring(0, 2)))
		{
			key = roman.substring(0, 2);
		}

		int amount = 0;

		for (final Map.Entry<Integer, String> entry : map.entrySet())
		{
			if (entry.getValue().equalsIgnoreCase(key))
			{
				amount = entry.getKey();

				break;
			}
		}

		if (amount == 0)
		{
			return 0;
		}

		return amount + toInt(roman.substring(key.length(), roman.length()));
	}

}
