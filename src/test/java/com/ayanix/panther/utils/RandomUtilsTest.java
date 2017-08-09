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
package com.ayanix.panther.utils;

import com.ayanix.panther.impl.common.utils.RandomUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
@RunWith(PowerMockRunner.class)
public class RandomUtilsTest
{

	@Test
	public void testInteger()
	{
		RandomUtils randomUtils = new RandomUtils();

		int min = 100;
		int max = 200;

		int random = randomUtils.getInteger(min, max);

		if (random < min || random > max)
		{
			fail("Random integer cannot be larger or smaller than range");
		}
	}

	@Test
	public void testArray()
	{
		RandomUtils randomUtils = new RandomUtils();

		List<String> list = new ArrayList<>();

		list.add("banana");
		list.add("apple");
		list.add("orange");

		Assert.assertTrue("Random element from list must be contained in list", list.contains(randomUtils.getElement(list)));
	}

}