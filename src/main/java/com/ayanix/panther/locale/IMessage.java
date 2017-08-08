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

import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public interface IMessage
{

	/**
	 * Get the key identifying the message.
	 *
	 * @return Key.
	 */
	String getKey();

	/**
	 * Replace {key} in the message with the value given.
	 *
	 * @param key   Substring to replace, excluding { and }.
	 * @param value Substring to replace with.
	 */
	IMessage replace(String key, String value);

	/**
	 * Get the formatted message in a String format.
	 * #toString() returns the same however unformatted.
	 * <p>
	 * If the message is a list, \n is appended to each line.
	 *
	 * @return String version of message.
	 */
	String get();

	/**
	 * Get the message in a String format.
	 * #toString() returns the same however unformatted.
	 * <p>
	 * If the message is a list, \n is appended to each line.
	 *
	 * @param formatted If true, colours are formatted.
	 * @return String version of message.
	 */
	String get(boolean formatted);

	/**
	 * Get the formatted message in a List format.
	 *
	 * @return List of string messages.
	 */
	List<String> getList();

	/**
	 * Get the message in a List format.
	 *
	 * @param formatted If true, colours are formatted.
	 * @return List of string messages.
	 */
	List<String> getList(boolean formatted);

	/**
	 * Send the formatted message to given player or console.
	 *
	 * @param sender Message to be sent to.
	 */
	void send(CommandSender sender);

	/**
	 * Broadcast the formatted message to all players online and console.
	 */
	void broadcast();

}