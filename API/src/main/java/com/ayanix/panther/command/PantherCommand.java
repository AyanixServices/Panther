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
package com.ayanix.panther.command;

import com.ayanix.panther.locale.Message;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public interface PantherCommand
{

	/**
	 * @return Permission required. Empty if all can use.
	 */
	String getPermission();

	/**
	 * @return Permission error message.
	 */
	Message getPermissionError();

	/**
	 * @return Minimum number of arguments required.
	 */
	int getMinArgs();

	/**
	 * @return Usage message, shown when number of arguments is not met.
	 */
	Message getArgsError();

	/**
	 * @return If true, command can only be run by a player.
	 */
	boolean isPlayerOnly();

	/**
	 * @return Identifying class of command.
	 */
	Class<?> getClazz();

	/**
	 * Runs when command is executed.
	 *
	 * @param sender Command sender.
	 * @param label  Label of command used.
	 * @param args   Arguments passed.
	 */
	void run(Object sender, String label, String... args);

}