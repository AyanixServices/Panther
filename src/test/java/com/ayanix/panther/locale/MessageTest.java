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
package com.ayanix.panther.locale;

import com.ayanix.panther.impl.locale.Message;
import org.bukkit.entity.Player;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;
import java.util.Collections;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
@RunWith(PowerMockRunner.class)
public class MessageTest
{

	@Test
	public void testDependencyCheck()
	{
		Player player = PowerMockito.mock(Player.class);

		/* Test a single line is received when formatted */
		Message message = PowerMockito.spy(new Message("test", Collections.singletonList("Banana")));
		message.send(player);

		Mockito.verify(player, Mockito.atLeastOnce()).sendMessage("Banana");
		Mockito.reset(player);

		/* Test if multiple lines are received */
		message = PowerMockito.spy(new Message("test", Arrays.asList("Banana", "Apple")));
		message.send(player);

		Mockito.verify(player, Mockito.times(2)).sendMessage(Mockito.anyString());
		Mockito.reset(player);

		/* Test if formatted lines are different from unformatted,
		 * multiple lines in a single string contains new line code and
		 * amount of lines is equal to amount of message lines */
		message = PowerMockito.spy(new Message("test", Arrays.asList("&dBanana", "&1Apple")));

		Assert.assertFalse("Formatted message cannot contain colour codes", message.get().contains("&d"));
		Assert.assertTrue("Multiple lines in a single string must contain \\n", message.get().contains("\n"));
		Assert.assertTrue("Message as a list must contain same amount of lines", message.getList().size() == 2);
	}

}