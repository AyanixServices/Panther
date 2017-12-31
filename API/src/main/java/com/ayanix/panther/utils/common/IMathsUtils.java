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
package com.ayanix.panther.utils.common;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public interface IMathsUtils
{

	/**
	 * Formats a value into readable format with commas.
	 *
	 * @param value The value to format.
	 * @return Formatted number.
	 */
	default String format(int value)
	{
		return format((double) value);
	}

	/**
	 * Formats a value into readable format with commas.
	 *
	 * @param value The value to format.
	 * @return Formatted number.
	 */
	String format(double value);

	/**
	 * @param string The string to check if applicable as a double.
	 * @return Whether or not the given string is a double.
	 */
	boolean isDouble(String string);

}