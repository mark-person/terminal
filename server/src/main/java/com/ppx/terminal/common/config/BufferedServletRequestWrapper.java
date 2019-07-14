/**
 * 
 */
package com.ppx.terminal.common.config;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class BufferedServletRequestWrapper extends HttpServletRequestWrapper {
 
	private byte[] buffer;
 
	public BufferedServletRequestWrapper(HttpServletRequest request) throws IOException {
		super(request);
		InputStream is = request.getInputStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte buff[] = new byte[1024];
		int read;
		while ((read = is.read(buff)) > 0) {
			baos.write(buff, 0, read);
		}
		this.buffer = baos.toByteArray();
		
	}
	
	
	@Override
	public ServletInputStream getInputStream() throws IOException {
		final ByteArrayInputStream bais = new ByteArrayInputStream(buffer);  
        return new ServletInputStream() {  
              
            @Override  
            public int read() throws IOException {  
                return bais.read();
            }

			@Override
			public boolean isFinished() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isReady() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void setReadListener(ReadListener listener) {
				// TODO Auto-generated method stub
				
			}  
        }; 
	}
 
	// 对外提供读取流的方法
	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(getInputStream()));
	}

}