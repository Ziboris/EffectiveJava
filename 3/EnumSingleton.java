package testJDK;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/*
 * 结果一定会报异常
 * 原因：EnumSingleton的.class.getDeclaredConstructor获取的构造器，并没有设置的无参构造器
 * http://www.cnblogs.com/chiclee/p/9097772.html
 */

public enum EnumSingleton {
	INSTANCE;
	public EnumSingleton getInstance() {
		return INSTANCE;
	}

	public static void main(String[] args)
			throws IllegalAccessException, InvocationTargetException, InstantiationException {
		EnumSingleton singleton1 = EnumSingleton.INSTANCE;
		EnumSingleton singleton2 = EnumSingleton.INSTANCE;
		System.out.println("正常情况下，实例化两个实例是否相同：" + (singleton1 == singleton2));

		Constructor<EnumSingleton> constructor = null;
		try {
			constructor = EnumSingleton.class.getDeclaredConstructor();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		constructor.setAccessible(true);
		EnumSingleton singleton3 = null;
		singleton3 = constructor.newInstance();
		System.out.println(singleton1 + "\n" + singleton2 + "\n" + singleton3);
		System.out.println("通过反射攻击单例模式情况下，实例化两个实例是否相同：" + (singleton1 == singleton3));
	}
}
