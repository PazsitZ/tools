package hu.pazsitz.tools.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class InterfaceAccessor<I, R> implements Function<Object, R> {

	/**
	 * Invocation by the interface for interface marked Implementation
	 * @param object
	 * @return
	 */
	protected abstract R getByInterface(I object);

	public R apply(Object object) {
		Class<I> clazz = (Class<I>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
		return apply(object, clazz);
	}

	private R apply(Object object, Class<I> clazz) {
		if (clazz.isAssignableFrom(object.getClass())) {
			return getByInterface((I) object);
		} else {
			return invokeMethodDynamically(object, clazz);
		}
	}

	private <R> R invokeMethodDynamically(Object object, Class<I> classInterface) {
		Method interfaceMethod = classInterface.getDeclaredMethods()[0];
		Method method = findMethod(object.getClass(), interfaceMethod.getName());

		validateMethods(object, interfaceMethod, method);

		try {
			method.setAccessible(true);
			return (R) method.invoke(object, new Object[0]);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException(interfaceMethod.getName() + " method call had an issue on the class ["+object.getClass().getName()+"]", e);
		}
	}

	private void validateMethods(Object object, Method interfaceMethod, Method method) {
		if (method == null) {
			throw new IllegalArgumentException(interfaceMethod.getName() + " method is expected, but missing from the class ["+object.getClass().getName()+"]");
		} else {
            if (!interfaceMethod.getReturnType().isAssignableFrom(method.getReturnType())) {
                throw new IllegalArgumentException("Return types are not matching: ["+method.getReturnType()+"]<->["+interfaceMethod.getReturnType()+"]");
            }
        }
	}

	private Method findMethod(Class<?> clazz, String name) {
		Class<?> searchType = clazz;
		while (searchType != null) {
			Method[] methods = (searchType.isInterface() ? searchType.getMethods() :
					getDeclaredMethods(searchType, false));
			for (Method method : methods) {
				if (name.equals(method.getName())) {
					return method;
				}
			}
			searchType = searchType.getSuperclass();
		}
		return null;
	}

	private static Method[] getDeclaredMethods(Class<?> clazz, boolean defensive) {
		Method[] result = null;
		try {
			Method[] declaredMethods = clazz.getDeclaredMethods();
			List<Method> defaultMethods = findConcreteMethodsOnInterfaces(clazz);
			if (defaultMethods != null) {
				result = new Method[declaredMethods.length + defaultMethods.size()];
				System.arraycopy(declaredMethods, 0, result, 0, declaredMethods.length);
				int index = declaredMethods.length;
				for (Method defaultMethod : defaultMethods) {
					result[index] = defaultMethod;
					index++;
				}
			}
			else {
				result = declaredMethods;
			}
		}
		catch (Throwable ex) {
			throw new IllegalStateException("Failed to introspect Class [" + clazz.getName() +
					"] from ClassLoader [" + clazz.getClassLoader() + "]", ex);
		}
		return (result.length == 0 || !defensive) ? result : result.clone();
	}

	private static List<Method> findConcreteMethodsOnInterfaces(Class<?> clazz) {
		List<Method> result = null;
		for (Class<?> ifc : clazz.getInterfaces()) {
			for (Method ifcMethod : ifc.getMethods()) {
				if (!Modifier.isAbstract(ifcMethod.getModifiers())) {
					if (result == null) {
						result = new ArrayList<>();
					}
					result.add(ifcMethod);
				}
			}
		}
		return result;
	}
}
