package hu.pazsitz.tools.java.util;

import java.util.function.Function;
import java.util.Objects;
import java.util.Optional;

import static java.lang.Boolean.FALSE;

public class OptionalValidator {
	
	public static <T> Boolean isValidInt(T model, final Function<?super T, ? extends Integer> funcRef) {
		return Optional.ofNullable(model)
				.map(funcRef)
				.map(i -> i > 0)
				.orElse(FALSE);
	}
	
	public static <T> Boolean isNonBlankString(T model, final Function<?super T, ? extends String> funcRef) {
		String preProcess = stringToBooleanPreProcess(model, funcRef);
		return stringToBooleanPostProcess(preProcess, StringUtils::isNotBlank);
	}
		
	public static <T> Boolean isNonEmptyString(T model, final Function<?super T, ? extends String> funcRef) {
		String preProcess = stringToBooleanPreProcess(model, funcRef);
		return stringToBooleanPostProcess(preProcess, StringUtils::isNotEmpty);
	}
	
	public static <T> Boolean isNonNullString(T model, final Function<?super T, ? extends String> funcRef) {
		String preProcess = stringToBooleanPreProcess(model, funcRef);
		return stringToBooleanPostProcess(preProcess, Objects::nonNull);
	}
	
	
	private static <T> String stringToBooleanPreProcess(T model, Function<? super T, ? extends String> funcRef) {
		return Optional.ofNullable(model)
				.map(funcRef)
				.orElse(null);
	}

	private static Boolean stringToBooleanPostProcess(String preProcess, Function<? super String, Boolean> funcRef) {
		return Optional.ofNullable(preProcess)
				.map(funcRef)
				.orElse(FALSE);
	}

}
