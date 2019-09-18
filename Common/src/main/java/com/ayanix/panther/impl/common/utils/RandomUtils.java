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

import com.ayanix.panther.utils.common.IRandomUtils;

import java.util.List;
import java.util.Random;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public class RandomUtils implements IRandomUtils
{

	private static final Random RANDOM = new Random();
	private static RandomUtils instance;

	/**
	 * Grab the static version of RandomUtils.
	 *
	 * @return RandomUtils.
	 */
	public static RandomUtils get()
	{
		if (instance == null)
		{
			instance = new RandomUtils();
		}

		return instance;
	}

	@Override
	public int getInteger(int min, int max)
	{
		min = Math.min(min, max);
		max = Math.max(min, max);

		if(min < 0 && max < 0) {
			int newMin = max * -1;
			int newMax = min * -1;

			return (RANDOM.nextInt(newMax - newMin + 1) + newMin) * -1;
		}

		return RANDOM.nextInt(max - min + 1) + min;
	}

	@Override
	public <E> E getElement(List<E> list)
	{
		return list.get(RANDOM.nextInt(list.size()));
	}

}