package com.utils;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;

public class PrintUtil {

	private static void print(String msg,HttpServletResponse response){
        PrintWriter writer=null;
		try {
            if(null != response){
                writer=response.getWriter();
                writer.print(msg);
                writer.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
            writer.close();
        }
    }
	public static void write(Object obj,HttpServletResponse response){
		response.setContentType("text/html; charset=utf-8");
		String json = JSONObject.toJSONString(obj);
		print(json,response);
	}
	
	/**
	 * 转发json格式工具类
	 * @author 99266
	 *
	 */
		public static void getJsonString(HttpServletResponse response,Object result) {
			//设置编码格式
			response.setContentType("text/html;charset=utf-8");
			String json = JSONObject.toJSONString(result);
			PrintWriter out = null;
			try {
				out = response.getWriter();
				out.print(json);
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				out.flush();
				out.close();
			}
		}
	}


