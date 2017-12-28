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
package com.ayanix.panther.utils.bukkit.placeholder;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public interface IBukkitPlaceholder
{

	/**
	 * @return The identifier of the placeholder.
	 */
	String getName();

	/**
	 * @param type The placeholder plugin.
	 * @return Whether or not the placeholder has been registered in the given plugin.
	 */
	boolean isRegistered(PlaceholderType type);

	/**
	 * Update the value of the registered plugin which can be called by {@link #isRegistered(PlaceholderType)}
	 *
	 * @param type  The placeholder plugin.
	 * @param value True if registered, false if not registered.
	 */
	void setRegistered(PlaceholderType type, boolean value);

	/**
	 * @return The code executed when the placeholder is called.
	 */
	PlaceholderRunnable getRunnable();

	/**
	 * The compatible plugin placeholders.
	 */
	enum PlaceholderType
	{

		/**
		 * MVdWPlaceholderAPI (by MVdW)
		 */
		MVDW_PLACEHOLDER_API,

		/**
		 * PlaceholderAPI (by Clip)
		 */
		PLACEHOLDERAPI,
	}

}
