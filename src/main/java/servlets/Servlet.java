package servlets;


import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        out.print("<h1>Create img</h1>");

        String text = "Hello World!";
        BufferedImage img = new BufferedImage(640, 120, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        Font font = new Font("Arial", Font.BOLD, 72);
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        int width = fm.stringWidth(text);
        int height = fm.getHeight();
        g2d.dispose();


        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2d = img.createGraphics();
        g2d.setFont(font);
        fm = g2d.getFontMetrics();
        Random random = new Random();
        g2d.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
        g2d.drawString(text, 0, fm.getAscent());
        g2d.dispose();

        ImageIO.write(img, "png", new File("/Users/xpesh/IdeaProjects/NetCracker/src/main/webapp/generate.png"));
        out.print("<img src=\"/NetCracker-1.0-SNAPSHOT/generate.png\" height=\"120\" width=\"640\"/>");
        out.print("<h3>done.</h3>");

    }
}
