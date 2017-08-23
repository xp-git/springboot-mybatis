package org.spring.springboot.shiro;

import java.io.Serializable;

import org.apache.shiro.util.SimpleByteSource;
/**
 * http://blog.csdn.net/poolokok/article/details/44009721
 */
public class MySimpleByteSource extends SimpleByteSource implements Serializable {
	public MySimpleByteSource(byte[] bytes) {
		super(bytes);
	}
}
