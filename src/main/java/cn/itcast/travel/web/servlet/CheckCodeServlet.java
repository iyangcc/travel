package cn.itcast.travel.web.servlet;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * 验证码
 */
@WebServlet("/checkCode")
public class CheckCodeServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		
		//服务器通知浏览器不要缓存
		response.setHeader("pragma","no-cache");
		response.setHeader("cache-control","no-cache");
		response.setHeader("expires","0");

		int width = 128;
		int height = 34;
		//1.获取内存中图片对象
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		//2.美化图片
		//2.1填充
		//获取画笔
		Graphics g = img.getGraphics();
		Color bg = new Color(230, 223, 254);
		g.setColor(bg);
		g.fillRect(0,0,width,height);
		//2.2画边框
		g.setColor(bg);
		g.drawRect(0,0,width-1,height-1);
		//2.3填写验证码
		String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		Random r = new Random();
		//设置字体
		g.setFont(new Font("Tahoma", Font.BOLD, 27));
		//设置字符串缓冲区
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= 4 ; i++) {
			int index = r.nextInt(str.length());
			int degree = r.nextInt(60)-30;//-30到30
			Color color = new Color(r.nextInt(11)+19, r.nextInt(11)+51, r.nextInt(11)+110);
			g.setColor(color);
			sb.append(str.charAt(index));
			RotateString(str.charAt(index)+"", width/5*i, (height/2)+8, g, degree);
			//g.drawString(str.charAt(index)+"",width/6*i,(height/2)+10);
		}
		String checkCode_session = sb.toString();
		//将验证码存入session
		request.getSession().setAttribute("checkCode_session",checkCode_session);
		//2.4画干扰数据
		for (int i = 0; i <= 22; i++) {
			int x = r.nextInt(width);
			int y = r.nextInt(height);
			int index = r.nextInt(str.length());
			Color colors = new Color(r.nextInt(256),r.nextInt(256),r.nextInt(256));
			g.setColor(colors);
			g.setFont(new Font("Calibri", Font.BOLD, r.nextInt(11)));
			g.drawString(str.charAt(index)+"",x,y);
		}
		//3.输出图片
		ImageIO.write(img,"jpg",response.getOutputStream());
	}
	/**
	 * 旋转并且画出指定字符串
	 * @param s 需要旋转的字符串
	 * @param x 字符串的x坐标
	 * @param y 字符串的Y坐标
	 * @param g 画笔g
	 * @param degree 旋转的角度
	 */
	private void RotateString(String s,int x,int y,Graphics g,int degree)
	{
		Graphics2D g2d = (Graphics2D) g.create();
		// 平移原点到图形环境的中心,这个方法的作用实际上就是将字符串移动到某一个位置
		g2d.translate(x-1, y+3);
		// 旋转文本
		g2d.rotate(degree* Math.PI / 180);
		//特别需要注意的是,这里的画笔已经具有了上次指定的一个位置,所以这里指定的其实是一个相对位置
		g2d.drawString(s,0 , 0);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request,response);
	}
}



