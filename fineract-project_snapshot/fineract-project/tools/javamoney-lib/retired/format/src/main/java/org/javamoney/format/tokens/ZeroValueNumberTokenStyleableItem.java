/*
 * Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.javamoney.format.tokens;

import java.io.IOException;
import java.util.Locale;

import org.javamoney.format.ItemParseContext;
import org.javamoney.format.LocalizationContext;
import org.javamoney.format.StyleableItemFormatToken;
import org.javamoney.format.ItemParseException;

/**
 * Conditional token that allows to replace the representation of a zero
 * {@link Number} with an arbitrary literal value.
 * 
 * @author Anatole Tresch
 * 
 * @param <T>
 *            The concrete {@link Number} type.
 */
public class ZeroValueNumberTokenStyleableItem<T extends Number> extends AbstractStyleableItemFormatToken<T>{

	private String zeroValue;
	private StyleableItemFormatToken<T> decorated;

	public ZeroValueNumberTokenStyleableItem(StyleableItemFormatToken<T> decorated) {
		if (decorated == null) {
			throw new IllegalArgumentException("decorated is required.");
		}
		this.decorated = decorated;
	}

	public ZeroValueNumberTokenStyleableItem<T> setZeroValue(String value) {
		this.zeroValue = value;
		return this;
	}

	public String getZeroValue() {
		return zeroValue;
	}

	@Override
	protected String getToken(T item, Locale locale, LocalizationContext style) {
		if (item.doubleValue() == 0.0d || item.doubleValue() == -0.0d) {
			return zeroValue;
		}
		StringBuilder builder = new StringBuilder();
		try {
			this.decorated.print(builder, item, locale, style);
		} catch (IOException e) {
			throw new IllegalStateException("Formatting failed.", e);
		}
		return builder.toString();
	}

	@Override
	public void parse(ItemParseContext context, Locale locale, LocalizationContext style)
			throws ItemParseException {
		// not supported...
	}

}
