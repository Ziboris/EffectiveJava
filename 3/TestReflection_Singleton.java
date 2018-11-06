package testJDK;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class TestReflection_Singleton {

	public static class Singleton {
		public static final Singleton INSTANCE = new Singleton();

		private Singleton() {
		}

		public void func() {
			System.out.println("helloworld");
		}

		public static Singleton getInstance() {
			return INSTANCE;
		}
	}

	public static void main(String[] args) throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Singleton singleton = Singleton.getInstance();
		Singleton sUsual = Singleton.getInstance();
		Constructor<Singleton> constructor = Singleton.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		Singleton sReflection = constructor.newInstance();
		System.out.println("看看正常情况下面构造的两个singleton实例是否相同:" + (singleton == sUsual));
		System.out.println("通过反射情况产生的两个singleton对象是否相同：" + (singleton == sReflection));
	}

	/*
	 * 反射创建的singleton和正常创建的不同，如果要抵御这种攻击，需要在创建第二个的时候抛出异常
	 */
}
