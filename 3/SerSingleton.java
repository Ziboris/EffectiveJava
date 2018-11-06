package testJDK;

/*
 * 序列化前后单例模式类里面的对象并不相等
 * 因为任何一个readObject方法不管是显示的还是默认的，都会新建一个实例对象
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerSingleton implements Serializable {
	private volatile static SerSingleton uniqueInstance;
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	private SerSingleton() {
	}

	public static SerSingleton getInstance() {
		if (uniqueInstance == null) {
			synchronized (SerSingleton.class) {
				if (uniqueInstance == null) {
					uniqueInstance = new SerSingleton();
				}
			}
		}
		return uniqueInstance;
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		SerSingleton s = SerSingleton.getInstance();
		s.setContent("单例序列化");
		System.out.println("序列化前读取其中的内容：" + s.getContent());
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("SerSingleton.obj"));
		oos.writeObject(s);
		oos.flush();
		oos.close();

		FileInputStream fis = new FileInputStream("SerSingleton.obj");
		ObjectInputStream ois = new ObjectInputStream(fis);
		SerSingleton s1 = (SerSingleton) ois.readObject();
		ois.close();
		System.out.println(s + "\n" + s1);
		System.out.println("序列化后读取其中的内容：" + s1.getContent());
		System.out.println("序列化前后两个是否同一个：" + (s == s1));
	}

}