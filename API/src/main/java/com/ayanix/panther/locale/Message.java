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

import java.util.List;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public interface Message
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
	 * @return The message with replaced values.
	 */
	Message replace(String key, String value);

	/**
	 * Get the formatted message in a String format.
	 * #toString() returns the same however unformatted.
	 * <p>
	 * If the message is a list, \n is appended to each line.
	 *
	 * @return String version of message.
	 */
	default String get()
	{
		return get(true);
	}

	/**
	 * Get the message in a String format.
	 * #toString() returns the same however unformatted.
	 * <p>
	 * If the message is a list, \n is appended to each line.
	 *
	 * @param formatted If true, colours are formatted.
	 * @return String version of message.
	 */
	default String get(boolean formatted)
	{
		StringBuilder builder = new StringBuilder();

		for (String message : getList(formatted))
		{
			builder.append(message);
			builder.append('\n');
		}

		String toString = builder.toString();

		toString = toString.substring(0, toString.length() - 1);

		return toString;
	}

	/**
	 * Get the message in a List format.
	 *
	 * @param formatted If true, colours are formatted.
	 * @return List of string messages.
	 */
	List<String> getList(boolean formatted);

	/**
	 * Get the formatted message in a List format.
	 *
	 * @return List of string messages.
	 */
	default List<String> getList()
	{
		return getList(true);
	}

	/**
	 * Send the formatted message to given player or console.
	 *
	 * @param sender Message to be sent to.
	 */
	void send(Object sender);

	/**
	 * Send the formatted message to given player or console.
	 * If the original message has already been sent to the player or console within the last second, it won't be sent again.
	 * <p>
	 * This will not take into account messages where the placeholders have been modified.
	 * (e.g. sending the same message but with different placeholder values within 1 second will still be blocked).
	 *
	 * @param sender Message to be sent to.
	 */
	default void sendOnce(Object sender)
	{
		sendOnce(sender, 1000);
	}

	/**
	 * Send the formatted message to given player or console.
	 * If the original message has already been sent to the player or console within the specified time, it won't be sent again.
	 * <p>
	 * This will not take into account messages where the placeholders have been modified.
	 * (e.g. sending the same message but with different placeholder values within the specified time will still be blocked).
	 *
	 * @param sender       Message to be sent to.
	 * @param milliseconds The time span to ignore duplicate messages, in milliseconds.
	 */
	void sendOnce(Object sender, long milliseconds);

	/**
	 * Send as title to player. If console, nothing will happen.
	 * This will not remove the old subtitle.
	 *
	 * @param sender Message to be sent to.
	 */
	default void sendTitle(Object sender)
	{
		sendTitle(sender, 20, 20, 60);
	}

	/**
	 * Send as title to player. If console, nothing will happen.
	 * This will not remove the old subtitle.
	 * <p>
	 * If the message is a list, the first line will be the title and the second line a subtitle.
	 *
	 * @param sender  Message to be sent to.
	 * @param fadeIn  Time in ticks to fade in.
	 * @param fadeOut Time in ticks to fade out.
	 * @param stay    Time in ticks to stay on screen.
	 */
	void sendTitle(Object sender, int fadeIn, int fadeOut, int stay);

	/**
	 * Send as subtitle to player. If console, nothing will happen.
	 * This will not remove the old title.
	 * <p>
	 * If the message is a list, the first line will be the title and the second line a subtitle.
	 *
	 * @param sender Message to be sent to.
	 */
	default void sendSubtitle(Object sender)
	{
		sendSubtitle(sender, 20, 20, 60);
	}

	/**
	 * Send as subtitle to player. If console, nothing will happen.
	 * This will not remove the old title.
	 *
	 * @param sender  Message to be sent to.
	 * @param fadeIn  Time in ticks to fade in.
	 * @param fadeOut Time in ticks to fade out.
	 * @param stay    Time in ticks to stay on screen.
	 */
	void sendSubtitle(Object sender, int fadeIn, int fadeOut, int stay);

	/**
	 * Broadcast the formatted message to all players online and console.
	 */
	void broadcast();

}