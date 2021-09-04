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

import com.ayanix.panther.utils.common.IMathsUtils;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public class MathsUtils implements IMathsUtils
{

	private static MathsUtils   instance;
	private final  NumberFormat format;

	public MathsUtils()
	{
		this.format = NumberFormat.getNumberInstance(Locale.US);
	}

	/**
	 * Grab the static version of MathsUtils.
	 *
	 * @return MathsUtils.
	 */
	public static MathsUtils get()
	{
		if (instance == null)
		{
			instance = new MathsUtils();
		}

		return instance;
	}

	@Override
	public String format(double value)
	{
		return format.format(value);
	}

	@Override
	public boolean isDouble(String string)
	{
		try
		{
			double value = Double.parseDouble(string);

			if (Double.isNaN(value) || !Double.isFinite(value)) {
				return false;
			}
		} catch (Exception e)
		{
			return false;
		}

		return true;
	}

	@Override
	public boolean isInteger(String string)
	{
		try
		{
			Integer.parseInt(string);
		} catch (Exception e)
		{
			return false;
		}

		return true;
	}


}