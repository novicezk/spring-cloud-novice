package com.novice.framework.cloud.commons.support;

public final class StringHelper {
	private StringHelper() {
	}

	public static String underscoreToCamelCase(String source) {
		StringBuilder target = new StringBuilder();
		String[] subArray = source.split("_");
		for (String s : subArray) {
			if (target.length() == 0) {
				target.append(s.toLowerCase());
			} else {
				target.append(s.substring(0, 1).toUpperCase());
				target.append(s.substring(1).toLowerCase());
			}
		}
		return target.toString();
	}

	public static String camelCaseToUnderscore(String source) {
		StringBuilder builder = new StringBuilder(source);
		for (int i = 1; i < builder.length() - 1; i++) {
			if (isUnderscoreRequired(builder.charAt(i - 1), builder.charAt(i), builder.charAt(i + 1))) {
				builder.insert(i++, '_');
			}
		}
		return builder.toString().toLowerCase();
	}

	private static boolean isUnderscoreRequired(char before, char current, char after) {
		return Character.isLowerCase(before) && Character.isUpperCase(current) && Character.isLowerCase(after);
	}
}
