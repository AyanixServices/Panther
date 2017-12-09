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
package com.ayanix.panther.utils.bukkit.item;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public interface PotionBuilder extends ItemBuilder
{

	/**
	 * Set the amplifier of the potion effect;
	 *
	 * @param amplifier The amplifier to set, cannot be negative.
	 * @return The PotionBuilder object.
	 */
	PotionBuilder amplifier(int amplifier);

	/**
	 * Set whether or not the potion is splash.
	 *
	 * @param splash Set whether or not the potion is splash.
	 * @return The PotionBuilder object.
	 */
	PotionBuilder splash(boolean splash);

	/**
	 * Set whether or not the potion is extended.
	 *
	 * @param extended Set whether or not the potion is extended.
	 * @return The PotionBuilder object.
	 */
	PotionBuilder extended(boolean extended);

}